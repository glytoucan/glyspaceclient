package org.glyspace.client;

import org.glyspace.registry.view.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class ContributorRestClientImpl implements ContributorRestClient {
	String rootUrl;
	
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
	
	@Autowired
	RestTemplate restTemplate;
	
	// This is relative to the rootUrl (defined in the configuration file)
	private final static String contributorServiceUrl = "/contributor";
	
	@Override
	public Contributor getContributor(String userName) {
		return this.restTemplate.getForObject(rootUrl + contributorServiceUrl + "/" + userName, Contributor.class);
	}

}
