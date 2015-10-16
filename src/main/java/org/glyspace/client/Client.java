package org.glyspace.client;

import java.util.Iterator;
import java.util.List;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.database.GlycanEntity;
import org.glyspace.registry.database.UserEntity;
import org.glyspace.registry.view.Glycan;
import org.glyspace.registry.view.GlycanList;
import org.glyspace.registry.view.GlycanResponse;
import org.glyspace.registry.view.User;
import org.glyspace.registry.view.UserList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class Client 
{
    @SuppressWarnings("resource")
	public static void main( String[] args )
    {
    	ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
    	UserRestClient userClient = applicationContext.getBean(UserRestClientImpl.class);
    	
    	//test get users
    	UserList users = userClient.getUserList();
    	for (Iterator iterator = users.getUsers().iterator(); iterator.hasNext();) {
			UserEntity user = (UserEntity) iterator.next();
			System.out.println ("User: " + user.getUserName() + " from " + user.getAffiliation());
			
		}
    	
    	//test add user
    	try {
	    	User user = new User();
	    	user.setLoginId("test");
	    	user.setAffiliation("CCRC-UGA");
	    	user.setEmail("test@gmail.com");
	    	user.setPassword("pass");
	    	user.setFullName("test fullname");
	    	System.out.println (userClient.addUser(user)); 
    	} catch (CustomClientException e) {
    		System.out.println ("Exception: " + e.getMessage() + ", status code: " + e.getStatusCode().toString());
    	} 
    	
    	//change password
    /*	try {
    		System.out.println(userClient.changePassword("pass"));
    	} catch (CustomClientException e) {
    		System.out.println ("Exception: " + e.getMessage() + ", status code: " + e.getStatusCode().toString());
    	}*/
    	
    	// test get user
    	
    	try {
    		UserEntity userEntity = userClient.getUser("test");
    		System.out.println ("user info: " + userEntity.getUserName() + " at " + userEntity.getAffiliation());
    	} catch (CustomClientException e) {
    		System.out.println ("Exception: " + e.getMessage() + ", status code: " + e.getStatusCode().toString());
    	}
    	
    	
    	
    	GlycanRestClient glycanClient = applicationContext.getBean(GlycanRestClientImpl.class);
    	
    	// submit a structure
    	
    /*	Glycan glycan = new Glycan();
    	String structure="";
    	structure += "RES\n" +
		"1b:b-dglc-HEX-1:5\n" +
		"2s:n-acetyl\n" +
		"3b:b-dglc-HEX-1:5\n" +
		"4s:n-acetyl\n" +
		"5b:b-dman-HEX-1:5\n" +
		"6b:a-dman-HEX-1:5\n" +
		"7b:b-dglc-HEX-1:5\n" +
		"8s:n-acetyl\n" +
		"9b:b-dgal-HEX-1:5\n" +
		"10b:a-dgro-dgal-NON-2:6|1:a|2:keto|3:d\n" +
		"11s:n-glycolyl\n" +
		"12b:b-dglc-HEX-1:5\n" +
		"13s:n-acetyl\n" +
		"14b:b-dgal-HEX-1:5\n" +
		"15b:a-dgro-dgal-NON-2:6|1:a|2:keto|3:d\n" +
		"16s:n-acetyl\n" +
		"17b:a-dman-HEX-1:5\n" +
		"18b:b-dglc-HEX-1:5\n" +
		"19s:n-acetyl\n" +
		"20b:b-dgal-HEX-1:5\n" +
		"21b:a-dgro-dgal-NON-2:6|1:a|2:keto|3:d\n" +
		"22s:n-acetyl\n" +
		"23b:b-dglc-HEX-1:5\n" +
		"24s:n-acetyl\n" +
		"25b:b-dgal-HEX-1:5\n" +
		"26b:b-dglc-HEX-1:5\n" +
		"27s:n-acetyl\n" +
		"28b:a-lgal-HEX-1:5|6:d\n" +
		"LIN\n" +
		"1:1d(2+1)2n\n" +
		"2:1o(4+1)3d\n" +
		"3:3d(2+1)4n\n" +
		"4:3o(4+1)5d\n" +
		"5:5o(3+1)6d\n" +
		"6:6o(2+1)7d\n" +
		"7:7d(2+1)8n\n" +
		"8:7o(4+1)9d\n" +
		"9:9o(3+2)10d\n" +
		"10:10d(5+1)11n\n" +
		"11:6o(4+1)12d\n" +
		"12:12d(2+1)13n\n" +
		"13:12o(4+1)14d\n" +
		"14:14o(3+2)15d\n" +
		"15:15d(5+1)16n\n" +
		"16:5o(6+1)17d\n" +
		"17:17o(2+1)18d\n" +
		"18:18d(2+1)19n\n" +
		"19:18o(4+1)20d\n" +
		"20:20o(3+2)21d\n" +
		"21:21d(5+1)22n\n" +
		"22:17o(6+1)23d\n" +
		"23:23d(2+1)24n\n" +
		"24:23o(4+1)25d\n" +
		"25:25o(3+1)26d\n" +
		"26:26d(2+1)27n\n" +
		"27:1o(6+1)28d";
    	glycan.setStructure(structure); */
    	
   /* 	Glycan glycan = new Glycan();
    	String structure="";
    	structure += "RES\n" + 
		"1b:b-dglc-HEX-1:5\n" +
		"2s:n-acetyl\n" +
		"3b:b-dglc-HEX-1:5\n" +
		"4s:n-acetyl\n" +
		"5b:b-dman-HEX-1:5\n" +
		"6b:a-dman-HEX-1:5\n" +
		"7b:b-dglc-HEX-1:5\n" +
		"8s:n-acetyl\n" +
		"9b:b-dgal-HEX-1:5\n" +
		"10b:a-dgro-dgal-NON-2:6|1:a|2:keto|3:d\n" +
		"11b:a-dman-HEX-1:5\n" +
		"12b:b-dglc-HEX-1:5\n" +
		"13s:n-acetyl\n" +
		"14b:b-dgal-HEX-1:5\n" +
		"15b:a-dgro-dgal-NON-2:6|1:a|2:keto|3:d\n" +
		"LIN\n" +
		"1:1d(2+1)2n\n" +
		"2:1o(4+1)3d\n" +
		"3:3d(2+1)4n\n" +
		"4:3o(4+1)5d\n" +
		"5:5o(3+1)6d\n" +
		"6:6o(2+1)7d\n" +
		"7:7d(2+1)8n\n" +
		"8:7o(4+1)9d\n" +
		"9:9o(6+2)10d\n" +
		"10:5o(6+1)11d\n" +
		"11:11o(2+1)12d\n" +
		"12:12d(2+1)13n\n" +
		"13:12o(4+1)14d\n" +
		"14:14o(3+2)15d\n";
    	glycan.setStructure(structure);
    	
    	String accessionNumber = "123";
    	try {
    		GlycanResponse resp = glycanClient.addGlycan(glycan);
    		System.out.println (resp.getAccessionNumber());
    		System.out.println ("Already existing? " + resp.getExisting());
    		System.out.println ("Pending? " + resp.getPending());
    	} catch (CustomClientException e) {
    		System.out.println ("Exception: " + e.getMessage() + ", status code: " + e.getStatusCode().toString());
    	}
    
    	// test getGlycan
    	try {
    		GlycanEntity glycanEntity = glycanClient.getGlycan(accessionNumber);  
    		System.out.println ("Got the glycan: " + glycanEntity.getStructureLength() + ", date: " + glycanEntity.getDateEntered());
    	} catch (CustomClientException e) {
    		System.out.println ("Exception: " + e.getMessage() + ", status code: " + e.getStatusCode().toString());
    	}
    	*/
    	// test listGlycans with IDs only
    	try {
    		GlycanList glycans = glycanClient.getGlycanList(true);
    		for (Iterator iterator = glycans.getGlycans().iterator(); iterator.hasNext();) {
				Object item = iterator.next();
				if (item instanceof String) {
					System.out.println ("Glycan " + (String)item);
				} else {
					System.out.println ("Something went wrong");
				}
			}
    		
    	} catch (CustomClientException e) {
    		System.out.println ("Exception: " + e.getMessage() + ", status code: " + e.getStatusCode().toString());
    	}
    }
}
