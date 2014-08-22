package eu.factorx.awac.util;

import javax.persistence.EntityTransaction;

import play.db.jpa.JPA;

public class MyrmexRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String toClientMessage;

	public MyrmexRuntimeException(String message) {
		super(message);
		toClientMessage = message;
		setTransactionRollbackOnly();
	}

	public MyrmexRuntimeException(BusinessErrorType message) {
		super(message.name());
		toClientMessage = message.name();
		setTransactionRollbackOnly();
	}

	public MyrmexRuntimeException(Throwable cause, String toClientMessage) {
		super(cause);
		this.toClientMessage = toClientMessage;
		setTransactionRollbackOnly();
	}

	public String getToClientMessage() {
		return toClientMessage;
	}

	private void setTransactionRollbackOnly() {
		EntityTransaction transaction = JPA.em().getTransaction();
		if (transaction != null && transaction.isActive()) {
			transaction.setRollbackOnly();
		}
	}

}
