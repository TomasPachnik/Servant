package sk.tomas.servant;

import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Config;
import sk.tomas.servant.annotation.PackageScan;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */

@Config
@PackageScan("sk.tomas.servant")
public class Configuration {

    @Bean("third")
    public Second second() {
        return new Second();
    }

}
