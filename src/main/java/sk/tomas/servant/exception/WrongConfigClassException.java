package sk.tomas.servant.exception;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */
public class WrongConfigClassException extends ServantException {

    public WrongConfigClassException() {
        super("set config class");
    }

}
