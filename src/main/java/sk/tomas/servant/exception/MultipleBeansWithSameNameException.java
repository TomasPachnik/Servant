package sk.tomas.servant.exception;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */
public class MultipleBeansWithSameNameException extends ServantException {

    public MultipleBeansWithSameNameException(String name) {
        super("multiple beans with name: " + name);
    }
}
