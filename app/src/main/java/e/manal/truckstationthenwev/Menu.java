package e.manal.truckstationthenwev;

/**
 * Created by manal on 2/8/2018.
 */

public class Menu {
    private String MID ;
    private String FID ;
public Menu (String FID ,    String MID ){
    this.setFID(FID);
    this.setMID(MID);
}
    public void setFID(String FID) {
        this.FID = FID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getFID() {
        return FID;
    }

    public  String getMID() {
        return MID;
    }
}
