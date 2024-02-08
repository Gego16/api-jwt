package com.api.web.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.web.Dao.PersonaDao;
import com.api.web.Entity.Persona;
import com.api.web.Service.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {
	
	@Autowired
	private PersonaDao personaDao;

	@Override
	public List<Persona> listausuarios() {
		return (List<Persona>) personaDao.findAll();
	}

	@Override
	public Persona save(Persona persona) {
		return personaDao.save(persona);
	}

	@Override
	public Persona findById(Long id) {
		return personaDao.findById(id).orElse(null);
	}

	@Override
	public boolean delete(Long id) {
		Persona pers = findById(id);
		personaDao.deleteById(pers.getId());
		return true;
	}

	@Override
	public Persona edit(Persona persona) {
		Persona editar = findById(persona.getId());
		editar.setNomUsuario(persona.getNomUsuario());
		editar.setApPaterno(persona.getApPaterno());
		editar.setApMaterno(persona.getApMaterno());
		save(editar);
		return editar;
	}

}
