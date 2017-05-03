package sk.tomas.servant.exception;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */
public class ServantException extends Exception {
    public ServantException(String message) {
        super(message);
    }

    public ServantException(String message, Throwable cause) {
        super(message, cause);
    }
}
