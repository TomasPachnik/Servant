package sk.tomas.servant;

import sk.tomas.servant.annotation.Inject;

/**
 * Created by Tomas Pachnik on 02-May-17.
 */
public class Second {

    private String second = "second";

    @Inject
    private First first;

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public First getFirst() {
        return first;
    }

}
