package org.glyspace.client;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.database.UserEntity;
import org.glyspace.registry.view.Confirmation;
import org.glyspace.registry.view.RoleList;
import org.glyspace.registry.view.User;
import org.glyspace.registry.view.UserList;

public interface UserRestClient {
	
	void setUsername(String username);
	void setPassword(String password);
	
	Confirmation changePassword(String newPassword) throws CustomClientException;
	Confirmation addUser (User user) throws CustomClientException;
    UserList getUserList() throws CustomClientException;
	String recoverUsername (String email) throws CustomClientException;
	Confirmation recoverPassword (String username) throws CustomClientException;
	UserEntity getUser (String username) throws CustomClientException;
	
	Confirmation validateUser(Integer userId) throws CustomClientException;
	Confirmation activateUser(Integer userId) throws CustomClientException;
	Confirmation deactivateUser(Integer userId) throws CustomClientException;
	UserEntity updateUser(User user, Integer userId) throws CustomClientException;
	Confirmation promoteUser(Integer userId, String rolename) throws CustomClientException;
	Confirmation demoteUser(Integer userId, String rolename) throws CustomClientException;
	RoleList listRoles() throws CustomClientException;
}
