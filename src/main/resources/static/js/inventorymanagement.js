var INVENTORY_PAGE_SIZE = 5;
var INVENTORY_URL = '/posis/inventories/?size=' + INVENTORY_PAGE_SIZE + '&sort=id';
var inventoryData = {};
inventoryData.product = {};

Ext.define('Posis.model.InventoryModel', {
	extend: 'Ext.data.Model',
	fields: ['id', 'stockQuantity', 'acquiredPrice', 'sellingPrice', 'createdBy', 'createdDate', 'lastModifiedBy', 'lastModifiedDate'],

	hasOne: [{
		model: 'Posis.model.ProductModel',
		name: 'product'
	}]	
}); 

Ext.define('Posis.store.InventoryStore', {
	extend: 'Ext.data.Store',
	model: 'Posis.model.InventoryModel',
	autoLoad: true,
	pageSize: INVENTORY_PAGE_SIZE,

	proxy: {
		type: 'ajax',
		url: INVENTORY_URL,
		reader: {
			type: 'json',
			root: 'data',
			idProperty: 'data.id',
			totalProperty: 'total'
		}
	}
});

var inventoryBufferedStore = new Posis.store.InventoryStore();
inventoryBufferedStore.getProxy().url = INVENTORY_URL;
inventoryBufferedStore.load();

Ext.define('Posis.view.InventoriesList', {
	extend: 'Ext.grid.Panel',
	title: 'Inventories',
	alias: 'widget.inventorieslist',
	id: 'inventorieslist',
	store: 'InventoryStore',
	height: 450,
	scrollable: true,
	pageSize: INVENTORY_PAGE_SIZE,

	tbar: [{
		xtype: 'textfield',
		id: 'inventorySearchField',
		enableKeyEvents: true,
		emptyText: 'Enter Search',
		action: 'search'
	}, {
		xtype: 'button',
		text: '+ Add Inventory',
		action: 'add'
	}],

	columns: [{
		header: 'ID',
		dataIndex: 'id'
	}, {
		header: 'PRODUCT ID',
		renderer: function(value, metaData, record, rowId, colId, store, view) {
			return record.getProduct().get('id');
		}
	}, {
		header: 'PRODUCT NAME',
		renderer: function(value, metaData, record, rowId, colId, store, view) {
			return record.getProduct().get('name');
		}
	}, {
		header: 'ACQUIRED PRICE',
		dataIndex: 'acquiredPrice'
	}, {
		header: 'SELLING PRICE',
		dataIndex: 'sellingPrice'
	}, {
		header: 'CREATED BY',
		dataIndex: 'createdBy'
	}, {
		header: 'CREATED DATE',
		dataIndex: 'createdDate'
	}, {
		header: 'LAST MODIFIED BY',
		dataIndex: 'lastModifiedBy'
	}, {
		header: 'LAST MODIFIED DATE',
		dataIndex: 'lastModifiedDate'
	}],

	bbar: {
		xtype: 'pagingtoolbar',
		id: 'inventoryPagingToolbar',
		store: inventoryBufferedStore,
		displayInfo: true,
		displayMsg: 'Displaying {0} of {1} of {2} &nbsp;records',
		emptyMsg: 'No records to display&nbsp;',
		listeners: {
			beforechange: function(pagingtoolbar, page, eOpts) {
				var searchText = Ext.getCmp('inventorySearchField').getValue();
				inventoryBufferedStore.getProxy().url = INVENTORY_URL + '&search=' + encodeURIComponent(searchText);
				inventoryBufferedStore.load();
				var inventoriesList = Ext.getCmp('inventorieslist');
				var mainContainer = Ext.getCmp('mainContainer');
				setTimeout(function() {
					inventoriesList.reconfigure(inventoryBufferedStore);
					mainContainer.updateLayout();
				}, 500);
			}	
		}
	}
});

Ext.define('Posis.view.InventoryFormWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.inventoryformwindow',
	width: 400,
	layout: 'fit',
	modal: true
});

Ext.define('Posis.view.InventoryForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.inventoryform',
	margin: 5,
	border: false,
	defaultType: 'textfield',
	items: [{
		xtype: 'combo',
		store: 'ProductStore',
		id: 'inventoryProductComboBox',
		name: 'product',
		displayField: 'name',
		valueField: 'id',
		width: 370,
		fieldLabel: 'Choose Product',
		pageSize: 5,
		enableKeyEvents: true,
		listConfig: {
			itemTpl: 'Name: {name} ID: {id}'
		},

		listeners: {
			keyup: function(combo, event) {
				var searchText = combo.getRawValue();
				if (!combo.isExpanded && event.getKey != 27) combo.expand();
				productBufferedStore.getProxy().url = '/posis/products/?size=5&sort=name&search=' + encodeURIComponent(searchText);
				productBufferedStore.load();
				setTimeout(function() {
					combo.bindStore(productBufferedStore);
				}, 500);
			},
			afterRender: function(combo) {
				console.log("combo box renderes");
				if (!combo.isExpanded) {
					var productName = inventoryData.product.name;
					if (productName != undefined) productBufferedStore.getProxy().url = '/posis/products/?size=5&sort=name&search=' + encodeURIComponent(productName);
					else productBufferedStore.getProxy().url = '/posis/products/?size=5&sort=name'; 
					productBufferedStore.load();
					setTimeout(function() {
						combo.bindStore(productBufferedStore);
						Ext.getCmp('inventoryProductComboBox').setValue(inventoryData.product.id);
					}, 500);				
				}
			}
		}
	}, {
		xtype: 'numberfield',
		fieldLabel: 'Quantity',
		id: 'stockQuantity',
		name: 'stockQuantity',
		allowBlank: false,
		allowDecimals: false
	}, {
		xtype: 'numberfield',
		fieldLabel: 'Base Price',
		id: 'acquiredPrice',
		name: 'acquiredPrice',
		allowBlank: false
	}, {
		xtype: 'numberfield',
		fieldLabel: 'Selling Price',
		id: 'sellingPrice',
		name: 'sellingPrice',
		allowBlank: false
	}, {
		id: 'inventoryId',
		name: 'id',
		hidden: true
	}],
	buttons: [{
		id: 'inventoryFormOkButton',
		text: 'SUBMIT'
	}],
	listeners: {
		afterRender: function(form) {
			console.log("Form is rendered");
			Ext.getCmp('inventoryId').setValue(inventoryData.id);
			Ext.getCmp('stockQuantity').setValue(inventoryData.stockQuantity);
			Ext.getCmp('acquiredPrice').setValue(inventoryData.acquiredPrice);
			Ext.getCmp('sellingPrice').setValue(inventoryData.sellingPrice);		
		}
	}
});

Ext.define('Posis.controller.InventoryController', {
	extend: 'Ext.app.Controller',
	views: ['InventoryFormWindow', 'InventoriesList'],
	refs: [{
		ref: 'inventoryFormWindow',
		xtype: 'inventoryformwindow',
		selector: 'inventoryformwindow',
		autoCreate: true
	}, {
		ref: 'inventoriesList',
		xtype: 'inventorieslist',
		selector: 'inventorieslist',
	}],
	init: function() {
		this.control({
			'inventorieslist > toolbar > button[action=add]': {
				click: this.showInventoryForm
			},
			'inventorieslist': {
				itemdblclick: this.onRowDblClick
			},
			'inventoryformwindow button[action=add]': {
				click: this.createInventory
			},
			'inventoryformwindow button[action=edit]': {
				click: this.updateInventory
			},
			'inventorieslist > toolbar > textfield[action=search]': {
				keyup: this.searchInventory
			}
		});
	},
	showInventoryForm: function() {
		inventoryData = {};
		inventoryData.product = {};
		var inventoryform = new Posis.view.InventoryForm();
		var inventoryformwindow = this.getInventoryFormWindow({
			title: 'Create Inventory',
			items: [inventoryform]
		});
		Ext.getCmp('inventoryFormOkButton').action = 'add';
		inventoryformwindow.show();
		inventoryform.reset();
	},
	onRowDblClick: function(metaData, record, item, index) {
		var inventoryId = record.data.id;
		var inventoryform = new Posis.view.InventoryForm();
		var inventoryformwindow = this.getInventoryFormWindow({
			title: 'Update Inventory',
			items: [inventoryform]
		});

		Ext.Ajax.request({
			url: '/posis/inventories/searchById?id=' + encodeURIComponent(inventoryId),
			method: 'GET',

			success: function(response) {
				var jsonResponse = JSON.parse(response.responseText);
				if (jsonResponse.status == 200) {
					inventoryData = {};
					inventoryData.id            = jsonResponse.data.id;
					inventoryData.product       = jsonResponse.data.product;					
					inventoryData.stockQuantity = jsonResponse.data.stockQuantity;
					inventoryData.acquiredPrice = jsonResponse.data.acquiredPrice;
					inventoryData.sellingPrice  = jsonResponse.data.sellingPrice;					
					inventoryformwindow.show();					
					Ext.getCmp('inventoryFormOkButton').action = 'edit';				
					console.log(inventoryData);
				}
			}
		});		
	},
	createInventory: function() {
		var inventoryformwindow = this.getInventoryFormWindow();
		var inventoryform = inventoryformwindow.down('form').getForm();
		var inventoryformdata = inventoryform.getValues();
		var pagingtoolbar = Ext.getCmp('inventoryPagingToolbar');
		if (inventoryform.isValid()) {
			inventoryData = {};
			inventoryData.product = {};
			inventoryData.product.id = inventoryformdata.product;
			inventoryData.stockQuantity = inventoryformdata.stockQuantity;
			inventoryData.acquiredPrice = inventoryformdata.acquiredPrice;
			inventoryData.sellingPrice = inventoryformdata.sellingPrice;

			Ext.Ajax.request({
				url: '/posis/inventories/',
				method: 'PUT',
				jsonData: inventoryData,

				success: function(response) {
					var jsonResponse = JSON.parse(response.responseText);
					if (jsonResponse.status == 201) {
						inventoryformwindow.close();
						Ext.Msg.alert('Success', jsonResponse.prompt, function() { pagingtoolbar.doRefresh(); })
						return;
					}
					Ext.Msg.alert('Notice', jsonResponse.prompt, function() { Ext.emptyFn });
				},
				failure: function(response) {
					Ext.Msg.alert('Error', 'Something went wrong.', function() { Ext.emptyFn });
				}
			});
		}
	},
	updateInventory: function() {
		var inventoryformwindow = this.getInventoryFormWindow();
		var inventoryform = inventoryformwindow.down('form').getForm();
		var inventoryformdata = inventoryform.getValues();
		var pagingtoolbar = Ext.getCmp('inventoryPagingToolbar');
		if (inventoryform.isValid()) {
			inventoryData = {};
			inventoryData.product = {};
			inventoryData.id            = inventoryformdata.id;
			inventoryData.product.id    = inventoryformdata.product;
			inventoryData.stockQuantity = inventoryformdata.stockQuantity;
			inventoryData.acquiredPrice = inventoryformdata.acquiredPrice;
			inventoryData.sellingPrice  = inventoryformdata.sellingPrice;

			console.log(inventoryData);
			Ext.Ajax.request({
				url: '/posis/inventories/',
				method: 'POST',
				jsonData: inventoryData,

				success: function(response) {
					var jsonResponse = JSON.parse(response.responseText);
					if (jsonResponse.status == 200) {
						inventoryformwindow.close();
						Ext.Msg.alert('Success', jsonResponse.prompt, function() { pagingtoolbar.doRefresh(); });
						return;
					}
					Ext.Msg.alert('Notice', jsonResponse.prompt, function() { Ext.emptyFn });
				},

				failure: function(response) {
					Ext.Msg.alert('Error', 'Something went wrong.', function() { Ext.emptyFn });
				}
			});
		}
	},
	searchInventory: function() {
		console.log("keyup event in inventory search");
		var searchText = Ext.getCmp('inventorySearchField').getValue();
		var inventoryStore = new Posis.store.InventoryStore();
		inventoryStore.getProxy().url = INVENTORY_URL + '&search=' + encodeURIComponent(searchText);
		inventoryStore.load();
		var pagingtoolbar = Ext.getCmp('inventoryPagingToolbar');
		var inventoriesList = this.getInventoriesList();
		setTimeout(function() {
			inventoriesList.reconfigure(inventoryStore);
			inventoryBufferedStore.totalCount = inventoryStore.getTotalCount();
			pagingtoolbar.onLoad();
		}, 500);
	}
});