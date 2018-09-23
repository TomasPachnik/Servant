package sk.tomas.servant;

import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;
import sk.tomas.servant.annotation.PostInit;
import sk.tomas.servant.core.Servant;

import java.sql.SQLOutput;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */
@Bean
public class First {

    private String first = "first";

    @Inject
    private Second third;

    @PostInit
    public void method() {
        System.out.println("post init is working!");
    }


    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public Second getThird() {
        return third;
    }

}
