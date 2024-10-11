package br.org.serratec.livros.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.livros.domain.Livro;
import br.org.serratec.livros.repository.LivroRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {
	
	@Autowired
	LivroRepository livroRepository;
	
	@GetMapping
	public ResponseEntity<List<Livro>> listar(){
		return ResponseEntity.ok(livroRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Livro> buscar(@PathVariable Long id){
		Optional<Livro> livroOpt = livroRepository.findById(id);
		return livroOpt.isPresent() ? ResponseEntity.ok(livroOpt.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Livro adicionar(@Valid @RequestBody Livro livro) {
		return livroRepository.save(livro);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Livro> mudar(@PathVariable Long id, @Valid @RequestBody Livro livro){
		if (livroRepository.existsById(id)) {
			livro.setId(id);
			livro = livroRepository.save(livro);
			return ResponseEntity.ok(livro);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Livro> deletar(@PathVariable Long id){
		if (livroRepository.existsById(id)) {
			
			livroRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
