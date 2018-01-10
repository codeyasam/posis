var PRODUCT_PAGE_SIZE = 5;
var PRODUCT_URL = '/posis/products/?size=' + PRODUCT_PAGE_SIZE + '&sort=id';

Ext.define('Posis.model.ProductModel', {
	extend: 'Ext.data.Model',
	fields: ['id', 'name', 'createdBy', 'createdDate', 'lastModifiedBy', 'lastModifiedDate'],
	
	hasOne: [{
		model: 'Posis.model.ProductTypeModel',
		name: 'productType'
	}],

	hasMany: [{
		model: 'Posis.model.TagModel',
		name: 'productTags'
	}]
});

Ext.define('Posis.store.ProductStore', {
	extend: 'Ext.data.Store',
	model: 'Posis.model.ProductModel',
	autoLoad: true,
	pageSize: 5,

	proxy: {
		type: 'ajax',
		url: PRODUCT_URL,
		reader: {
			type: 'json',
			root: 'data',
			idProperty: 'data.id',
			totalProperty: 'total'
		}
	}
});

var productBufferedStore = new Posis.store.ProductStore();
productBufferedStore.getProxy().url = PRODUCT_URL;
productBufferedStore.load();

Ext.define('Posis.view.ProductsList', {
	extend: 'Ext.grid.Panel',
	title: 'Products',
	alias: 'widget.productslist',
	id: 'productslist',
	store: 'ProductStore',
	height: 450,
	scrollable: true,
	pageSize: PRODUCT_PAGE_SIZE,

	tbar: [{
		xtype: 'textfield',
		id: 'productSearchField',
		enableKeyEvents: true,
		emptyText: 'Enter Search',
		action: 'search'
	}, {
		xtype: 'button',
		text: '+ Add Product',
		action: 'add'
	}],

	columns: [{
		header: 'ID',
		dataIndex: 'id'
	}, {
		header: 'NAME',
		dataIndex: 'name'
	}, {
		header: 'CATEGORY ID',
		renderer: function(value, metaData, record, rowId, colId, store, view) {
			return record.getProductType().get('id');
		}	
	}, {
		header: 'CATEGORY',
		renderer: function(value, metaData, record, rowId, colId, store, view) {
			return record.getProductType().get('name');
		}
	}, {
		header: 'TAGS',
		renderer: function(value, metaData, record, rowId, colId, store, view) {
			values = [];
			record.productTags().each(function(tag) {
				values.push(tag.get('name'));
			});
			return values.join("<br/>");
		}
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
		id: 'productPaingToolbar',
		store: productBufferedStore,
		displayInfo: true,
		displayMsg: 'Displaying {0} to {1} of {2} &nbsp;records ',
        emptyMsg: "No records to display&nbsp;",
        listeners: {
        	beforechange: function(pagingtoolbar, page, eOpts) {
        		var searchText = Ext.getCmp('productSearchField').getValue();
        		productBufferedStore.getProxy().url = PRODUCT_URL;
        		productBufferedStore.load();
        		var productsList = Ext.getCmp('productslist');
        		var mainContainer = Ext.getCmp('mainContainer');
        		setTimeout(function() {
        			productsList.reconfigure(productBufferedStore);
        			mainContainer.updateLayout();
        		}, 500);
        	}
        }
	}
});

Ext.define('Posis.view.ProductFormWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.productformwindow',
	width: 375,
	layout: 'fit',
	modal: true
});

Ext.define('Posis.view.ProductForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.productform',
	margin: 5,
	border: false,
	defaultType: 'textfield',
	items: [{
		id: 'productName',
		fieldLabel: 'Product Name',
		name: 'name',
		allowBlank: false
	}, {
		xtype: 'combo',
		store: 'ProductTypeStore',
		name: 'productType',
		id: 'productCategoryComboBox',
		displayField: 'name',
		valueField: 'id',
		width: 340,
		fieldLabel: 'Choose Category',
		pageSize: 5,
		enableKeyEvents: true,

		listeners: {
			keyup: function(combo, event) {
				console.log("key press: " + combo.getRawValue() + " keypress: " + event.getKey());
				var searchText = combo.getRawValue();
				if (!combo.isExpanded && event.getKey() != 27) combo.expand();
        		productTypeBufferedStore.getProxy().url = '/posis/endproduct/type/?size=5&sort=name&search=' + encodeURIComponent(searchText);
        		productTypeBufferedStore.load();
        		setTimeout(function() {
        			combo.bindStore(productTypeBufferedStore);
        		}, 500);							
			},
			afterRender: function(combo) {
				if (!combo.isExpanded) {
					productTypeBufferedStore.getProxy().url = '/posis/endproduct/type/?size=5&sort=name';
					productTypeBufferedStore.load();
					combo.bindStore(productTypeBufferedStore);
				}
			}
		}
	}, {
		id: 'productTags',
		xtype: 'tagfield',
		fieldLabel: 'Tags',
		store: 'TagStore',
		displayField: 'name',
		valueField: 'id',
		width: 340,
		pageSize: 5,
		name: 'tags',
		enableKeyEvents: true,
		listeners: {
			keyup: function(tagfield, event) {
				console.log(tagfield.getValue());
				console.log(event.getKey());
				console.log(tagfield.inputEl.dom.value);
				var searchText = tagfield.inputEl.dom.value;				
				if (!tagfield.isExpanded && event.getKey() != 27) tagfield.expand();
				tagBufferedStore.getProxy().url = '/posis/tags/?size=5&sort=name&search=' + encodeURIComponent(searchText);
				tagBufferedStore.load();
				setTimeout(function() {
					tagfield.bindStore(tagBufferedStore);
				}, 500);				
			}
		}
	}, {
		id: 'productId',
		name: 'id',
		hidden: true
	}],
	buttons: [{
		id: 'productFormOkButton',
		text: 'SUBMIT'
	}]	
});

Ext.define('Posis.controller.ProductController', {
	extend: 'Ext.app.Controller',
	views: ['ProductFormWindow', 'ProductsList'],
	refs: [{
		ref: 'productFormWindow',
		xtype: 'productformwindow',
		selector: 'productformwindow',
		autoCreate: true
	}, {
		ref: 'productsList',
		xtype: 'productslist',
		selector: 'productslist'
	}],
	init: function() {
		this.control({
			'productslist > toolbar > button[action=add]': {
				click: this.showProductForm
			},
			'productslist': {
				itemdblclick: this.onRowDblClick
			},
			'productformwindow button[action=add]': {
				click: this.createProduct
			},
			'productformwindow button[action=edit]': {
				click: this.updateProduct
			},
			'productslist > toolbar > textfield[action=search]': {
				keyup: this.searchProduct
			}
		});
	},
	showProductForm: function() {
		var productform = new Posis.view.ProductForm();
		var productformwindow = this.getProductFormWindow({
			title: 'Create Product',
			items: [productform]
		});
		Ext.getCmp('productFormOkButton').action = 'add';
		productformwindow.show();
	},
	onRowDblClick: function(metaData, record, item, index) {
		var productId = record.data.id;
		var productform = new Posis.view.ProductForm();
		var productformwindow = this.getProductFormWindow({
			title: 'Update Product',
			items: [productform]
		});

		Ext.Ajax.request({
			url: '/posis/products/searchById?id=' + encodeURIComponent(productId),
			method: 'GET',

			success: function(response) {
				var jsonResponse = JSON.parse(response.responseText);
				if (jsonResponse.status == 200) {
					Ext.getCmp('productName').setValue(jsonResponse.data.name);
					Ext.getCmp('productCategoryComboBox').setValue(jsonResponse.data.productType.id);
					var tagsValues = [];
					jsonResponse.data.productTags.forEach(function(eachTag) {
						tagsValues.push(eachTag.id);
					});
					Ext.getCmp('productTags').setValue(tagsValues);		
					Ext.getCmp('productId').setValue(productId);
					Ext.getCmp('productFormOkButton').action = 'edit';
					productformwindow.show();			
				}
			}
		});
	},
	createProduct: function() {
		var productformwindow = this.getProductFormWindow();
		var productform = productformwindow.down('form').getForm();
		var productformdata = productform.getValues();
		var pagingtoolbar = Ext.getCmp('productPaingToolbar');
		if (productform.isValid()) {
			var productData = {};
			productData.productTags = [];
			productData.productType = {};
			productData.name = productformdata.name;
			productData.productType.id = productformdata.productType;
			productformdata.tags.forEach(function(eachTagID) {
				productData.productTags.push({"id": eachTagID });
			});

			console.log(productData);
			Ext.Ajax.request({
				url: '/posis/products/',
				method: 'PUT',
				jsonData: productData,

				success: function(response) {
					var jsonResponse = JSON.parse(response.responseText);
					if (jsonResponse.status == 201) {
						productformwindow.close();
						Ext.Msg.alert('Success', jsonResponse.prompt, function() { pagingtoolbar.doRefresh(); });
						return
					}
					Ext.Msg.alert('Notice', jsonResponse.prompt, Ext.emptyFn);
				},
				failure: function(response) {
					Ext.Msg.alert('Error', 'Something went wrong.', Ext.emptyFn);
				}
			});
		}
	},
	updateProduct: function() {
		var productformwindow = this.getProductFormWindow();
		var productform = productformwindow.down('form').getForm();
		var productformdata = productform.getValues();
		var pagingtoolbar = Ext.getCmp('productPaingToolbar');
		if (productform.isValid()) {
			var productData = {};
			productData.productTags = [];
			productData.productType = {};
			productData.id = productformdata.id;
			productData.name = productformdata.name;
			productData.productType.id = productformdata.productType;
			productformdata.tags.forEach(function(eachTagID) {
				productData.productTags.push({"id": eachTagID});
			});

			Ext.Ajax.request({
				url: '/posis/products/',
				method: 'POST',
				jsonData: productData,

				success: function(response) {
					var jsonResponse = JSON.parse(response.responseText);
					if (jsonResponse.status == 200) {
						productformwindow.close();
						Ext.Msg.alert('Success', jsonResponse.prompt, function() { pagingtoolbar.doRefresh(); });
						return;
					}
					Ext.Msg.alert('Notice', jsonResponse.prompt, Ext.emptyFn);
				}, 
				failure: function(response) {
					Ext.Msg.alert('Error', 'Something went wrong.', Ext.emptyFn);
				}
			});
		}
	},
	searchProduct: function() {
		var searchText = Ext.getCmp('productSearchField').getValue();
		var productStore = new Posis.store.ProductStore();
		productStore.getProxy().url = PRODUCT_URL + '&search=' + encodeURIComponent(searchText);
		productStore.load();
		var pagingtoolbar = Ext.getCmp('productPaingToolbar');
		var productsList = this.getProductsList();
		setTimeout(function() {
			productsList.reconfigure(productStore);
			productBufferedStore.totalCount = productsList.getTotalCount();
			pagingtoolbar.onLoad();
		}, 500);
	}
});