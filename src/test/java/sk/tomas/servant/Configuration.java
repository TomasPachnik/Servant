package sk.tomas.servant;

import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Config;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */

@Config
public class Configuration {

    @Bean
    public First first() {
        return new First();
    }

    @Bean("third")
    public Second second() {
        return new Second();
    }

}
