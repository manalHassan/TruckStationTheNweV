package e.manal.truckstationthenwev;

/**
 * Created by manal on 2/8/2018.
 */

public class Subscription {
    //FUnf = follow or unfollow if its 1=follow , 0=unfollow
    private String FUnf;
    private String CID , FID;

    public Subscription (String CID , String FID,String FUnf){
        this.setCID(CID);
        this.setFID(FID);
        this.setFUnf(FUnf);
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public void setFUnf(String FUnf) { this.FUnf = FUnf;}

    public String getFID() {
        return FID;
    }

    public String getCID() {
        return CID;
    }
    public String getFUnf() {
        return FUnf;
    }

}
