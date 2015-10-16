package org.glyspace.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.database.MotifEntity;
import org.glyspace.registry.database.MotifSequence;
import org.glyspace.registry.view.MotifEntityList;
import org.glyspace.registry.view.MotifInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class MotifClientTest {
	@Autowired
	MotifRestClient motifRestClient;
	
	@Before
	public void init() {
		motifRestClient.setUsername("test");
		motifRestClient.setPassword("t3st");
	}
	
	@Test
	public void testGetMotif () {
		MotifEntity motif = motifRestClient.getMotif("O-Glycan core 1");
		assertNotNull(motif);
		assertEquals("tag size", 2, motif.getTags().size());
		try {
			motifRestClient.getMotif(null);
		} catch (CustomClientException c) {
			assertTrue("No motif", true);
		}
		try {
			motifRestClient.getMotif("test");
		} catch (CustomClientException c) {
			assertTrue("No motif", true);
		}
	}
	
	@Test
	public void testGetMotifList() {
		MotifEntityList list = motifRestClient.getMotifList();
		assertTrue("list not empty", list.getMotifs().size() > 1);
	}
	
	@Test
	public void testGetImage () {
		BufferedImage image = motifRestClient.getMotifSequenceImage(121, "png", "cfg", "compact");
		assertNotNull("PNG", image);
		image = motifRestClient.getMotifSequenceImage(121, "jpg", "cfg", "compact");
		assertNotNull("JPG", image);
		String imageXML = motifRestClient.getMotifSequenceSVGImage (121, "cfg", "compact");
		System.out.println ("SVG image\n" + imageXML);
		assertNotNull("SVG", image);
		
	}
	
	@Test
	public void testMotifFind () {
		String[] tags = {"full", "O-Glycan"};
		MotifEntityList list = motifRestClient.findMotifs(tags, "AND");
		assertTrue("AND list empty", list.getMotifs().size() > 1);
		
		String[] tags2 = {"ambiguous", "N-Glycan"};
		list = motifRestClient.findMotifs(tags2, "OR");
		assertTrue("OR list empty", list.getMotifs().size() > 1);
	}
	
	@Test
	public void testMotifAdd () {
		MotifInput input = new MotifInput();
		input.setName("O-Glycan core 1");
		String[] tags = {"full", "O-Glycan"};
		List<MotifSequence> sequences = new ArrayList<>();
		MotifSequence sequence = new MotifSequence();
		sequence.setSequence("RES\n1b:a-dgal-HEX-1:5\n2s:n-acetyl\n3b:b-dgal-HEX-1:5\nLIN 1:1d(2+1)2n\n2:1o(3+1)3d");
		sequence.setReducing(true);
		sequences.add (sequence);
		input.setSequences(sequences);
		input.setTags(tags);
		
		motifRestClient.addMotif(input);
		assertTrue(true);
	}
}
