package org.glyspace.client;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.view.Confirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component(value = "setttingsRestClient")
public class SettingsRestClientImpl implements SettingsRestClient {
	String username;
	String password;
	String rootUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	// This is relative to the rootUrl (defined in the configuration file)
	private final static String settingsServiceUrl = "/settings";
	
	static HttpHeaders getHeaders(String auth) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	    byte[] encodedAuthorisation = Base64.encodeBase64(auth.getBytes());
	    headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

	    return headers;
	 }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Confirmation setDelay(long newDelay) throws CustomClientException {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));

	    byte[] encodedAuthorisation = Base64.encodeBase64((username + ":" + password).getBytes());
	    headers.add("Authorization", "Basic " + new String(encodedAuthorisation));
		HttpEntity requestEntity = new HttpEntity(new Long(newDelay), 
				headers);
		ResponseEntity<Confirmation> resp = this.restTemplate.exchange (rootUrl + settingsServiceUrl + "/delay", HttpMethod.PUT, requestEntity, Confirmation.class);
		return resp.getBody();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long getDelay() throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(getHeaders(username + ":" + password));
		ResponseEntity<Long> delayResponse = this.restTemplate.exchange (rootUrl + settingsServiceUrl + "/delay", HttpMethod.GET, requestEntity, Long.class);
		return delayResponse.getBody();
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
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the rootUrl
	 */
	public String getRootUrl() {
		return rootUrl;
	}
	/**
	 * @param rootUrl the rootUrl to set
	 */
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

}
