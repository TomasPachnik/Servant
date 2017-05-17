package sk.tomas.servant;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import sk.tomas.servant.core.Core;
import sk.tomas.servant.exception.BeanNotFoundException;
import sk.tomas.servant.exception.CannotCreateBeanExcetion;
import sk.tomas.servant.exception.ServantException;
import sk.tomas.servant.exception.WrongObjectTypeException;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */
public class MyTest extends BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void NotFoundTest() throws BeanNotFoundException {
        Core.getByName("first");
        Core.getByName("third");
        thrown.expect(BeanNotFoundException.class);
        thrown.expectMessage("Bean 'second' not found");
        Core.getByName("second");
    }

    @Test
    public void dependencyInjectionTest() throws BeanNotFoundException {
        Assert.assertTrue(((First) Core.getByName("first")).getThird().getSecond().equals("second"));
    }

    @Ignore
    @Test
    public void NpeTest() throws ServantException {
        thrown.expect(BeanNotFoundException.class);
        thrown.expectMessage("Bean 'third' not found");
        Core.addConfiguration(BrokenConfiguration.class);
    }

    @Test
    public void UpdateTest() throws ServantException {
        First first = (First) Core.getByName("first");
        First second = new First();
        second.setFirst("second");
        Core.updateByName("first", second);
        First third = (First) Core.getByName("first");
        Assert.assertTrue(first.getFirst().equals("first") && third.getFirst().equals("second"));
    }

    @Test
    public void UpdateNegativeTest() throws ServantException {
        Second second = new Second();
        thrown.expect(WrongObjectTypeException.class);
        thrown.expectMessage("Bean 'first' is class sk.tomas.servant.First, but you set class sk.tomas.servant.Second!");
        Core.updateByName("first", second);
    }

}
