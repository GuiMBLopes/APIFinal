package br.org.serratec.livros.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.livros.domain.Livro;
import br.org.serratec.livros.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {

	@Autowired
	LivroService livroService;

	@Operation(summary = "Lista todos os livros", description = "A resposta lista os dados "
			+ "livro id, titulo, quantidade de paginas, autor, ano de lançamento, editora")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Livro.class), mediaType = "application/json") }, description = "Retorna todos os livros cadastrados"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}"))) })

	@GetMapping
	public ResponseEntity<List<Livro>> listar() {
		return ResponseEntity.ok(livroService.listar());
	}

	@Operation(summary = "Lista um livro", description = "A resposta lista os dados "
			+ "livro id, titulo, quantidade de paginas, autor, ano de lançamento, editora")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Livro.class), mediaType = "application/json") }, description = "Retorna o livro procurado"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}"))) })

	@GetMapping("/{id}")
	public ResponseEntity<Livro> buscar(@PathVariable Long id) {
		Optional<Livro> livroOpt = livroService.buscar(id);
		return livroOpt.isPresent() ? ResponseEntity.ok(livroOpt.get()) : ResponseEntity.notFound().build();
	}

	@Operation(summary = "Insere um novo livro", description = "A resposta é um objeto "
			+ "com os dados cadastrados do servidor")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Livro adicionado "),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}"))) })

	@PostMapping
	public ResponseEntity<Livro> adicionar(@Valid @RequestBody Livro livro) {
		livro = livroService.inserir(livro);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getId()).toUri();

		return ResponseEntity.created(uri).body(livro);
	}

	@Operation(summary = "Altera um livro existente", description = "A resposta é um objeto "
			+ "com os dados alterados")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Livro alterado"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}"))) })

	@PutMapping("/{id}")
	public ResponseEntity<Livro> mudar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
		if (livroService.verifcarId(id)) {
			livro.setId(id);
			livro = livroService.inserir(livro);
			return ResponseEntity.ok(livro);
		}

		return ResponseEntity.notFound().build();
	}

	@Operation(summary = "Exclui um livro", description = "A resposta é o objeto " + "excluido")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", content = {
			@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Exclui um objeto pelo id"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}"))) })

	@DeleteMapping("/{id}")
	public ResponseEntity<Livro> deletar(@PathVariable Long id) {
		if (livroService.verifcarId(id)) {

			livroService.deletar(id);
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

}
