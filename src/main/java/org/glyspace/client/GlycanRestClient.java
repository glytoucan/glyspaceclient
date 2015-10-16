package org.glyspace.client;

import java.awt.image.BufferedImage;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.database.GlycanEntity;
import org.glyspace.registry.service.search.CombinationSearch;
import org.glyspace.registry.view.Confirmation;
import org.glyspace.registry.view.Glycan;
import org.glyspace.registry.view.GlycanInputList;
import org.glyspace.registry.view.GlycanList;
import org.glyspace.registry.view.GlycanResponse;
import org.glyspace.registry.view.GlycanResponseList;
import org.glyspace.registry.view.User;
import org.glyspace.registry.view.search.CompositionSearchInput;

public interface GlycanRestClient {
	public void setUsername(String username);
	public void setPassword(String password);
	
	public GlycanEntity getGlycan (String accessionNumber) throws CustomClientException;
	public Confirmation deleteGlycan (String accessionNumber) throws CustomClientException;
	public Confirmation deleteGlycansByAccessionNumbers (GlycanList accessionNumbers) throws CustomClientException;
	public GlycanList getGlycanList (boolean idOnly) throws CustomClientException;
	public GlycanList getGlycansByAccessionNumbers (GlycanList accessionNumbers) throws CustomClientException;
	public GlycanResponse addGlycan (Glycan glycan) throws CustomClientException;
	public GlycanList substructureSearch (Glycan glycan) throws CustomClientException;
	public GlycanList compositionSearch (CompositionSearchInput input) throws CustomClientException;
	public GlycanList searchByUser (User user) throws CustomClientException;
	public GlycanList searchPendingByUser (User user) throws CustomClientException;
	public GlycanList motifSearch (String motifName) throws CustomClientException;
	public GlycanList complexSearch (CombinationSearch search) throws CustomClientException;
	public GlycanEntity exactStructureSearch (Glycan structure) throws CustomClientException;
	public GlycanResponseList checkGlycanStructures (GlycanInputList glycans) throws CustomClientException;
	public GlycanResponseList addGlycanStructures (GlycanInputList glycans) throws CustomClientException;
	public BufferedImage getGlycanImage (String accessionNumber, String format, String notation, String style) throws CustomClientException;
	public String getGlycanSVGImage (String accessionNumber, String notation, String style) throws CustomClientException;
}
