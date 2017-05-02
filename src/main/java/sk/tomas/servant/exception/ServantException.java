package sk.tomas.servant.exception;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */
public abstract class ServantException extends Exception {
    ServantException(String message) {
        super(message);
    }
}
