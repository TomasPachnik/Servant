package sk.tomas.servant.core;

import sk.tomas.servant.exception.BeanNotFoundException;
import sk.tomas.servant.exception.ServantException;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */
public interface Core {

    Object getByName(String name) throws BeanNotFoundException;

    void updateByName(String name, Object obj) throws ServantException;

}
