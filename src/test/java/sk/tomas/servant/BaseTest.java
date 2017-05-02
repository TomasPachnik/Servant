package sk.tomas.servant;

import org.junit.Before;
import sk.tomas.servant.core.Core;
import sk.tomas.servant.core.impl.CoreImpl;
import sk.tomas.servant.exception.ServantException;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */

public class BaseTest {

    protected Core core;

    @Before
    public void iniciazize() throws ServantException {
        core = new CoreImpl(Configuration.class);
    }

}
