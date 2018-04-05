package e.manal.truckstationthenwev;

/**
 * Created by manal on 3/17/2018.
 */

public class cart {
    private String CID ;
    private long FID ;
    private String ItemName;
    private int quntity ;
    public cart ( ){
        quntity = 0 ;
        this.setCID(CID);
        this.setFID (FID);
        this.setItemName(ItemName);
        this.setQuntity(quntity);
    }

    public String getCID() {
        return CID;
    }

    public long getFID() {
        return FID;
    }

    public int getQuntity() {
        return quntity;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setFID(long FID) {
        this.FID = FID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setQuntity(int quntity) {
        this.quntity=+ quntity;
    }
}
