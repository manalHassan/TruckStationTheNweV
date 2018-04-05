package e.manal.truckstationthenwev;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by manal on 2/8/2018.
 */

public class PreOrder {
    private String PreID ;
    private String CID , FID , Cart , PDate , PTime ;
    private double price ;
    private DateTimeFormatter dtf ;
@RequiresApi(api = Build.VERSION_CODES.O)
public PreOrder (String CID , String FID , String Cart , String PDate , String PTime , double price ,String PreID ){
    this.setCart(Cart);
    this.setCID(CID);
    this.setFID(FID);
    this.setPreID(PreID);
    this.setPrice(price);
    dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    // System.out.println(dtf.format(now)); //2016/11/16 12:08:43
    this.setPDate(dtf.format(now).substring(0,dtf.format(now).indexOf(" ")));
    this.setPTime(dtf.format(now).substring(dtf.format(now).indexOf(" ")));

}
    public void setCID(String CID) {
        this.CID = CID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public void setPDate(String PDate) {
        this.PDate = PDate;
    }

    public void setCart(String cart) {
        Cart = cart;
    }

    public void setPreID(String preID) {
        PreID = preID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPTime(String PTime) {
        this.PTime = PTime;
    }

    public String getFID() {
        return FID;
    }

    public String getCID() {
        return CID;
    }

    public String getPDate() {
        return PDate;
    }

    public double getPrice() {
        return price;
    }

    public String  getPreID() {
        return PreID;
    }

    public String getCart() {
        return Cart;
    }

    public String getPTime() {
        return PTime;
    }

}

