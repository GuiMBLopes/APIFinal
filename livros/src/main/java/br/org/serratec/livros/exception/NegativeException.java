package br.org.serratec.livros.exception;

public class NegativeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegativeException(String message) {
		super(message);
	}
	
}
