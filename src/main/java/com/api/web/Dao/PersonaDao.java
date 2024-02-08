package com.api.web.Dao;

import org.springframework.data.repository.CrudRepository;

import com.api.web.Entity.Persona;

public interface PersonaDao extends CrudRepository<Persona, Long> {

}
