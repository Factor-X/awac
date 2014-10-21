package eu.factorx.awac.util;

import javax.persistence.EntityTransaction;

import play.db.jpa.JPA;

import java.util.ArrayList;
import java.util.List;

public class MyrmexRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String toClientMessage;
    private final BusinessErrorType businessErrorType;
    private final List<String> paramList = new ArrayList<>();


	public MyrmexRuntimeException(String message) {
		super(message);
		toClientMessage = message;
        businessErrorType=null;
		setTransactionRollbackOnly();
	}

	public MyrmexRuntimeException(BusinessErrorType businessErrorType,String... params) {
		super();
        toClientMessage=null;
		this.businessErrorType=businessErrorType;
        for(String param : params){
            paramList.add(param);
        }
		setTransactionRollbackOnly();
	}

	public MyrmexRuntimeException(Throwable cause, String toClientMessage) {
		super(cause);
		this.toClientMessage = toClientMessage;
        businessErrorType=null;
		setTransactionRollbackOnly();
	}

	public String getToClientMessage() {
		if(businessErrorType!=null) {
            return businessErrorType.toString();
        }
        else{
            return toClientMessage;
        }
	}

	private void setTransactionRollbackOnly() {
		EntityTransaction transaction = JPA.em().getTransaction();
		if (transaction != null && transaction.isActive()) {
			transaction.setRollbackOnly();
		}
	}

    public BusinessErrorType getBusinessErrorType() {
        return businessErrorType;
    }

    public List<String> getParamList() {
        return paramList;
    }

    @Override
    public String toString() {
        return "MyrmexRuntimeException{" +
                "toClientMessage='" + toClientMessage + '\'' +
                ", businessErrorType=" + businessErrorType +
                ", paramList=" + paramList +
                '}';
    }
}
