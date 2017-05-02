package sk.tomas.servant.exception;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */
public class CannotCreateBeanExcetion extends ServantException {
    public CannotCreateBeanExcetion(String name) {
        super("Can not initialize. Bean: '" + name + "' is null!");
    }
}
