package org.apache.guacamole.auth.ldap.connection;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.GuacamoleSecurityException;
import org.apache.guacamole.net.auth.AuthenticatedUser;
import org.apache.guacamole.net.auth.Connection;
import org.apache.guacamole.net.auth.Directory;

import com.google.inject.Inject;

public class LDAPConnectionDirectory implements Directory<Connection> {

	/**
     * Service for managing connection objects.
     */
    @Inject
    private ConnectionService connectionService;
    
    @Inject
    private LdapNetworkConnection ldapConnection;
    
    private AuthenticatedUser user;
    
    public LDAPConnectionDirectory(AuthenticatedUser user, LdapNetworkConnection ldapConnection) {
		this.user = user;
		this.ldapConnection = ldapConnection;
	}

	@Override
	public Connection get(String identifier) throws GuacamoleException {
		return connectionService.findById(ldapConnection, identifier);
	}

	@Override
	public Collection<Connection> getAll(Collection<String> identifiers) throws GuacamoleException {
		return connectionService.findById(ldapConnection, identifiers);
	}

	@Override
	public Set<String> getIdentifiers() throws GuacamoleException {
		return connectionService.findAll(ldapConnection);
	}

	@Override
	public void add(Connection object) throws GuacamoleException {
		throw new GuacamoleSecurityException("LDAP Write support not implemented.");
	}

	@Override
	public void update(Connection object) throws GuacamoleException {
		throw new GuacamoleSecurityException("LDAP Write support not implemented.");
	}

	@Override
	public void remove(String identifier) throws GuacamoleException {
		throw new GuacamoleSecurityException("LDAP Write support not implemented.");
	}
    
    
}
