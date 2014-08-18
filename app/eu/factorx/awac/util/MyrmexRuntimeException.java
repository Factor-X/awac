package eu.factorx.awac.util;

public class MyrmexRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String toClientMessage;

	public MyrmexRuntimeException(String message) {
		super(message);
		toClientMessage = message;
	}

	public MyrmexRuntimeException(BusinessErrorType message) {
		super(message.name());
		toClientMessage = message.name();
	}

	public MyrmexRuntimeException(Throwable cause, String toClientMessage) {
		super(cause);
		this.toClientMessage = toClientMessage;
	}

	public String getToClientMessage() {
		return toClientMessage;
	}
}
