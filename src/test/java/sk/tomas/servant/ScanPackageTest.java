package sk.tomas.servant;

import org.junit.Test;
import sk.tomas.servant.core.Servant;

public class ScanPackageTest {

    @Test
    public void scanPackageTest() {
        Servant.scanPackage("sk.tomas.servant");
    }

}
