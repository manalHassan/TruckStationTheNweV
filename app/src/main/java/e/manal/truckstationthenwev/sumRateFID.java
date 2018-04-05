package e.manal.truckstationthenwev;

/**
 * Created by amerah on 3/24/2018 AD.
 */

public class sumRateFID {

    int sum;



    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    String FID;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    String UID;

    public sumRateFID() {


    }

    public sumRateFID(int sum, int numCus, String FID,String UID) {
        this.sum = sum;
        this.numCus = numCus;
        this.FID = FID;
         this.UID=UID;
    }

    int numCus;


    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getNumCus() {
        return numCus;
    }

    public void setNumCus(int numCus) {
        this.numCus = numCus;
    }

}
