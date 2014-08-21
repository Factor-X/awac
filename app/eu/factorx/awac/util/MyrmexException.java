package eu.factorx.awac.util;

public class MyrmexException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String toClientMessage;

	public MyrmexException(String message) {
		super(message);
		toClientMessage = message;
	}

	public MyrmexException(BusinessErrorType message) {
		super(message.name());
		toClientMessage = message.name();
	}

	public MyrmexException(Throwable cause, String toClientMessage) {
		super(cause);
		this.toClientMessage = toClientMessage;
	}

	public String getToClientMessage() {
		return toClientMessage;
	}
}
