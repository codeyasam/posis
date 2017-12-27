Ext.define('Posis.model.ProductTypeModel', {
	extend: 'Ext.data.Model',
	fields: ['id', 'name', 'createdBy', 'createdDate', 'lastModifiedBy', 'lastModifiedDate']
});

Ext.define('Posis.store.ProductTypeStore', {
	extend: 'Ext.data.Store',
	model: 'Posis.model.ProductTypeModel',
	autoLoad: true,
	pageSize: 5,
	// data: productTypeData,
	// proxy: {
	// 	type: 'memory'
	// }
	proxy: {
		type: 'ajax',
		url: '/posis/endproduct/type/?size=5&sort=id',
		reader: {
			type: 'json',
			root: 'data',
			idProperty: 'data.id',
			totalProperty: 'total'
		}
	}	
});

var productTypeBufferedStore = new Posis.store.ProductTypeStore();
productTypeBufferedStore.getProxy().url = '/posis/endproduct/type/?size=5&sort=id';		
productTypeBufferedStore.load();

Ext.define('Posis.view.ProductTypesList', {
	extend: 'Ext.grid.Panel',
	title: 'Product Categories',
	alias: 'widget.producttypeslist',
	id: 'producttypeslist',
	store: 'ProductTypeStore',
	height: 250,
	scrollable: true,
	pageSize: 5,

	tbar: [{
		xtype: 'textfield',
		id: 'productTypeSearchField',
		enableKeyEvents: true,
		emptyText: 'Enter Search',
		action: 'search'
	}, {
		xtype: 'button',
		text: '+ Add Product Category',
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
        id: 'productTypePagingToolbar',
        store: productTypeBufferedStore,
        displayInfo: true,
        displayMsg: 'Displaying {0} to {1} of {2} &nbsp;records ',
        emptyMsg: "No records to display&nbsp;",
        listeners: {
        	beforechange: function(pagingtoolbar, page, eOpts) {
        		var searchText = Ext.getCmp('productTypeSearchField').getValue();
        		productTypeBufferedStore.getProxy().url = '/posis/endproduct/type/?size=5&sort=id'; 
        			+ '&page=' + encodeURIComponent(page) + '&search=' + encodeURIComponent(searchText);
        		productTypeBufferedStore.load();
        		var productTypesList = Ext.getCmp('producttypeslist');
        		var mainContainer = Ext.getCmp('mainContainer');
        		setTimeout(function() {
        			productTypesList.reconfigure(productTypeBufferedStore);
        			mainContainer.updateLayout();
        		}, 500);
        	}
        }
    }	
});

Ext.define('Posis.view.ProductTypeFormWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.producttypeformwindow',
	width: 300,
	layout: 'fit',
	modal: true
});

Ext.define('Posis.view.ProductTypeForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.producttypeform',
	margin: 5,
	border: false,
	defaultType: 'textfield',
	items: [{
		id: 'productTypeName',
		fieldLabel: 'Category Name',
		name: 'name',
		allowBlank: false
	}, {
		id: 'productTypeId',
		name: 'id',
		hidden: true
	}],
	buttons: [{
		id: 'productTypeFormOkButton',
		text: 'SUBMIT'
	}]
});

Ext.define('Posis.controller.ProductTypeController', {
	extend: 'Ext.app.Controller',
	views: ['ProductTypeFormWindow', 'ProductTypesList'],
	refs: [{
		ref: 'productTypeFormWindow',
		xtype: 'producttypeformwindow',
		selector: 'producttypeformwindow',
		autoCreate: true
	}, {
		ref: 'productTypesList',
		xtype: 'producttypeslist',
		selector: 'producttypeslist'
	}],
	init: function() {
		this.control({
			'producttypeslist > toolbar > button[action=add]': {
				click: this.showProductCategoryForm
			},
			'producttypeslist': {
				itemdblclick: this.onRowDblClick
			},
			'producttypeformwindow button[action=add]': {
				click: this.createProductType
			},
			'producttypeformwindow button[action=edit]': {
				click: this.updateProductType
			},			
			'producttypeslist > toolbar > textfield[action=search]': {
				keyup: this.searchProductType
			}
		});
	},
	showProductCategoryForm: function() {
		console.log("show product category form clicked");
		var producttypeform =  new Posis.view.ProductTypeForm();
		var producttypeformwindow = this.getProductTypeFormWindow({
			title: 'Create Product Category',
			items: [producttypeform]
		});
		Ext.getCmp('productTypeFormOkButton').action = 'add';
		producttypeformwindow.show();
	},
	onRowDblClick: function(metaData, record, item, index) {
		console.log("double clicked row")
		var productTypeId = record.data.id;
		var producttypeform = new Posis.view.ProductTypeForm();
		var producttypeformwindow = this.getProductTypeFormWindow({
			title: 'Update Product Category',
			items: [producttypeform]
		});
		Ext.Ajax.request({
			url: '/posis/endproduct/type/searchById?id=' + productTypeId,
			method: 'GET',

			success: function(response) {
				var jsonResponse = JSON.parse(response.responseText);
				if (jsonResponse.status == 200) {
					Ext.getCmp('productTypeName').setValue(jsonResponse.data.name);
					Ext.getCmp('productTypeId').setValue(jsonResponse.data.id);
					Ext.getCmp('productTypeFormOkButton').action = 'edit';
					producttypeformwindow.show();
				}
			}

		});
	},
	createProductType: function() {
		var producttypeformwindow = this.getProductTypeFormWindow();
		var producttypeform = producttypeformwindow.down('form').getForm();
		var productTypeData = producttypeform.getValues();
		var pagingtoolbar = Ext.getCmp('productTypePagingToolbar');
		console.log(productTypeData);
		if (producttypeform.isValid()) {
			Ext.Ajax.request({
				url: '/posis/endproduct/type/',
				method: 'PUT',
				jsonData: productTypeData,

				success: function(response) {
					var jsonResponse = JSON.parse(response.responseText);
					if (jsonResponse.status == 201) {
						producttypeformwindow.close();
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
	updateProductType: function() {
		var producttypeformwindow = this.getProductTypeFormWindow();
		var producttypeform = producttypeformwindow.down('form').getForm();
		var productTypeData = producttypeform.getValues();
		var pagingtoolbar = Ext.getCmp('productTypePagingToolbar');
		if (producttypeform.isValid()) {
			Ext.Ajax.request({
				url: '/posis/endproduct/type/',
				method: 'POST',
				jsonData: productTypeData,

				success: function(response) {
					var jsonResponse = JSON.parse(response.responseText);
					if (jsonResponse.status == 200) {
						producttypeformwindow.close();
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
	searchProductType: function() {
		var searchText = Ext.getCmp('productTypeSearchField').getValue();
		var productTypeStore = new Posis.store.ProductTypeStore();
		productTypeStore.getProxy().url = '/posis/endproduct/type/?search=' + encodeURIComponent(searchText) + '&?size=5&sort=id';
		var pagingtoolbar = Ext.getCmp('productTypePagingToolbar');
		productTypeStore.load();
		var productTypesList = this.getProductTypesList();
		setTimeout(function() {
			productTypesList.reconfigure(productTypeStore);
			productTypeBufferedStore.totalCount = productTypeStore.getTotalCount();
			pagingtoolbar.onLoad();
		}, 500);
	}
});



