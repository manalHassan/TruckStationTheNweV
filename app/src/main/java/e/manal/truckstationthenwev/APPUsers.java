package e.manal.truckstationthenwev;

/**
 * Created by HP on 3/4/2018.
 */

public class APPUsers {

    //private String UID;
    private String Type;

    public APPUsers(String Type) {
        //this.setUID(UID);
        this.setType(Type);
    }

    public void setUID(String UID) {
       // this.UID = UID;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getType() {
        return Type;
    }
}
