package org.glyspace.client;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.database.MotifEntity;
import org.glyspace.registry.database.MotifSequence;
import org.glyspace.registry.view.Confirmation;
import org.glyspace.registry.view.MotifEntityList;
import org.glyspace.registry.view.MotifInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MotifRestClientImpl implements MotifRestClient {
	String username;
	String password;
	String rootUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	// This is relative to the rootUrl (defined in the configuration file)
	private final static String motifServiceUrl = "/motifs";
	
	static HttpHeaders getHeaders(String auth) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_XML);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));

	    byte[] encodedAuthorisation = Base64.encodeBase64(auth.getBytes());
	    headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

	    return headers;
	 }
	
	@Override
	public void setUsername(String username) {
		this.username = username;

	}

	@Override
	public void setPassword(String password) {
		this.password=password;

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

	@Override
	public Confirmation addMotif(MotifInput motif) throws CustomClientException {
		HttpEntity<MotifInput> requestEntity = new HttpEntity<MotifInput>(motif,
		        getHeaders(username + ":" + password));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + motifServiceUrl + "/add", 
				HttpMethod.POST, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@Override
	public Confirmation addMotifSequence(String motifName,
			MotifSequence sequence) throws CustomClientException {
		HttpEntity<MotifSequence> requestEntity = new HttpEntity<MotifSequence>(sequence,
		        getHeaders(username + ":" + password));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + motifServiceUrl + "/add/" + motifName + "/sequence", 
				HttpMethod.POST, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@Override
	public Confirmation addMotifTag(String motifName, String tag)
			throws CustomClientException {
		HttpEntity<String> requestEntity = new HttpEntity<String>(tag,
		        getHeaders(username + ":" + password));
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (rootUrl + motifServiceUrl + "/add/" + motifName + "/tag", 
				HttpMethod.POST, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Confirmation updateMotifSequenceSetReducing(Integer sequenceId,
			Boolean reducing) throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		String url = rootUrl + motifServiceUrl + "/update/" + sequenceId + "/reducing?value=";
		if (reducing != null) {
			url += reducing;
		}
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (url, 
				HttpMethod.PUT, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@Override
	public MotifEntity getMotif(String motifName) throws CustomClientException {
		MotifEntity motif = this.restTemplate.getForObject(rootUrl + motifServiceUrl + "/get?motifName=" + motifName, MotifEntity.class);
		return motif;
	}

	@Override
	public MotifEntityList getMotifList() throws CustomClientException {
		MotifEntityList motifList = this.restTemplate.getForObject(rootUrl + motifServiceUrl + "/list", MotifEntityList.class);
		return motifList;
	}

	@Override
	public MotifEntityList findMotifs(String[] tags, String queryType)
			throws CustomClientException {
		String url = rootUrl + motifServiceUrl + "/search?";
		for (int i = 0; i < tags.length; i++) {
			url += "tag=" + tags[i];
			if (i < tags.length-1) 
				url += "&";
		}
		if (queryType != null && !queryType.isEmpty()) {
			url += "&queryType=" + queryType;
		}
		System.out.println ("url" + url);
		MotifEntityList motifList = this.restTemplate.getForObject(url, MotifEntityList.class);
		return motifList;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BufferedImage getMotifSequenceImage(Integer sequenceId,
			String format, String notation, String style)
			throws CustomClientException {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG));
		HttpEntity requestEntity = new HttpEntity(headers);
		return restTemplate.exchange(rootUrl + motifServiceUrl + "/image/" + sequenceId + "?format=" + format + "&notation=" + notation + "&style=" + style,  
				HttpMethod.GET,  requestEntity, BufferedImage.class).getBody();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getMotifSequenceSVGImage(Integer sequenceId, String notation,
			String style) throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(getHeaders(""));
		return restTemplate.exchange(rootUrl + motifServiceUrl + "/image/" + sequenceId + "?format=svg&notation=" + notation + "&style=" + style,  
					HttpMethod.GET,  requestEntity, String.class).getBody();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Confirmation deleteMotif(Integer motifId)
			throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		String url = rootUrl + motifServiceUrl + "/delete/" + motifId;
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (url, 
				HttpMethod.DELETE, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Confirmation deleteMotifSequence(Integer sequenceId)
			throws CustomClientException {
		HttpEntity requestEntity = new HttpEntity(
		        getHeaders(username + ":" + password));
		String url = rootUrl + motifServiceUrl + "/delete/sequence/" + sequenceId;
		final ResponseEntity<Confirmation> responseEntity = this.restTemplate.exchange (url, 
				HttpMethod.DELETE, requestEntity, Confirmation.class);
		return responseEntity.getBody();
	}

}
