package com.batch.steps;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Persona;

/**Clase donde se procesan los datos que llegan del ItemReader, se deben definir el tipo de objeto de entrada y de salida (<Persona, Persona>)*/
public class PersonaItemProcessor implements ItemProcessor<Persona, Persona>{

	@Override
	public Persona process(Persona item) throws Exception {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		
		/**Se agrega la fecha alta a Persona*/
		item.setFechaAlta(format.format(date));
		
		return item;
	}

}
