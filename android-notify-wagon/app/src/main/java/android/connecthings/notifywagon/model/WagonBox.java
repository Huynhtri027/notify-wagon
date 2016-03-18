package android.connecthings.notifywagon.model;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class WagonBox {

    private int count;
    private List<Wagon> wagons;

    public WagonBox(){
        wagons = new ArrayList<>();
    }

    public int getCount() {
        return count;
    }

    public List<Wagon> getWagons() {
        return wagons;
    }
}
