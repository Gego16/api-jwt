package com.api.web.Service;

import java.util.List;

import com.api.web.Entity.Persona;


public interface PersonaService {
	
	public List<Persona>listausuarios();
	
	public Persona save(Persona persona);
	
	public Persona findById(Long id);
	
	public boolean delete(Long id);
	
	public Persona edit(Persona persona);

}
