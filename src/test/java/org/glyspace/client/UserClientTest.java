package org.glyspace.client;


import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.database.RoleEntity;
import org.glyspace.registry.database.UserEntity;
import org.glyspace.registry.view.Confirmation;
import org.glyspace.registry.view.RoleList;
import org.glyspace.registry.view.User;
import org.glyspace.registry.view.UserList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Unit test for UserRestClient
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class UserClientTest 
{
	@Autowired
    private UserRestClient userRestClient;
	
	@Before
	public void init() {
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3st");
	}

	@Test
    public void testGetUser()
    {
       String username="test";
       UserEntity user = userRestClient.getUser(username);
       assertNotNull(user);
       assertEquals(user.getLoginId(), username);
    }
	
	@Test
	public void testGetUserRole() {
		String username = "test";
		UserEntity user = userRestClient.getUser(username);
		Set<RoleEntity> entities = user.getRoles();
		for (Iterator iterator = entities.iterator(); iterator.hasNext();) {
			RoleEntity roleEntity = (RoleEntity) iterator.next();
			assertEquals(roleEntity.getRoleName(), RoleEntity.ADMIN);
		}
	}

	@Test
	public void testAddUser() 
	{
		UserList list = userRestClient.getUserList();
		for (Iterator iterator = list.getUsers().iterator(); iterator.hasNext();) {
			UserEntity testUser = (UserEntity) iterator.next();
			if (testUser.getLoginId().equals("test")) {
				// try to add a duplicate user
				try {
					User user = new User();
			    	user.setLoginId("test");
			    	user.setAffiliation("Glyspace.org");
			    	user.setEmail("testglyspace@gmail.com");
			    	user.setPassword("t3$t");
			    	user.setFullName("Test User");
			    	userRestClient.addUser(user);
			    	assertFalse("Duplicate, should no be allowed", true);   // fail 
				} catch (CustomClientException ce2) { // should come here
					assertTrue ("Conflict expected: duplicate user", ce2.getStatusCode() == HttpStatus.CONFLICT);
					System.out.println (ce2.getMessage());
				}
				
				return;
			}
		}
		
		// if not found, add the user
		try {
			// try with invalid field
			User user = new User();
	    	user.setLoginId("test");
	    	user.setAffiliation("Glyspace.org");
	    	user.setEmail("testglyspace@gmail.com");
	    	user.setPassword("pass");
	    	user.setFullName("Test User");
	        userRestClient.addUser(user);
	        assertFalse ("Should never add this user", true);
		} catch (CustomClientException ce) {
			assertTrue("Fail with password validation error", ce.getStatusCode()==HttpStatus.BAD_REQUEST);
			System.out.println (ce.getMessage());
		}
		
		User user = new User();
    	user.setLoginId("test");
    	user.setAffiliation("Glyspace.org");
    	user.setEmail("testglyspace@gmail.com");
    	user.setPassword("t3$t");
    	user.setFullName("Test User");
    	Confirmation message = userRestClient.addUser(user);
    	assertEquals ("Added user", message.getMessage(), "User added successfully");
					
	}
	
	@Test
	public void testValidate() {
		testAddUser();
		UserList list = userRestClient.getUserList();
		for (Iterator iterator = list.getUsers().iterator(); iterator.hasNext();) {
			UserEntity testUser = (UserEntity) iterator.next();
			if (testUser.getLoginId().equals("test")) {
				// test validate with no authorization
				userRestClient.setUsername("test");
				userRestClient.setPassword("t3$t");
				try {
					userRestClient.validateUser(testUser.getUserId());
					testUser = userRestClient.getUser(testUser.getLoginId());
				} catch (CustomClientException ce) {
					assertTrue("Should receive unauthorized exception", ce.getStatusCode() == HttpStatus.FORBIDDEN || ce.getStatusCode() == HttpStatus.UNAUTHORIZED);
				}
				
				// with correct authorization
				userRestClient.setUsername("test");
				userRestClient.setPassword("t3st");
				userRestClient.validateUser(testUser.getUserId());
			}
		}
	}
	
	@Test
	public void testUpdateUser () {
		testValidate();
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3$t");
		UserEntity user = userRestClient.getUser("test");
		User updatedUser = new User();
		updatedUser.setFullName("My Test User");
		userRestClient.updateUser(updatedUser, user.getUserId());
		UserEntity user2 = userRestClient.getUser("test");
		assertEquals (user2.getUserName(), "My Test User");
		assertEquals (user2.getAffiliation(), user.getAffiliation());
		assertEquals (user2.getEmail(), user.getEmail());
	}
	
	@Test
	public void testChangePassword () {
		testValidate();
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3$t");
		userRestClient.changePassword("t3$t2");
		try {
			userRestClient.changePassword("t3$t3");
			// should fail since we've changed the password
			assertTrue(false);
		} catch (CustomClientException ce) {
			assertTrue(true);
			userRestClient.setUsername("test");
			userRestClient.setPassword("t3$t2");
			// change it back to the original
			userRestClient.changePassword("t3$t");
		}
	}
	
	@Test
	public void testPromoteDemoteUser () {
		testValidate();
		// add a new role to "test" user
		UserEntity user = userRestClient.getUser("test");
		userRestClient.promoteUser(user.getUserId(), "MODERATOR");
		// check if we can do something that only moderators can do
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3$t");
		userRestClient.validateUser(user.getUserId());
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3st");
		userRestClient.demoteUser(user.getUserId(), "MODERATOR");
		// now trying to validate should fail
		try {
			userRestClient.setUsername("test");
			userRestClient.setPassword("t3$t");
			userRestClient.validateUser(user.getUserId());
			assertTrue(false);
		} catch (CustomClientException ce) {
			assertTrue("No more a moderator, cannot validate", true);
		}
	}
	
	@Test (expected = CustomClientException.class)
	public void testDemotetest () {
		UserEntity test = userRestClient.getUser("test");
		userRestClient.demoteUser(test.getUserId(), "test");
	}
	
	
	@Test
	public void testRecoverUsername () {
		testValidate();
		String username=userRestClient.recoverUsername("testglyspace@gmail.com");
		assertEquals(username, "test");
	}
	
//	@Test
	public void testRecoverPassword() {
		testValidate();
		userRestClient.recoverPassword("test");
		assertTrue(true);
	}
	
	@Test
	public void testDeactivateUser () {
		testValidate();
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3st");
		UserEntity user = userRestClient.getUser("test");
		userRestClient.deactivateUser(user.getUserId());
		user = userRestClient.getUser("test");
		assertFalse(user.getActive());	
	
	}
	
	@Test(expected = CustomClientException.class)
	public void testActivateUserWithNoAuthorization () 
	{
		testValidate();
		UserEntity user = userRestClient.getUser("test");
		userRestClient.setUsername("test");
		userRestClient.setPassword("pass");
		userRestClient.deactivateUser(user.getUserId());
	}
	
	@Test
	public void testActivateUserWithAuthorization() {
		testValidate();
		UserEntity user = userRestClient.getUser("test");
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3st");
		userRestClient.activateUser(user.getUserId());
	}
	
	@Test
	public void testUserListWithAuthorization () {
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3st");
		UserList list = userRestClient.getUserList();
		assertTrue(list.getUsers().size() > 1);   // at least test should be there
	}
	
	@Test (expected = CustomClientException.class )
	public void testUserListWithNoAuthorization () {
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3$t");
		userRestClient.getUserList();
	}
	
	@Test
	public void testGetRoles () {
		userRestClient.setUsername("test");
		userRestClient.setPassword("t3st");
		RoleList list = userRestClient.listRoles();
		assertTrue(list.getRoleNames().size() > 2);   // at least 3 roles should be there
	}
}
