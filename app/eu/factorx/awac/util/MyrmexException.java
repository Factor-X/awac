package eu.factorx.awac.util;

import javax.persistence.EntityTransaction;

import play.db.jpa.JPA;

public class MyrmexException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String toClientMessage;

	public MyrmexException(String message) {
		super(message);
		toClientMessage = message;
		setTransactionRollbackOnly();
	}

	public MyrmexException(BusinessErrorType message) {
		super(message.name());
		toClientMessage = message.name();
		setTransactionRollbackOnly();
	}

	public MyrmexException(Throwable cause, String toClientMessage) {
		super(cause);
		this.toClientMessage = toClientMessage;
		setTransactionRollbackOnly();
	}

	private void setTransactionRollbackOnly() {
		EntityTransaction transaction = JPA.em().getTransaction();
		if (transaction != null && transaction.isActive()) {			
			transaction.setRollbackOnly();
		}
	}

	public String getToClientMessage() {
		return toClientMessage;
	}
}
