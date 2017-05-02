package sk.tomas.servant.exception;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */
public class CannotCreateBeanExcetion extends BusinessException {
    public CannotCreateBeanExcetion(String name) {
        super("Can not initiazize Bean: '" + name + "', is null!");
    }
}
