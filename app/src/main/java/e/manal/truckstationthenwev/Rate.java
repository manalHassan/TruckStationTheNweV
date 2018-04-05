package e.manal.truckstationthenwev;

/**
 * Created by manal on 2/8/2018.
 */
public class Rate {
    private String FID, CID;
    private double ratingValue;
    private int numCu;

    public int getNumCu() {
        return numCu;
    }

    public void setNumCu(int numCu) {
        this.numCu = numCu;
    }

    public Rate(){}

    public Rate(String CID, String FID, double ratingValue,int numCu) {
        this.setFID(FID);
        this.setCID(CID);
        this.setRatingValue(ratingValue);
        this.setNumCu(numCu);

    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getCID() {
        return CID;
    }

    public String getFID() {
        return FID;
    }

    public double getRatingValue() {
        return ratingValue;
    }
}