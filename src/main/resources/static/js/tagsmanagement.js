var TAGS_PAGE_SIZE = 5;
var TAGS_URL = '/posis/tags/?size=' + TAGS_PAGE_SIZE + '&sort=id';

Ext.define('Posis.model.TagModel', {
	extend: 'Ext.data.Model',
	fields: ['id', 'name', 'createdBy', 'createdDate', 'lastModifiedBy', 'lastModifiedDate']
});

Ext.define('Posis.store.TagStore', {
	extend: 'Ext.data.Store',
	model: 'Posis.model.TagModel',
	autoLoad: true,
	pageSize: TAGS_PAGE_SIZE,

	proxy: {
		type: 'ajax',
		url: TAGS_URL,
		reader: {
			type: 'json',
			root: 'data',
			idProperty: 'data.id',
			totalProperty: 'total'
		}
	}
});

var tagBufferedStore = new Posis.store.TagStore();
tagBufferedStore.getProxy().url = TAGS_URL;
tagBufferedStore.load();

Ext.define('Posis.view.tagsList', {
	extend: 'Ext.grid.Panel',
	title: 'Tags',
	alias: 'widget.tagslist',
	id: 'tagslist',
	store: 'TagStore',
	height: 450,
	scrollable: true,
	pageSize: TAGS_PAGE_SIZE,

	tbar: [{
		xtype: 'textfield',
		id: 'tagSearchField',
		enableKeyEvents: true,
		emptyText: 'Enter Search',
		action: 'search'
	}, {
		xtype: 'button',
		text: '+ Add Tag',
		action: 'add'
	}],

	columns: [{
		header: 'ID',
		dataIndex: 'id'
	}, {
		header: 'NAME',
		dataIndex: 'name'
	}, {
		header: 'CREATED BY',
		dataIndex: 'createdBy'
	}, {
		header: 'CREATED DATE',
		dataIndex: 'createdDate'
	}, {
		header: 'LAST MODIFIED BY',
		dataIndex: 'lastModifiedBy',
		width: 130
	}, {
		header: 'LAST MODIFIED DATE',
		dataIndex: 'lastModifiedDate',
		width: 150
	}],

	bbar: {
		xtype: 'pagingtoolbar',
		id: 'tagPagingToolbar',
		store: tagBufferedStore,
		displayInfo: true,
		displayMsg: 'Displaying {0} to {1} of {2} &nbsp;records ',
		emptyMsg: 'No records to display&nbsp;',
		listeners: {
			beforechange: function(pagingtoolbar, page, eOpts) {
				var searchText = Ext.getCmp('tagSearchField').getValue();
				tagBufferedStore.getProxy().url = TAGS_URL + '&search=' + encodeURIComponent(searchText);
				tagBufferedStore.load();
				var tagsList = Ext.getCmp('tagslist');
				var mainContainer = Ext.getCmp('mainContainer');
				setTimeout(function() {
					tagsList.reconfigure(tagBufferedStore);
					mainContainer.updateLayout();
				}, 500);
			}
		}
	}
});

Ext.define('Posis.view.TagFormWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.tagformwindow',
	width: 300,
	layout: 'fit',
	modal: true
});

Ext.define('Posis.view.TagForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.tagform',
	margin: 5,
	border: false,
	defaultType: 'textfield',
	items: [{
		id: 'tagName',
		fieldLabel: 'Tag Name',
		name: 'name',
		allowBlank: false
	}, {
		id: 'tagId',
		name: 'id',
		hidden: true
	}],

	buttons: [{
		id: 'tagFormOkButton',
		text: 'SUBMIT'
	}]
});

Ext.define('Posis.controller.TagController', {
	extend: 'Ext.app.Controller',
	views: ['TagFormWindow', 'TagForm'],
	refs: [{
		ref: 'tagFormWindow',
		xtype: 'tagformwindow',
		selector: 'tagformwindow',
		autoCreate: true
	}, {
		ref: 'tagsList',
		xtype: 'tagslist',
		selector: 'tagslist'
	}],

	init: function() {
		this.control({
			'tagslist > toolbar > button[action=add]': {
				click: this.showTagForm
			},
			'tagslist': {
				itemdblclick: this.onRowDblClick
			},
			'tagformwindow button[action=add]': {
				click: this.createTag
			},
			'tagformwindow button[action=edit]': {
				click: this.updateTag
			},
			'tagslist > toolbar > textfield[action=search]': {
				keyup: this.searchTag
			}
		});
	},
	showTagForm: function() {
		console.log("show tag form clicked");
		var tagform = new Posis.view.TagForm();
		var tagformwindow = this.getTagFormWindow({
			title: 'Create Tag',
			items: [tagform]
		});
		Ext.getCmp('tagFormOkButton').action = 'add';
		tagformwindow.show();
	},
	onRowDblClick: function(metaData, record, item, index) {
		console.log("double clicked on tag rows");
		var tagId = record.data.id;
		var tagform = new Posis.view.TagForm();
		var tagformwindow = this.getTagFormWindow({
			title: 'Update Tag',
			items: [tagform]
		});
		Ext.Ajax.request({
			url: '/posis/tags/searchById?id=' + tagId,
			method: 'GET',

			success: function(response) {
				var jsonResponse = JSON.parse(response.responseText);
				if (jsonResponse.status == 200) {
					Ext.getCmp('tagName').setValue(jsonResponse.data.name);
					Ext.getCmp('tagId').setValue(jsonResponse.data.id);
					Ext.getCmp('tagFormOkButton').action = 'edit';
					tagformwindow.show();
				}
			} 
		});
	},
	createTag: function() {
		var tagformwindow = this.getTagFormWindow();
		var tagform = tagformwindow.down('form').getForm();
		var tagData = tagform.getValues();
		var pagingtoolbar = Ext.getCmp('tagPagingToolbar');
		console.log(tagData);
		if (tagform.isValid()) {
			Ext.Ajax.request({
				url: '/posis/tags/',
				method: 'PUT',
				jsonData: tagData,

				success: function(response) {
					var jsonResponse = JSON.parse(response.responseText);
					if (jsonResponse.status == 201) {
						tagformwindow.close();
						Ext.Msg.alert('Success', jsonResponse.prompt, function() { pagingtoolbar.doRefresh(); });
						return;
					}
					Ext.Msg.alert('Notice', jsonResponse.prompt, Ext.emptyFn);
				},
				failure: function(response) {
					console.log("Something went wrong.");
					Ext.Msg.alert('Error', 'Something went wrong', Ext.emptyFn);
				}
			});
		}
	},
	updateTag: function() {
		var tagformwindow = this.getTagFormWindow();
		var tagform = tagformwindow.down('form').getForm();
		var tagData = tagform.getValues();
		var pagingtoolbar = Ext.getCmp('tagPagingToolbar');

		if (tagform.isValid()) {
			Ext.Ajax.request({
				url: '/posis/tags/',
				method: 'POST',
				jsonData: tagData,

				success: function(response) {
					var jsonResponse = JSON.parse(response.responseText);
					if (jsonResponse.status == 200) {
						tagformwindow.close();
						Ext.Msg.alert('Success', jsonResponse.prompt, function() { pagingtoolbar.doRefresh(); });
						return;
					}
					Ext.Msg.alert('Notice', jsonResponse.prompt, Ext.emptyFn);
				},
				failure: function(response) {
					console.log("Something went wrong.");
					Ext.Msg.alert('Error', 'Something went wrong.', Ext.emptyFn);
				}
			});
		}
	},
	searchTag: function() {
		var searchText = Ext.getCmp('tagSearchField').getValue();
		var tagStore = new Posis.store.TagStore();
		tagStore.getProxy().url = TAGS_URL + '&search=' + encodeURIComponent(searchText);
		var pagingtoolbar = Ext.getCmp('tagPagingToolbar');
		tagStore.load();
		var tagsList = this.getTagsList();
		setTimeout(function() {
			tagsList.reconfigure(tagStore);
			tagBufferedStore.totalCount = tagStore.getTotalCount();
			pagingtoolbar.onLoad();
		}, 500);
	}
});