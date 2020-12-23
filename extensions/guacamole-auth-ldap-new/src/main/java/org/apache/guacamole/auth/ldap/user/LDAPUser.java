package org.apache.guacamole.auth.ldap.user;

import java.util.Collections;
import java.util.Map;

import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.auth.ldap.LDAPAuthenticationProvider;
import org.apache.guacamole.auth.ldap.connection.ConnectionService;
import org.apache.guacamole.auth.ldap.group.UserGroupService;
import org.apache.guacamole.net.auth.AbstractUser;
import org.apache.guacamole.net.auth.AuthenticatedUser;
import org.apache.guacamole.net.auth.Connection;
import org.apache.guacamole.net.auth.Directory;
import org.apache.guacamole.net.auth.User;
import org.apache.guacamole.net.auth.UserGroup;
import org.apache.guacamole.net.auth.permission.ObjectPermissionSet;
import org.apache.guacamole.net.auth.simple.SimpleDirectory;
import org.apache.guacamole.net.auth.simple.SimpleObjectPermissionSet;

import com.google.inject.Inject;

public class LDAPUser extends AbstractUser {
	
	/**
     * Service for retrieving Guacamole connections from the LDAP server.
     */
    @Inject
    private ConnectionService connectionService;

    /**
     * Service for retrieving Guacamole users from the LDAP server.
     */
    @Inject
    private UserService userService;

    /**
     * Service for retrieving user groups.
     */
    @Inject
    private UserGroupService userGroupService;
    
    /**
     * User from a user directory which has been authenticated
     */
    private AuthenticatedUser user;
    
    private LdapNetworkConnection ldapConnection;
    
    public LDAPUser(AuthenticatedUser user, LdapNetworkConnection ldapConnection) {
		super.setIdentifier(user.getIdentifier());
		this.user = user;
		this.ldapConnection = ldapConnection;
	}
    
    //TODO: Implement cache?
    @Override
    public ObjectPermissionSet getUserPermissions() throws GuacamoleException {
    	// Query all accessible users
    	Directory<User> userDirectory = new SimpleDirectory<>(
            userService.getUsers(ldapConnection)
        );
        return new SimpleObjectPermissionSet(userDirectory.getIdentifiers());
    }

    //TODO: Implement cache?
    @Override
    public ObjectPermissionSet getUserGroupPermissions() throws GuacamoleException {
    	// Query all accessible user groups
    	Directory<UserGroup> userGroupDirectory = new SimpleDirectory<>(
            userGroupService.getUserGroups(ldapConnection)
        );
        return new SimpleObjectPermissionSet(userGroupDirectory.getIdentifiers());
    }

    //TODO: Implement cache?
    @Override
    public ObjectPermissionSet getConnectionPermissions() throws GuacamoleException {
    	// Query all accessible user groups
    	Directory<Connection> connectionDirectory = new SimpleDirectory<>(
            connectionService.getConnections(user, ldapConnection)
        );
        return new SimpleObjectPermissionSet(connectionDirectory.getIdentifiers());
    }
    
    //TODO: Implement cache?
    @Override
    public ObjectPermissionSet getConnectionGroupPermissions() throws GuacamoleException {
        return new SimpleObjectPermissionSet(Collections.singleton(LDAPAuthenticationProvider.ROOT_CONNECTION_GROUP));
    }

    @Override
    public Map<String, String> getAttributes() {
    	// TODO Auto-generated method stub, replace with query to ldap user-dn with mapping from config-file
    	// TODO Alternative: ldap-property: guacUserAttribute with value <name,base64>:<value,base64>
    	return super.getAttributes();
    }
    
    @Override
    public void setAttributes(Map<String, String> attributes) {
    	// TODO Auto-generated method stub, implement write support.
    	super.setAttributes(attributes);
    }
}
