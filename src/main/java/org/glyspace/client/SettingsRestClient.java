package org.glyspace.client;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.view.Confirmation;

public interface SettingsRestClient {
	void setUsername(String username);
	void setPassword(String password);
	
	Confirmation setDelay (long newDelay) throws CustomClientException;
	long getDelay () throws CustomClientException;
}
