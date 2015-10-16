package org.glyspace.client.config;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.batik.transcoder.TranscoderInput;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class BufferedImageHttpMessageConverter implements HttpMessageConverter<BufferedImage> {

    public List<MediaType> getSupportedMediaTypes() {
    	List<MediaType> supported = new ArrayList<MediaType>();
    	Collections.addAll(supported, new MediaType("image", "jpeg"), new MediaType("image", "png"));
        return supported;
    }

    public boolean supports(Class<? extends BufferedImage> clazz) {
        return BufferedImage.class.equals(clazz);
    }


	@Override
	public BufferedImage read(Class<? extends BufferedImage> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return ImageIO.read(inputMessage.getBody());
	}

	@Override
	public void write(BufferedImage t, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		throw new UnsupportedOperationException("Not implemented");
		
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return BufferedImage.class.equals(clazz);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return false;
	}

}