package org.glyspace.client;

import java.awt.image.BufferedImage;

import org.glyspace.client.exception.CustomClientException;
import org.glyspace.registry.database.MotifEntity;
import org.glyspace.registry.database.MotifSequence;
import org.glyspace.registry.view.Confirmation;
import org.glyspace.registry.view.MotifEntityList;
import org.glyspace.registry.view.MotifInput;

public interface MotifRestClient {

	public void setUsername(String username);
	public void setPassword(String password);
	
	Confirmation addMotif (MotifInput motif) throws CustomClientException;
	Confirmation deleteMotif (Integer motifId) throws CustomClientException;
	Confirmation addMotifSequence (String motifName, MotifSequence sequence) throws CustomClientException;
	Confirmation deleteMotifSequence (Integer sequenceId) throws CustomClientException;
	Confirmation addMotifTag (String motifName, String tag) throws CustomClientException;
	Confirmation updateMotifSequenceSetReducing (Integer sequenceId, Boolean reducing) throws CustomClientException;
	MotifEntity getMotif (String motifName) throws CustomClientException;
	MotifEntityList getMotifList () throws CustomClientException;
	MotifEntityList findMotifs (String[] tags, String queryTag) throws CustomClientException;
	BufferedImage getMotifSequenceImage (Integer sequenceId, String format, String notation, String style) throws CustomClientException;
	String getMotifSequenceSVGImage (Integer sequenceId, String notation, String style) throws CustomClientException;
}
