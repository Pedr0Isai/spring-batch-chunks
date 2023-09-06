package com.batch.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.batch.model.Persona;
import com.batch.repository.IPersonaDAO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonaServiceImpl implements IPersonaService {

	private final IPersonaDAO repository;
	
	@Override
	public Iterable<Persona> saveAll(List<Persona> personaList) {
		return repository.saveAll(personaList);
	}

}
