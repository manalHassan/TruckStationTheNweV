package e.manal.truckstationthenwev;

/**
 * Created by HP on 3/18/2018.
 */

public class PublicFowllower {
    private String pic;
    private String name;
    private String type;
    public PublicFowllower(){}
    public PublicFowllower(String pic, String name , String type){
        this.setName(name);
        this.setPic(pic);
        this.setType(type);
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic(){return pic;}

    public String getName(){return name;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
