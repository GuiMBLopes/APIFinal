package br.org.serratec.livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.serratec.livros.domain.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

}
