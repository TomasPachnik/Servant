package sk.tomas.servant.exception;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */

public class BeanNotFoundException extends ServantException {

    public BeanNotFoundException(String name) {
        super("Bean '" + name + "' not found");
    }

}
