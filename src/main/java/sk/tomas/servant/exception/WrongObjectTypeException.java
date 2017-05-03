package sk.tomas.servant.exception;

/**
 * Created by Tomas Pachnik on 03-May-17.
 */
public class WrongObjectTypeException extends ServantException {

    public WrongObjectTypeException(String name, Class clazz1, Class clazz2) {
        super("Bean '" + name + "' is " + clazz1 + ", but you set " + clazz2 + "!");
    }
}
