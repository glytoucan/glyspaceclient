package org.glyspace.client;

import static org.junit.Assert.assertTrue;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.view.Confirmation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class SettingsClientTest {
	
	@Autowired
	SettingsRestClient settingsRestClient;
	
	@Test
	public void getDelay() {
		settingsRestClient.setUsername("test");
		settingsRestClient.setPassword("t3st");
		assertTrue (settingsRestClient.getDelay() == 600);
	}
	
	@Test
	public void setDelay() {
		settingsRestClient.setUsername("test");
		settingsRestClient.setPassword("t3st");
		Confirmation res = settingsRestClient.setDelay(1200);
		assertTrue (res.getStatus().equals("success"));
		assertTrue (settingsRestClient.getDelay() == 1200);
		// revert back to the original
		settingsRestClient.setDelay(600);
	}
	
	@Test(expected=CustomClientException.class)
	public void testNoAuthorizationSetDelay () {
		settingsRestClient.setUsername("test");
		settingsRestClient.setPassword("test");
		settingsRestClient.setDelay(1200);
	}
	
	
}
