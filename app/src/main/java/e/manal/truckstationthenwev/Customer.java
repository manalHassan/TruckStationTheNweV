package e.manal.truckstationthenwev;

/**
 * Created by manal on 2/8/2018.
 */

public class Customer  {
    private  String    CPassword , CEmail ,CFirstName, CLastName , uid;
    private  int CPoneNoumber ;
    private double XCLication;
    private double YCLocation;

    public Customer(){}

    public Customer (  String CPassword ,String  CEmail , String CFirstName,String  CLastName ,int CPoneNoumber ,double  XCLication,double  YCLocation , String uid){
        this.setCEmail( CEmail);

        this.setCFirstName( CFirstName);

        this.setCLastName(CLastName);

        this.setCPassword(CPassword) ;

        this.setCPoneNoumber( CPoneNoumber) ;


        this.setXCLication( XCLication);

        this.setYCLocation( YCLocation);
        this.setUid (uid);
    }

    public void setCEmail(String CEmail) {
        this.CEmail = CEmail;
    }

    public void setCFirstName(String CFirstName) {
        this.CFirstName = CFirstName;
    }

    public void setCLastName(String CLastName) {
        this.CLastName = CLastName;
    }

    public void setCPassword(String CPassword) {
        this.CPassword = CPassword;
    }

    public void setCPoneNoumber(int CPoneNoumber) {
        this.CPoneNoumber = CPoneNoumber;
    }


    public void setXCLication(double XLication) {
        this.XCLication = XLication;
    }

    public void setYCLocation(double YLocation) {
        this.YCLocation = YLocation;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getXCLication() {
        return XCLication;
    }

    public double getYCLocation() {
        return YCLocation;
    }

    public int getCPoneNoumber() {
        return CPoneNoumber;
    }

    public String getCEmail() {
        return CEmail;
    }

    public String getCFirstName() {
        return CFirstName;
    }

    public String getCLastName() {
        return CLastName;
    }

    public String getCPassword() {
        return CPassword;
    }

    public String getUid() {
        return uid;
    }
}

