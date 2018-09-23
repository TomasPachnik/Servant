package sk.tomas.servant;

import org.junit.Before;
import sk.tomas.servant.core.Servant;
import sk.tomas.servant.exception.ServantException;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */

public class BaseTest {

    @Before
    public void iniciazize() {
        Servant.addConfiguration(Configuration.class);
    }

}
