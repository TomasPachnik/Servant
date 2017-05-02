package sk.tomas.servant;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import sk.tomas.servant.core.Core;
import sk.tomas.servant.core.impl.CoreImpl;
import sk.tomas.servant.exception.BeanNotFoundException;
import sk.tomas.servant.exception.CannotCreateBeanExcetion;
import sk.tomas.servant.exception.ServantException;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */
public class MyTest extends BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void NotFoundTest() throws BeanNotFoundException {
        core.getByName("first");
        core.getByName("third");

        thrown.expect(BeanNotFoundException.class);
        thrown.expectMessage("Bean 'second' not found");
        core.getByName("second");
    }

    @Test
    public void dependencyInjectionTest() throws BeanNotFoundException {
        Assert.assertTrue(((First) core.getByName("first")).getThird().getSecond().equals("second"));
    }

    @Test
    public void NpeTest() throws ServantException {
        thrown.expect(CannotCreateBeanExcetion.class);
        thrown.expectMessage("Can not initialize. Bean: 'second' is null!");
        Core core = new CoreImpl(BrokenConfiguration.class);
    }

}
