package e.manal.truckstationthenwev;

import android.os.Build;
import android.support.annotation.RequiresApi;


/**
 * Created by manal on 2/8/2018.
 */

public class Request {
    private String  RID;
    private String CID , FID , RStatus , RDate;
   // private DateTimeFormatter dtf ;
    private double x,y;
    private String notes;

     @RequiresApi(api = Build.VERSION_CODES.O)
     public Request (String CID , String FID  ,String  RID,String RDate,double x,double y,String notes){
         //dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         //LocalDateTime now = LocalDateTime.now();
        // System.out.println(dtf.format(now));
         this.setRDate(RDate);
         this.setCID(CID);
         this.setFID(FID);
         this.setRID(RID);
         this.setRStatus("في الانتظــار");
         this.setX(x);
         this.setY(y);
         this.setNotes(notes);
     }
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
public Request(){}
public Request(Request r){
    this.setRDate(r.RDate);
    this.setCID(r.CID);
    this.setFID(r.FID);
    this.setRID(r.RID);
    this.setRStatus(r.getRStatus());
    this.setX(r.x);
    this.setY(r.y);
    this.setNotes(r.notes);

}
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public void setRDate(String RDate) {
        this.RDate = RDate;
    }

    public void setRID(String  RID) {
        this.RID = RID;
    }

    public void setRStatus(String RStatus) {
        this.RStatus = RStatus;
    }



    public String getFID() {
        return FID;
    }

    public String getCID() {
        return CID;
    }
    public double getX(){return x;}
    public double gety(){return y;}
    public String getRDate() {
        return RDate;
    }

    public String getRID() {
        return RID;
    }

    public String getRStatus() {
        return RStatus;
    }

    public String getNotes(){return notes;}
}

