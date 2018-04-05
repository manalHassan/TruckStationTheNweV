package e.manal.truckstationthenwev;

/**
 * Created by manal on 2/8/2018.
 */
///////////////
public class PublicFoodTruckOwner  {

    private  String FUsername  , FPassword , FEmail  , FPreOrderStatuse , FWorkingHours , Fstatus , qusins , uid;
    private  int FPoneNoumber ;
    private double XFLication;
    private double YFLocation;
    private String url;
    private boolean canBePrivate ;
    private int sumRate;
    private int numCus;
    private boolean available;
    private boolean allow;

    public int getSumRate() {
        return sumRate;
    }

    public void setSumRate(int sumRate) {
        this.sumRate = sumRate;
    }

    public int getNumCus() {
        return numCus;
    }

    public void setNumCus(int numCus) {
        this.numCus = numCus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PublicFoodTruckOwner (){}

    public PublicFoodTruckOwner(String FUsername, String FPassword, String FEmail,
                                int FPoneNoumber, double XFLication,
                                double YFLocation, String qusins, String FWorkingHours,
                                boolean canBePrivte,String uid,int sumRate,int numCus, boolean allow, boolean available){
        this.setFEmail(FEmail);
        this.setFPassword(FPassword);
        this.setFPoneNoumber(FPoneNoumber);
        this.setFUsername(FUsername);
        this.setXFLication(XFLication);
        this.setYFLocation(YFLocation);
        this.setQusins(qusins);
        this.setFWorkingHours(FWorkingHours);
        this.setCanBePrivate(canBePrivte);
        this.setNumCus(numCus);
        this.setSumRate(sumRate);
        this.setUid(uid);
        this.setAllow(allow);
        this.setAvailable(available);
    }




    public void setQusins(String qusins) {
        this.qusins = qusins;
    }

    public void setFEmail(String FEmail) {
        this.FEmail = FEmail;
    }

    public void setFPassword(String FPassword) {
        this.FPassword = FPassword;
    }

    public void setFPoneNoumber(int FPoneNoumber) {
        this.FPoneNoumber = FPoneNoumber;
    }

    public void setFPreOrderStatuse(String FPreOrderStatuse) {
        this.FPreOrderStatuse = FPreOrderStatuse;
    }

    public void setCanBePrivate(boolean canBePrivate) {
        this.canBePrivate = canBePrivate;
    }

    public void setFstatus(String fstatus) {
        Fstatus = fstatus;
    }


    public void setFUsername(String FUsername) {
        this.FUsername = FUsername;
    }

    public void setFWorkingHours(String FWorkingHours) {
        this.FWorkingHours = FWorkingHours;
    }

    public void setXFLication(double XFLication) {
        this.XFLication = XFLication;
    }

    public void setYFLocation(double YFLocation) {
        this.YFLocation = YFLocation;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getXFLication() {
        return XFLication;
    }

    public double getYFLocation() {
        return YFLocation;
    }

    public int getFPoneNoumber() {
        return FPoneNoumber;
    }

    public String getFEmail() {
        return FEmail;
    }

    public String getFPassword() {
        return FPassword;
    }

    public String getFPreOrderStatuse() {
        return FPreOrderStatuse;
    }

    public String getFstatus() {
        return Fstatus;
    }


    public String getFUsername() {
        return FUsername;
    }

    public boolean getCanBePrivate (){
        return canBePrivate ;
    }
    public String getFWorkingHours() {
        return FWorkingHours;
    }

    public String getQusins() {
        return qusins;
    }

    public String getUid() {
        return uid;
    }

    public boolean isAvailable(){ return available;}

    public boolean isAllow(){ return allow;
    }

    public void setAvailable(boolean available){
        this.available=available;
    }

    public void setAllow(boolean allow){
        this.allow=allow;
    }
}