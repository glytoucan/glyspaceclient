package org.glyspace.client;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.database.UserEntity;
import org.glyspace.registry.view.Confirmation;
import org.glyspace.registry.view.RoleList;
import org.glyspace.registry.view.User;
import org.glyspace.registry.view.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component(value = "userRestClient")
public class UserRestClientImpl implements UserRestClient{
	// This is relative to the rootUrl (defined in the configuration file)
	private final static String userServiceUrl = "/users";
	
	private String username;
	private String password;
	private String rootUrl;

	@Autowired
	private RestTemplate restTemplate;

	static HttpHeaders getHeaders(String auth) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_XML);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));

	    byte[] encodedAuthorisation = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
	    headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

	    return headers;
	 }

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @param url the url to set
	 */
	public void setRootUrl(String url) {
		this.rootUrl = url;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserEntity getUser(String loginName) throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		ResponseEntity<UserEntity> responseEntity = this.restTemplate.exchange(rootUrl + userServiceUrl + "/get/" + loginName, HttpMethod.GET, requestEntity, UserEntity.class);
		return responseEntity.getBody();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public UserList getUserList() throws CustomClientException {
		
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		ResponseEntity<UserList> responseEntity = this.restTemplate.exchange(rootUrl + userServiceUrl + "/list", HttpMethod.GET, requestEntity, UserList.class);
		return responseEntity.getBody();
	}
	
	@Override
	public Confirmation addUser (User user) throws CustomClientException {		
		HttpEntity<User> requestEntity = new HttpEntity<User>(user,
		        getHeaders(""));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + userServiceUrl + "/add", HttpMethod.POST, requestEntity, Confirmation.class);
	           // .postForEntity(userServiceUrl + "add", requestEntity, String.class );
		System.out.println("Response Status : " + responseEntity.getStatusCode());
		final HttpHeaders headers = responseEntity.getHeaders();
	    System.out.println("headers in response are : " + headers);
		
		return responseEntity.getBody();
	}
	
	/**
	 * Changes the password for the current user with "username" and "password" (defined in client.properties)
	 * @param newPassword
	 * @return the response message
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Confirmation changePassword (String newPassword) throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(newPassword,
		        getHeaders(username + ":" + password));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + userServiceUrl +  "/" + username + "/password", HttpMethod.PUT, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}
	
	@Override
	public String recoverUsername(String email) throws CustomClientException {
		String response = this.restTemplate.getForObject (rootUrl + userServiceUrl + "/recover?email=" + email, String.class);
		return response;
	}

	@Override
	public Confirmation recoverPassword(String username) throws CustomClientException {
		Confirmation response = this.restTemplate.getForObject (rootUrl + userServiceUrl + "/" + username + "/password", Confirmation.class);
		return response;
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Confirmation validateUser(Integer userId) throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + userServiceUrl + "/" + userId + "/validate", HttpMethod.PUT, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Confirmation activateUser(Integer userId) throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + userServiceUrl + "/" + userId + "/activate", HttpMethod.PUT, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Confirmation deactivateUser(Integer userId) throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + userServiceUrl + "/" + userId + "/deactivate", HttpMethod.PUT, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserEntity updateUser(User user, Integer userId)
			throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(user,
		        getHeaders(username + ":" + password));
		final ResponseEntity<UserEntity> responseEntity = this.restTemplate.exchange (rootUrl + userServiceUrl + "/" + userId + "/update", HttpMethod.POST, requestEntity, UserEntity.class);
		return responseEntity.getBody();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Confirmation promoteUser(Integer userId, String rolename) throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(rolename,
		        getHeaders(username + ":" + password));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + userServiceUrl + "/" + userId + "/role", HttpMethod.POST, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Confirmation demoteUser(Integer userId, String rolename) throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + userServiceUrl + "/" + userId + "/role/" + rolename, HttpMethod.DELETE, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RoleList listRoles() throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		ResponseEntity<RoleList> listResponse = this.restTemplate.exchange (rootUrl + userServiceUrl + "/roles/list", HttpMethod.GET, requestEntity, RoleList.class);
		return listResponse.getBody();
	}
}
