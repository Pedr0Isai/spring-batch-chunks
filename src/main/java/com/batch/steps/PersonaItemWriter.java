package com.batch.steps;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.model.Persona;
import com.batch.service.IPersonaService;

import lombok.extern.slf4j.Slf4j;

/**El writer implementa ItemWriter que facilita la escritura en BBDD*/
@Slf4j
public class PersonaItemWriter implements ItemWriter<Persona> {
	
	/**Se inyecta el servicio de Persona*/
	@Autowired
	private IPersonaService service;

	@Override
	public void write(List<? extends Persona> list) throws Exception {
		log.info("------------> Inicio del paso de ESCRITURA <------------");
		
		list.forEach(persona -> log.info(persona.toString()));
		service.saveAll((List<Persona>) list);
		
		log.info("------------> Inicio del paso de ESCRITURA <------------");
	}
	
}
