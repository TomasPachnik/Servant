package sk.tomas.servant;

import sk.tomas.servant.annotation.Inject;
import sk.tomas.servant.core.Servant;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */
public class First {

    private String first = "first";

    public First() {
        Servant.addToContext(this);
    }

    @Inject
    private Second third;

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
