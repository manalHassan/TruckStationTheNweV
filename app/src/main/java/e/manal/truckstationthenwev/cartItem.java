package e.manal.truckstationthenwev;

/**
 * Created by manal on 3/18/2018.
 */

public class cartItem {
   private String  catItem , Fid , cIId;
   private double price1 ;



   public cartItem(){}

   public cartItem (String c , double price , String Fid , String cIId){
       catItem = c ;
       price1 = price ;
       this.Fid = Fid ;
       this.cIId = cIId ;

   }

    public void setCatItem(String catItem) {
        this.catItem = catItem;
    }

    public String getCatItem() {
        return catItem;
    }

    public void setPrice1(double price1) {
        this.price1 = price1;
    }

    public double getPrice1() {
        return price1;
    }

    public void setFid(String fid) {
        Fid = fid;
    }

    public String getFid() {
        return Fid;
    }

    public void setcIId(String cIId) {
        this.cIId = cIId;
    }

    public String getcIId() {
        return cIId;
    }
}
