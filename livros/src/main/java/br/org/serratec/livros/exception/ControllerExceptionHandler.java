package br.org.serratec.livros.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		
		List<String> erros = new ArrayList<String>();
		
		for(FieldError e : ex.getBindingResult().getFieldErrors()) {
			erros.add(e.getField() + ": " + e.getDefaultMessage());
		}

		ErroResposta erroResposta = new ErroResposta(status.value(), "Há campos invalidos", LocalDateTime.now(), erros);
		
		return super.handleExceptionInternal(ex, erroResposta ,headers, status, request);
	}
	
	@ExceptionHandler(PaginaException.class)
		private ResponseEntity<Object> handlePaginaException(PaginaException ex){
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}

}
