package org.glyspace.client;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.view.Contributor;

public interface ContributorRestClient {
	Contributor getContributor (String userName) throws CustomClientException;;
}
