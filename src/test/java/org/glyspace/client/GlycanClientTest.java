package org.glyspace.client;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.database.GlycanEntity;
import org.glyspace.registry.service.search.CombinationSearch;
import org.glyspace.registry.service.search.CompositionSearchType;
import org.glyspace.registry.service.search.ContributorSearchType;
import org.glyspace.registry.service.search.ExactSearchType;
import org.glyspace.registry.service.search.MotifSearchType;
import org.glyspace.registry.service.search.Operation;
import org.glyspace.registry.service.search.SubstructureSearchType;
import org.glyspace.registry.view.Confirmation;
import org.glyspace.registry.view.Glycan;
import org.glyspace.registry.view.GlycanInputList;
import org.glyspace.registry.view.GlycanList;
import org.glyspace.registry.view.GlycanResponse;
import org.glyspace.registry.view.GlycanResponseList;
import org.glyspace.registry.view.User;
import org.glyspace.registry.view.search.CompositionSearchInput;
import org.glyspace.registry.view.search.Range;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class GlycanClientTest {
	
	@Autowired
	GlycanRestClient glycanRestClient;
	
	@Before
	public void init() {
		glycanRestClient.setUsername("admin");
		glycanRestClient.setPassword("Adm1n$");
	}
	
	@Test
	public void testSearchKcfGlycan () {
		Glycan glycan = new Glycan();
    	String structure="ENTRY         CT-1             Glycan\nNODE  18\n     1  Neu5Ac   -48   -16\n     2  GlcNAc   -8   -8\n     3  GlcNAc   -32   0\n     4  GlcNAc   -48   -12\n     5  GlcNAc   -32   -4\n     6  Neu5Ac   -48   -4\n     7  GlcNAc   0   0\n     8  GlcNAc   -32   -16\n     9  Neu5Gc   -48   0\n     10  GlcNAc   -32   -12\n     11  Man   -24   -2\n     12  Man   -24   -14\n     13  Gal   -40   -4\n     14  Gal   -40   -12\n     15  Gal   -40   -16\n     16  Gal   -40   0\n     17  Man   -16   -8\n     18  Fuc   -8   8\nEDGE  17\n     1  5:b1     11:4\n     2  13:b1     5:4\n     3  6:a2     13:3\n     4  12:a1     17:6\n     5  8:b1     12:2\n     6  15:b1     8:4\n     7  2:b1     7:4\n     8  1:a2     15:3\n     9  10:b1     12:6\n     10  14:b1     10:4\n     11  4:b1     14:3\n     12  18:a1     7:6\n     13  17:b1     2:4\n     14  11:a1     17:3\n     15  3:b1     11:2\n     16  16:b1     3:4\n     17  9:a2     16:3\n///";
    	glycan.setStructure(structure); 
    	glycan.setEncoding("kcf");

    	GlycanEntity resp = glycanRestClient.exactStructureSearch(glycan);

    	assertNotNull(resp);
    	assertNotNull(resp.getAccessionNumber());
		System.out.println (resp.getAccessionNumber());
	}
    
	@Test
	public void testAddGlycan () {
		Glycan glycan = new Glycan();
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
    	glycan.setStructure(structure); 
    	
    	GlycanResponse resp = glycanRestClient.addGlycan(glycan);
    	
    	assertNotNull(resp);
    	assertNotNull(resp.getAccessionNumber());
		System.out.println (resp.getAccessionNumber());
		System.out.println ("Already existing? " + resp.getExisting());
		System.out.println ("Pending? " + resp.getPending());
		
		if (!resp.getExisting()) { // just inserted 
			assertTrue (resp.getPending());
		}
	}
	
	@Test
	public void testAddGlycanWithoutLogin () {
		Glycan glycan = new Glycan();
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
    	glycan.setStructure(structure); 
    	
    	glycanRestClient.setUsername(null);
    	glycanRestClient.setPassword("");
    	
    	try {
    		GlycanResponse resp = glycanRestClient.addGlycan(glycan);
    		assertTrue("should not be allowed to submit a structure", false);
    	} catch (CustomClientException ce) {
    		assertTrue (true);
    	}
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testListGlycansIdOnly () {
		GlycanList glycans = glycanRestClient.getGlycanList(true);
		for (Iterator iterator = glycans.getGlycans().iterator(); iterator.hasNext();) {
			Object item = iterator.next();
			assertTrue (item instanceof String);
			System.out.println ("Glycan: " + item);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testListGlycans () {
		GlycanList glycans = glycanRestClient.getGlycanList(false);
		for (Iterator iterator = glycans.getGlycans().iterator(); iterator.hasNext();) {
			Object item = iterator.next();
			assertTrue (item instanceof GlycanEntity);
			break;
			//System.out.println ("Glycan: " + ((GlycanEntity)item).getAccessionNumber());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Test 
	public void testGetGlycan () {
		GlycanList glycans = glycanRestClient.getGlycanList(false);
		for (Iterator iterator = glycans.getGlycans().iterator(); iterator.hasNext();) {
			Object item = iterator.next();
			assertTrue (item instanceof GlycanEntity);
			GlycanEntity glycan = glycanRestClient.getGlycan(((GlycanEntity)item).getAccessionNumber());
			
			System.out.println ("Got Glycan: " + ((GlycanEntity)item).getAccessionNumber());
			assertNotNull(glycan);
			assertNotNull(glycan.getStructure());
			break;
		}
	}
	
	@Test
	public void testSubstructureSearch() {
		Glycan glycan = new Glycan();
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
    	glycan.setStructure(structure); 
    	
    	try {
	    	GlycanList list = glycanRestClient.substructureSearch(glycan);
	    	assertNotNull(list);
	    	assertFalse(list.getGlycans().isEmpty());
    	} catch (CustomClientException ce) {   // if there is an exception, it should be 404 only
    		assertTrue (ce.getStatusCode() == HttpStatus.NOT_FOUND);
    	}
	}
	
	@Test
	public void testExactSearch() {
		
		testAddGlycan();
		
		Glycan glycan = new Glycan();
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
    	glycan.setStructure(structure); 
    	
    	try {
	    	GlycanEntity glycanEntity = glycanRestClient.exactStructureSearch(glycan);
	    	assertNotNull(glycanEntity);
    	} catch (CustomClientException ce) {   
    		assertFalse (true);
    	}
	}
	
	@Test
	public void testCompositionSearch() {
		
		testAddGlycan();
		
		CompositionSearchInput input = new CompositionSearchInput();
		input.setNeuAc(new Range(2));
		input.setHexNac(new Range(7));
		input.setdHex(new Range(1));
		input.setNeuGc(new Range(1));
		input.setHexose(new Range(7));
    	
    	try {
	    	GlycanList list = glycanRestClient.compositionSearch(input);
	    	assertNotNull(list);
	    	assertFalse(list.getGlycans().isEmpty());
    	} catch (CustomClientException ce) {   
    		assertFalse (true);
    	}
	}
	
	@Test
	public void testMotifSearch() {
		testAddGlycan();
		try {
			GlycanList list = glycanRestClient.motifSearch("Lactosamine motif");
	    	assertNotNull(list);
	    	assertFalse(list.getGlycans().isEmpty());
    	} catch (CustomClientException ce) {   
    		assertFalse (true);
    	}
		
	}
	
	@Test
	public void testContributorSearch() {
		testAddGlycan();
		User user = new User();
		user.setLoginId("admin");
		try {
			GlycanList list = glycanRestClient.searchByUser(user);
	    	assertNotNull(list);
	    	assertFalse(list.getGlycans().isEmpty());
    	} catch (CustomClientException ce) {   
    		assertFalse (true);
    	}
	}
	
	@Test
	public void testComplexSearch() {
		
		testAddGlycan();
		
		CombinationSearch search = new CombinationSearch();
	    
	    ExactSearchType eSearch = new ExactSearchType();
	    Glycan glycan2 = new Glycan();
	    glycan2.setStructure("RES\n" +
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
		"27:1o(6+1)28d");
	    glycan2.setEncoding("glycoct_condensed");
	    eSearch.setInput(glycan2);
	    
	    MotifSearchType mSearch = new MotifSearchType();
	    mSearch.setInput("Lactosamine motif");
	    
	    ContributorSearchType cSearch = new ContributorSearchType();
	    User user = new User();
	    user.setLoginId("test");
	    cSearch.setInput(user);
	    
	    CombinationSearch search2 = new CombinationSearch();
	    search2.setSearch1(mSearch);
	    
	    CompositionSearchType compS = new CompositionSearchType();
	    CompositionSearchInput input = new CompositionSearchInput();
		input.setNeuAc(new Range(2));
		input.setHexNac(new Range(7));
		input.setdHex(new Range(1));
		input.setNeuGc(new Range(1));
		input.setHexose(new Range(7));
		input.setOther(new Range(5));
		compS.setInput(input);
		
		search2.setSearch2(cSearch);
		search2.setOperation(Operation.DIFFERENCE);
		
	    search.setSearch1(eSearch);
	    search.setSearch2(search2);
	    search.setOperation(Operation.INTERSECTION);
	    
	    try {
			GlycanList list = glycanRestClient.complexSearch(search2);
	    	assertNotNull(list);
	    	assertFalse(list.getGlycans().isEmpty());
    	} catch (CustomClientException ce) {   
    		assertFalse (true);
    	}
	}
	
	@Test
	public void testListGlycansByAccessionNumbers() {
		testAddGlycan();
		
		CombinationSearch search = new CombinationSearch();
	    
	    ExactSearchType eSearch = new ExactSearchType();
	    Glycan glycan2 = new Glycan();
	    glycan2.setStructure("RES\n" +
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
		"27:1o(6+1)28d");
	    glycan2.setEncoding("glycoct_condensed");
	    eSearch.setInput(glycan2);
	    
	    MotifSearchType mSearch = new MotifSearchType();
	    mSearch.setInput("Lactosamine motif");
	    
	    ContributorSearchType cSearch = new ContributorSearchType();
	    User user = new User();
	    user.setLoginId("test");
	    cSearch.setInput(user);
	    
	    CombinationSearch search2 = new CombinationSearch();
	    search2.setSearch1(mSearch);
	    
	    CompositionSearchType compS = new CompositionSearchType();
	    CompositionSearchInput input = new CompositionSearchInput();
		input.setNeuAc(new Range(2));
		input.setHexNac(new Range(7));
		input.setdHex(new Range(1));
		input.setNeuGc(new Range(1));
		input.setHexose(new Range(7));
		input.setOther(new Range(5));
		compS.setInput(input);
		
		search2.setSearch2(cSearch);
		search2.setOperation(Operation.DIFFERENCE);
		
	    search.setSearch1(eSearch);
	    search.setSearch2(search2);
	    search.setOperation(Operation.INTERSECTION);
	    
	    try {
			GlycanList list = glycanRestClient.complexSearch(search2);	
			if (list != null) {
				GlycanList reducedList = new GlycanList();
				System.out.println("list size:" + list.getGlycans().size());
				if (list.getGlycans().size() > 50) {
					String[] array = new String[50];
					for (int i=0; i < 50; i++) {
						String accessionNumber = (String) list.getGlycans().get(i);
						array[i] = accessionNumber;
					}
					reducedList.setGlycans(array);
				}
				else {
					reducedList.setGlycans(list.getGlycans().toArray());
				}
				System.out.println("reduced list size:" + reducedList.getGlycans().size());
				GlycanList fullList = glycanRestClient.getGlycansByAccessionNumbers(reducedList);
				if (fullList == null || fullList.getGlycans().isEmpty()) {
					assertFalse("Should have some data", false);
				}
				for (Iterator iterator = fullList.getGlycans().iterator(); iterator.hasNext();) {
					Object type = (Object) iterator.next();
					if (type instanceof GlycanEntity) {
						assertTrue(true);
						break;
					}
					else {
						assertFalse("Should be GlycanEntity", true);
						break;
					}
				}
			}
	    } catch (Exception e) {
	    	assertFalse ("Search failed", true);
	    }
	}
	
	@Test
	public void testDelete () {
		String structure="RES\n" +
		"1b:x-dgro-dgal-NON-2:6|1:a|2:keto|3:d\n" +
		"2s:n-glycolyl\n" + 
		"LIN\n" + 
		"1:1d(5+1)2n";
		Glycan glycan = new Glycan();
		glycan.setStructure(structure);
		GlycanResponse resp = glycanRestClient.addGlycan(glycan);
		if (!resp.getExisting()) {
			Confirmation con = glycanRestClient.deleteGlycan(resp.getAccessionNumber());
			assertEquals ("success", con.getStatus());
		}
	}
	
	@Test
	public void testGetImage () {
		String structure="RES\n" +
		"1b:x-dgro-dgal-NON-2:6|1:a|2:keto|3:d\n" +
		"2s:n-glycolyl\n" + 
		"LIN\n" + 
		"1:1d(5+1)2n";
		Glycan glycan = new Glycan();
		glycan.setStructure(structure);
		GlycanResponse resp = glycanRestClient.addGlycan(glycan);
		if (!resp.getPending()) {
			BufferedImage image = glycanRestClient.getGlycanImage(resp.getAccessionNumber(), "png", "cfg", "compact");
			assertNotNull("PNG", image);
			image = glycanRestClient.getGlycanImage(resp.getAccessionNumber(), "jpg", "cfg", "compact");
			assertNotNull("JPG", image);
			String imageXML = glycanRestClient.getGlycanSVGImage(resp.getAccessionNumber(), "cfg", "compact");
			System.out.println ("SVG image\n" + imageXML);
			assertNotNull("SVG", image);
			
		}
	}
	
	@Test
	public void testCheckListofGlycans() {
		Glycan glycan = new Glycan();
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
    	glycan.setStructure(structure); 
    	
    	Glycan glycan2 = new Glycan();
    	structure="<sugar version=\"1.0\">" +
  "<residues>" +
    "<basetype id=\"1\" anomer=\"a\" superclass=\"hex\" ringStart=\"1\" ringEnd=\"5\" name=\"a-dgal-HEX-1:5\">" +
      "<stemtype id=\"1\" type=\"dgal\" />" +
    "</basetype>" +
    "<substituent id=\"2\" name=\"n-acetyl\" />" +
    "<basetype id=\"3\" anomer=\"b\" superclass=\"hex\" ringStart=\"1\" ringEnd=\"5\" name=\"b-dgal-HEX-1:5\"> " + 
      "<stemtype id=\"1\" type=\"dgal\" />" +
    "</basetype>" +
    "<basetype id=\"4\" anomer=\"a\" superclass=\"non\" ringStart=\"2\" ringEnd=\"6\" name=\"a-dgro-dgal-NON-2:6|1:a|2:keto|3:d\">" +
      "<stemtype id=\"1\" type=\"dgro\" />" +
      "<stemtype id=\"2\" type=\"dgal\" />" +
      "<modification type=\"a\" pos_one=\"1\" />" +
      "<modification type=\"keto\" pos_one=\"2\" />" +
      "<modification type=\"d\" pos_one=\"3\" />" +
    "</basetype>" +
    "<substituent id=\"5\" name=\"n-acetyl\" />" +
    "<basetype id=\"6\" anomer=\"a\" superclass=\"non\" ringStart=\"2\" ringEnd=\"6\" name=\"a-dgro-dgal-NON-2:6|1:a|2:keto|3:d\"> " +
      "<stemtype id=\"1\" type=\"dgro\" />" +
      "<stemtype id=\"2\" type=\"dgal\" />" +
      "<modification type=\"a\" pos_one=\"1\" />" +
      "<modification type=\"keto\" pos_one=\"2\" />" +
      "<modification type=\"d\" pos_one=\"3\" /> " +
    "</basetype> " +
    "<substituent id=\"7\" name=\"n-acetyl\" /> " +
  "</residues>" +
  "<linkages>" +
    "<connection id=\"1\" parent=\"1\" child=\"2\">" +
      "<linkage id=\"1\" parentType=\"d\" childType=\"n\">" +
        "<parent pos=\"2\" />" +
        "<child pos=\"1\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"2\" parent=\"1\" child=\"3\">" +
      "<linkage id=\"2\" parentType=\"o\" childType=\"d\">" +
       " <parent pos=\"3\" />" +
       " <child pos=\"1\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"3\" parent=\"3\" child=\"4\">" +
     " <linkage id=\"3\" parentType=\"o\" childType=\"d\">" +
        "<parent pos=\"3\" />" +
        "<child pos=\"2\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"4\" parent=\"4\" child=\"5\">" +
      "<linkage id=\"4\" parentType=\"d\" childType=\"n\"> " +
        "<parent pos=\"5\" />" +
        "<child pos=\"1\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"5\" parent=\"3\" child=\"6\">" +
      "<linkage id=\"5\" parentType=\"o\" childType=\"d\">" +
        "<parent pos=\"6\" />" +
        "<child pos=\"2\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"6\" parent=\"6\" child=\"7\">" +
      "<linkage id=\"6\" parentType=\"d\" childType=\"n\">" +
        "<parent pos=\"5\" />" +
        "<child pos=\"1\" />" +
      "</linkage>" +
    "</connection>" +
  "</linkages>" +
"</sugar>";
    	glycan2.setEncoding("glycoct_xml");
    	glycan2.setStructure(structure);
    	
    	List<Glycan> glycans = new ArrayList<>();
    	glycans.add(glycan);
    	glycans.add(glycan2);
    	GlycanInputList list = new GlycanInputList();
    	list.setGlycans(glycans);
    	
    	GlycanResponseList result = glycanRestClient.checkGlycanStructures(list);
    	assertEquals(result.getResponses().size() + result.getErrorResponses().size(), 2);	
	}
	
	@Test
	public void testAddListofGlycans() {
		Glycan glycan = new Glycan();
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
    	glycan.setStructure(structure); 
    	
    	Glycan glycan2 = new Glycan();
    	structure="<sugar version=\"1.0\">" +
  "<residues>" +
    "<basetype id=\"1\" anomer=\"a\" superclass=\"hex\" ringStart=\"1\" ringEnd=\"5\" name=\"a-dgal-HEX-1:5\">" +
      "<stemtype id=\"1\" type=\"dgal\" />" +
    "</basetype>" +
    "<substituent id=\"2\" name=\"n-acetyl\" />" +
    "<basetype id=\"3\" anomer=\"b\" superclass=\"hex\" ringStart=\"1\" ringEnd=\"5\" name=\"b-dgal-HEX-1:5\"> " + 
      "<stemtype id=\"1\" type=\"dgal\" />" +
    "</basetype>" +
    "<basetype id=\"4\" anomer=\"a\" superclass=\"non\" ringStart=\"2\" ringEnd=\"6\" name=\"a-dgro-dgal-NON-2:6|1:a|2:keto|3:d\">" +
      "<stemtype id=\"1\" type=\"dgro\" />" +
      "<stemtype id=\"2\" type=\"dgal\" />" +
      "<modification type=\"a\" pos_one=\"1\" />" +
      "<modification type=\"keto\" pos_one=\"2\" />" +
      "<modification type=\"d\" pos_one=\"3\" />" +
    "</basetype>" +
    "<substituent id=\"5\" name=\"n-acetyl\" />" +
    "<basetype id=\"6\" anomer=\"a\" superclass=\"non\" ringStart=\"2\" ringEnd=\"6\" name=\"a-dgro-dgal-NON-2:6|1:a|2:keto|3:d\"> " +
      "<stemtype id=\"1\" type=\"dgro\" />" +
      "<stemtype id=\"2\" type=\"dgal\" />" +
      "<modification type=\"a\" pos_one=\"1\" />" +
      "<modification type=\"keto\" pos_one=\"2\" />" +
      "<modification type=\"d\" pos_one=\"3\" /> " +
    "</basetype> " +
    "<substituent id=\"7\" name=\"n-acetyl\" /> " +
  "</residues>" +
  "<linkages>" +
    "<connection id=\"1\" parent=\"1\" child=\"2\">" +
      "<linkage id=\"1\" parentType=\"d\" childType=\"n\">" +
        "<parent pos=\"2\" />" +
        "<child pos=\"1\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"2\" parent=\"1\" child=\"3\">" +
      "<linkage id=\"2\" parentType=\"o\" childType=\"d\">" +
       " <parent pos=\"3\" />" +
       " <child pos=\"1\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"3\" parent=\"3\" child=\"4\">" +
     " <linkage id=\"3\" parentType=\"o\" childType=\"d\">" +
        "<parent pos=\"3\" />" +
        "<child pos=\"2\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"4\" parent=\"4\" child=\"5\">" +
      "<linkage id=\"4\" parentType=\"d\" childType=\"n\"> " +
        "<parent pos=\"5\" />" +
        "<child pos=\"1\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"5\" parent=\"3\" child=\"6\">" +
      "<linkage id=\"5\" parentType=\"o\" childType=\"d\">" +
        "<parent pos=\"6\" />" +
        "<child pos=\"2\" />" +
      "</linkage>" +
    "</connection>" +
    "<connection id=\"6\" parent=\"6\" child=\"7\">" +
      "<linkage id=\"6\" parentType=\"d\" childType=\"n\">" +
        "<parent pos=\"5\" />" +
        "<child pos=\"1\" />" +
      "</linkage>" +
    "</connection>" +
  "</linkages>" +
"</sugar>";
    	glycan2.setEncoding("glycoct_xml");
    	glycan2.setStructure(structure);
    	
    	List<Glycan> glycans = new ArrayList<>();
    	glycans.add(glycan);
    	glycans.add(glycan2);
    	GlycanInputList list = new GlycanInputList();
    	list.setGlycans(glycans);
    	
    	GlycanResponseList result = glycanRestClient.addGlycanStructures(list);
    	assertEquals(result.getResponses().size() + result.getErrorResponses().size(), 2);	
	}
}
