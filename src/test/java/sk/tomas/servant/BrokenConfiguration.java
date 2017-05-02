package sk.tomas.servant;

import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Config;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */

@Config
public class BrokenConfiguration {

    @Bean
    public First first() {
        return new First();
    }

    @Bean()
    public Second second() {
        return null;
    }

}
