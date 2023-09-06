package com.batch.service;

import java.util.List;

import com.batch.model.Persona;

public interface IPersonaService {

	Iterable<Persona> saveAll(List<Persona> personaList);
	
}
