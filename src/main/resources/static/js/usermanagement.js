Ext.define('Posis.model.UserModel', {
	extend: 'Ext.data.Model',
	fields: ['id', 'firstName', 'lastName', 'username', 'status', 'createdBy', 'createdDate', 'lastModifiedBy', 'lastModifiedDate'],
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
	pageSize: 5,
	// data: data,
	// proxy: {
	// 	type: 'memory'
	// }
	proxy: {
		type: 'ajax',
		url: '/posis/users/?size=5&sort=id',
		reader: {
			type: 'json',
			root: 'data',
			idProperty: 'data.id',
			totalProperty: 'total'
		}
	}	
});

var bufferedStore = new Posis.store.UserStore();
bufferedStore.getProxy().url = '/posis/users/?size=5&sort=id';		
bufferedStore.load();

Ext.define('Posis.view.UsersList', {
	extend: 'Ext.grid.Panel',
	title: 'End Users',
	alias: 'widget.userslist',
	id: 'userslist',
	store: 'UserStore',
	height: 250,
	scrollable: true,
	pageSize: 5,

	tbar: [{
		xtype: 'textfield',
		id: 'userSearchField',
		enableKeyEvents : true,
		emptyText: 'Enter Search',
		action: 'search'
	}, {
		xtype: 'button',
		text: '+ Add User',
		action: 'add'
	}],

	columns: [{
		header: 'ID',
		dataIndex: 'id'
	}, {
		header: 'FULL NAME',
		renderer: function(value, metaData, record, rowId, colId, store, view) {
			return record.get('lastName') + ", " + record.get("firstName");
		}
	}, {
		header: 'STATUS',
		dataIndex: 'status'
	}, {
		header: 'USERNAME',
		dataIndex: 'username'
	}, {
		header: 'ROLE ID',
		renderer: function(value, metaData, record, rowId, coldId, store, view) {
			values = [];
			record.roles().each(function(role) {
				values.push(role.get('id'));
			});
			return values.join("<br/>");
		}
	}, {
		header: 'ROLES',
		renderer: function(value, metaData, record, rowId, colId, store, view) {
			values = [];
			record.roles().each(function(role) {
				values.push(role.get('role'));
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
		dataIndex: 'lastModifiedBy',
		width: 130
	}, {
		header: 'LAST MODIFIED DATE',
		dataIndex: 'lastModifiedDate',
		width: 150
	}, {
		header: 'ACTION',
		renderer: function(value, metaData, record) {
			var id = Ext.id();
			if (record.get('status') == 'ENABLED') {
				Ext.defer(function() {
					Ext.widget('image', {
						renderTo: id,
						name: 'disableUser',
						src: '/posis/images/disable.png',
						height: 13,
						width: 13,
						listeners: {
							afterrender: function(me) {
								me.getEl().on('click', function() {
									var grid = Ext.ComponentQuery.query('userslist')[0];
									if (grid) {
										var sm = grid.getSelectionModel();
										var rs = sm.getSelection();
										if (!rs.length) {
											Ext.Msg.alert('Info', 'No User is Selected');
											return;
										}
										Ext.Msg.confirm('Disable User', 
											'Are you sure you want to disable this user?',
											function(button) {
												if (button == 'yes') {
													disableUser(record.data.username);
												}
										});
									}
								});
							}
						}
					});
				}, 50);
				return Ext.String.format('<div id="{0}"></div>', id);
			}
		}
	}],
    bbar: {
        xtype: 'pagingtoolbar',
        id: 'userPagingToolbar',
        store: bufferedStore,
        displayInfo: true,
        displayMsg: 'Displaying {0} to {1} of {2} &nbsp;records ',
        emptyMsg: "No records to display&nbsp;",
        listeners: {
        	beforechange: function(pagingtoolbar, page, eOpts) {
        		var searchText = Ext.getCmp('userSearchField').getValue();
        		bufferedStore.getProxy().url = '/posis/users/?size=5&sort=id' 
        			+ '&page=' + encodeURIComponent(page) + '&search=' + encodeURIComponent(searchText);
        		bufferedStore.load();
        		var usersList = Ext.getCmp('userslist');
        		var mainContainer = Ext.getCmp('mainContainer');
        		setTimeout(function() {
        			usersList.reconfigure(bufferedStore);
        			mainContainer.updateLayout();
        		}, 500);
        	}
        }
    }	

});

function disableUser(username) {
	var pagingtoolbar = Ext.getCmp('userPagingToolbar');	
	Ext.Ajax.request({
		url: '/posis/users/searchByUsername',
		method: 'GET',
		params: {"username": username},

		success: function(response) {
			var jsonResponse = JSON.parse(response.responseText);				
			if (jsonResponse.status == 200) {
				var userData = jsonResponse.data;
				userData.status = 'DISABLED';
				Ext.Ajax.request({
					url: '/posis/users/disableUser',
					method: 'POST',
					jsonData: userData,

					success: function(response) {
						jsonResponse = JSON.parse(response.responseText);
						if (jsonResponse.status == 200) {
							Ext.Msg.alert('Success', jsonResponse.prompt, Ext.emptyFn);
							pagingtoolbar.doRefresh();
						} else {
							Ext.Msg.alert('Notice', jsonResponse.prompt, Ext.emptyFn);
						}
					}
				});								
			}
		}
	});	
}

Ext.define('Posis.view.UserFormWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.userformwindow',
	width: 300,
	layout: 'fit',
	modal: true
});

Ext.define('Posis.view.UserForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.userform',
	margin: 5,
	border: false,
	defaultType: 'textfield',
	items: [{
		fieldLabel: 'First Name',
		name: 'firstName',
		allowBlank: false
	}, {
		fieldLabel: 'Last Name',
		name: 'lastName',
		allowBlank: false
	}, {
		fieldLabel: 'Username',
		id: 'username',
		name: 'username',
		allowBlank: false
	}, {
		inputType: 'password',
		fieldLabel: 'Password',
		name: 'password',
		allowBlank: false
	}, {
		inputType: 'password',
		fieldLabel: 'Cofirm Password',
		name: 'confirmpassword',
		allowBlank: false
	}, {
		xtype: 'checkboxgroup',
		fieldLabel: 'access level',
		horizontal: true,
		items: [{
			id: 'isUser',
			boxLabel: 'User', 
			name: 'isUser',
			inputValue: 1,
			checked: true
		}, {
			id: 'isAdmin',
			boxLabel: 'Admin',
			name: 'isAdmin',
			inputValue: 2
		}]
	}, {
		id: 'userStatus',
		hidden: true
	}],
	buttons: [{
		id: 'userFormOkButton',
		text: 'SUBMIT',
	}, {
		text: 'RESET',
		handler: function() {
			this.up('form').reset();
		}
	}]		
});

Ext.define('Posis.controller.UserController', {
	extend: 'Ext.app.Controller',
	views: ['UserFormWindow', 'UsersList'],
	refs: [{
		ref: 'userFormWindow',
		xtype: 'userformwindow',
		selector: 'userformwindow',
		autoCreate: true
	}, {
		ref: 'usersList',
		xtype: 'userslist',
		selector: 'userslist'
	}],
	init: function() {
		this.control({
			'userslist > toolbar > button[action=add]' : {
				click: this.showUserForm
			},
			'userslist': {
				itemdblclick: this.onRowDblClick
			},
			'userformwindow button[action=add]': {
				click: this.createUser
			},
			'userformwindow button[action=edit]': {
				click: this.updateUser
			},
			'userslist > toolbar > textfield[action=search]': {
				keyup: this.searchUser
			}
		});		
	}, 
	showUserForm: function() {
		console.log("show user form clicked");
		var userform = new Posis.view.UserForm();
		var userformwindow = this.getUserFormWindow({
			title: 'Create User',
			items: [userform]
		});
		Ext.getCmp('userFormOkButton').action = 'add';
		userformwindow.show();
	},
	onRowDblClick: function(metaData, record, item, index) {
		var username = record.data.username;
		var userform = new Posis.view.UserForm();
		var userformwindow = this.getUserFormWindow({
			title: 'Update User',
			items: [userform]
		});
		Ext.getCmp('isUser').setValue(false);
		Ext.Ajax.request({
			url: '/posis/users/searchByUsername',
			method: 'GET',
			params: {"username": username},

			success: function(response) {
				var jsonResponse = JSON.parse(response.responseText);				
				if (jsonResponse.status == 200) {
					var userData = jsonResponse.data;
					userform.getForm().findField('password').fieldLabel = 'New Password';
					Ext.getCmp('username').setReadOnly(true);
					userform.getForm().setValues(userData);
					Ext.getCmp('isUser').setValue(false);
					Ext.getCmp('isAdmin').setValue(false);
					Ext.getCmp('userStatus').setValue(userData.status);
					console.log(userData.status);
					if (userData.roles[0] != null && userData.roles[0].id == 1) Ext.getCmp('isUser').setValue(true);
					if (userData.roles[1] != null && userData.roles[1].id == 2) Ext.getCmp('isAdmin').setValue(true);
					Ext.getCmp('userFormOkButton').action = 'edit';
					userformwindow.show();					
				}
			}
		});
	},
	createUser: function() {
		var userformwindow = this.getUserFormWindow();
		var userform = userformwindow.down('form').getForm();
		Ext.getCmp('username').setReadOnly(false);
		var hasPasswordMatch =
			 userform.findField('password').getValue() == userform.findField('confirmpassword').getValue();
		if (userform.isValid() && hasPasswordMatch) {
			console.log("user is to be created");
			this.executeCreateUserRequest(userform, userformwindow);
		} else if (!hasPasswordMatch) {
			Ext.Msg.alert('Notice', 'Passwords dont match.', Ext.emptyFn);
		} else {
			console.log("invalid");
		}
	},
	executeCreateUserRequest: function(userform, userformwindow) {
		var userData = {};
		userData.roles = [];
		userData = Object.assign(userData, userform.getValues());
		userData.status = "ENABLED";
		if (userData.isUser) userData.roles.push({ "id": userData.isUser });
		if (userData.isAdmin) userData.roles.push({ "id": userData.isAdmin });
		delete userData['confirmpassword'];
		delete userData['isUser'];
		delete userData['isAdmin'];
		var pagingtoolbar = Ext.getCmp('userPagingToolbar');
		Ext.Ajax.request({
			url: '/posis/users/',
			method: 'PUT',
			jsonData: userData,

			success: function(response) {
				var jsonResponse = JSON.parse(response.responseText);
				if (jsonResponse.status == 201) {
					userformwindow.close();
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

	},
	updateUser: function() {
		var userformwindow = this.getUserFormWindow();
		var userform = userformwindow.down('form').getForm();

		var hasPasswordMatch =
			 userform.findField('password').getValue() == userform.findField('confirmpassword').getValue();
		if (userform.isValid() && hasPasswordMatch) {
			console.log("user updated");
			this.executeUpdateUserRequest(userform, userformwindow);			
		} else if (!hasPasswordMatch) {
			Ext.Msg.alert('Notice', 'Passwords dont match.', Ext.emptyFn);
		} else {
			console.log("invalid");
		}
	},
	executeUpdateUserRequest: function(userform, userformwindow) {
		var userData = {};
		userData.roles = [];
		userData = Object.assign(userData, userform.getValues());
		userData.status = Ext.getCmp('userStatus').getValue()
		if (userData.isUser) userData.roles.push({ "id": userData.isUser });
		if (userData.isAdmin) userData.roles.push({ "id": userData.isAdmin });
		delete userData['confirmpassword'];
		delete userData['isUser'];
		delete userData['isAdmin'];
		var pagingtoolbar = Ext.getCmp('userPagingToolbar');		
		Ext.Ajax.request({
			url: '/posis/users/',
			method: 'POST',
			jsonData: userData, 

			success: function(response) {
				var jsonResponse = JSON.parse(response.responseText);
				if (jsonResponse.status == 200) {
					userformwindow.close();
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
	},
	searchUser: function() {
		var searchText = Ext.getCmp('userSearchField').getValue();
		var userStore = new Posis.store.UserStore();
		userStore.getProxy().url = '/posis/users/?search=' + encodeURIComponent(searchText) + "&size=5&sort=id";		
		var pagingtoolbar = Ext.getCmp('userPagingToolbar');
		userStore.load(); 
		var usersList = this.getUsersList();
		setTimeout(function() { 						
			usersList.reconfigure(userStore);
			bufferedStore.totalCount = userStore.getTotalCount();
			pagingtoolbar.onLoad();			
		}, 500);
	}
});