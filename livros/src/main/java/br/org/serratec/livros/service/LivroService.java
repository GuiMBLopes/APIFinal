package br.org.serratec.livros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.livros.domain.Livro;
import br.org.serratec.livros.exception.NegativeException;
import br.org.serratec.livros.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	LivroRepository livroRepository;

	public List<Livro> listar() {
		return livroRepository.findAll();
	}

	public Optional<Livro> buscar(Long id) {
		return livroRepository.findById(id);
	}
	
	public Livro inserir(Livro livro) {
		if(livro.getQtdPaginas() <= 0) {
			throw new NegativeException("o campo QtdPaginas não pode ser menor ou igual a 0");
		}
		return livroRepository.save(livro);
	}
	
	public boolean verifcarId(Long id) {
		return livroRepository.existsById(id);
	}
	
	public void deletar(Long id) {
		 livroRepository.deleteById(id);
	}

}
