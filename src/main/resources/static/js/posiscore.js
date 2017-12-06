Ext.define('Posis.model.UserModel', {
	extend: 'Ext.data.Model',
	fields: ['id', 'firstName', 'lastName', 'username'],
	hasMany: [{
		model: 'Posis.model.RoleModel',
		name: 'roles'
	}]
});

Ext.define('Posis.model.RoleModel', {
	extend: 'Ext.data.Model',
	fields: ['id', 'role']
});

Ext.define('Posis.store.UserStore', {
	extend: 'Ext.data.Store',
	model: 'Posis.model.UserModel',
	autoLoad: true,

	proxy: {
		type: 'ajax',
		url: '/posis/users/',
		reader: {
			type: 'json',
			root: '',
			idProperty: 'id'
		}
	}
});

Ext.define('Posis.view.UsersList', {
	extend: 'Ext.grid.Panel',
	title: 'End Users',
	alias: 'widget.userslist',
	store: 'UserStore',
	columns: [{
		header: 'ID',
		dataIndex: 'id'
	}, {
		header: 'FULL NAME',
		renderer: function(value, metaData, record, rowId, colId, store, view) {
			return record.get('lastName') + ", " + record.get("firstName");
		}
	}, {
		header: 'USERNAME',
		dataIndex
	}]
});