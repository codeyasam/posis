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

Ext.define('Posis.view.ProductTypeList', {
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
        		var producttypeslist = Ext.getCmp('producttypeslist');
        		var mainContainer = Ext.getCmp('mainContainer');
        		setTimeout(function() {
        			producttypeslist.reconfigure(productTypeBufferedStore);
        			mainContainer.updateLayout();
        		}, 500);
        	}
        }
    }	
});