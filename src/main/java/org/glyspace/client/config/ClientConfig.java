package org.glyspace.client.config;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.IOException;
import java.io.Writer;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

@Configuration
public class ClientConfig {

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    marshaller.setClassesToBeBound(new Class[]{
	              org.glyspace.registry.view.User.class,
	              org.glyspace.registry.view.Glycan.class,
	              org.glyspace.registry.view.GlycanErrorResponse.class,
	              org.glyspace.registry.view.GlycanInputList.class,
	              org.glyspace.registry.view.GlycanResponseList.class,
	              org.glyspace.registry.view.GlycanResponse.class,
	              org.glyspace.registry.view.Confirmation.class,
	              org.glyspace.registry.view.GlycanList.class,
	              org.glyspace.registry.view.RoleList.class,
	              org.glyspace.registry.view.UserList.class,
	              org.glyspace.registry.view.MotifInput.class,
	              org.glyspace.registry.database.MotifEntity.class,
	              org.glyspace.registry.database.MotifSequence.class,
	              org.glyspace.registry.view.MotifEntityList.class,
	              org.glyspace.registry.view.search.CompositionSearchInput.class,
	              org.glyspace.registry.view.search.Range.class,
	              org.glyspace.registry.database.UserEntity.class,
	              org.glyspace.registry.database.GlycanEntity.class,
	              org.glyspace.registry.service.search.CombinationSearch.class
	              
	    });
	    
	    HashMap<String,Object> properties = new HashMap<String,Object>();
	    properties.put(CharacterEscapeHandler.class.getName(),
    			new CharacterEscapeHandler() {
			@Override
			public void escape(char[] ch, int start, int length,
					boolean isAttVal, Writer out) throws IOException {
				out.write( ch, start, length ); 
			}
		});
	    marshaller.setMarshallerProperties(properties );
	    return marshaller;
	}
	
}
