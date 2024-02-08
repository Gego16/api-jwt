package com.api.web.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.web.Entity.Persona;
import com.api.web.Service.PersonaService;


@RestController
@RequestMapping("/usuarios")
public class PersonaController {

	@Autowired
	private PersonaService personaService;
	
	@GetMapping("/lista")
	public ResponseEntity<List<Persona>>lista(){
		List<Persona> person = new ArrayList<>();
		
		try {
			person = personaService.listausuarios();
			if(person != null) {
				return new ResponseEntity<>(person,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<Persona>agregar(@RequestBody Persona logging){
		Persona persoSave;
		try {
			persoSave = personaService.save(logging);
			if(persoSave != null) {
				return new ResponseEntity<>(persoSave,HttpStatus.CREATED);
			}
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		}
		return null;
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Boolean>eliminar(@PathVariable Long id){
		try {
			personaService.delete(id);
			return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
		}catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping("/editar/{id}")
	public ResponseEntity<Persona> editar(@PathVariable Long id, @RequestBody Persona persona) {
		
	    persona.setId(id); // Asigna el id al objeto Logging antes de editarlo
	    
	    Persona editado = personaService.edit(persona);
	    
	    try {
	        if (editado != null) {
	            return new ResponseEntity<>(editado, HttpStatus.ACCEPTED);
	        } else {
	            return new ResponseEntity<>(editado, HttpStatus.NOT_ACCEPTABLE);
	        }
	    } catch (Exception e) {
	        System.out.println(e);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}
