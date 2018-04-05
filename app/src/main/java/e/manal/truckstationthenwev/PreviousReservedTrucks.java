package e.manal.truckstationthenwev;

/**
 * Created by HP on 4/4/2018.
 */

public class PreviousReservedTrucks {

    private String pic;
    private String name;


    public PreviousReservedTrucks( ){
    }

    public PreviousReservedTrucks(String pic, String name ){
        this.setName(name);
        this.setPic(pic);
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic(){return pic;}

    public String getName(){return name;}

}
