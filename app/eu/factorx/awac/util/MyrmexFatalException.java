package eu.factorx.awac.util;

public class MyrmexFatalException extends MyrmexRuntimeException {


	public MyrmexFatalException(String message) {
		super(message);
	}

	public MyrmexFatalException(BusinessErrorType businessErrorType, String... params) {
		super(businessErrorType,params);
	}

	public MyrmexFatalException(Throwable cause, String toClientMessage) {
		super(cause,toClientMessage);
	}
}
