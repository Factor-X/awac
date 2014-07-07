package eu.factorx.awac.util;

/**
 * Created by root on 7/07/14.
 */
public class MyrmexRunTimeException extends RuntimeException{

    private final String toClientMessage;

    public MyrmexRunTimeException(String message) {
        super(message);
        toClientMessage = message;
    }

    public MyrmexRunTimeException(Throwable cause, String toClientMessage) {
        super(cause);
        this.toClientMessage = toClientMessage;
    }

    public String getToClientMessage() {
        return toClientMessage;
    }
}
