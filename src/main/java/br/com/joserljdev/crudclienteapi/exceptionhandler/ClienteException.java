package br.com.joserljdev.crudclienteapi.exceptionhandler;

public class ClienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClienteException(String message) {
		super(message);
	}
}
