package e.manal.truckstationthenwev;

/**
 * Created by manal on 2/8/2018.
 */

public class Item {
    private String  ItemID ;
    private String  CatID ;
    private String  IName , IPicture , Idescription;
    private double IPrice ;

    public Item(){

    }

    public Item(Item i){

        this.setCatID(i.CatID);
        this.setIdescription(i.Idescription);
        this.setIName(i.IName);
        this.setIPicture(i.IPicture);
        this.setItemID(i.ItemID);
        this.setIPrice(i.IPrice);

    }


public Item (String IName ,String IPicture ,String Idescription , double IPrice  ,String  CatID , String  ItemID ){

    this.setCatID(CatID);
    this.setIdescription(Idescription);
    this.setIName(IName);
    this.setIPicture(IPicture);
    this.setItemID(ItemID);
    this.setIPrice(IPrice);

}
    public void setCatID(String  catID) {
        CatID = catID;
    }

    public void setIdescription(String idescription) {
        Idescription = idescription;
    }

    public void setIName(String IName) {
        this.IName = IName;
    }

    public void setIPicture(String IPicture) {
        this.IPicture = IPicture;
    }

    public void setIPrice(double IPrice) {
        this.IPrice = IPrice;
    }

    public  void setItemID(String  itemID) {
        ItemID = itemID;
    }

    public double getIPrice() {
        return IPrice;
    }

    public String  getCatID() {
        return CatID;
    }

    public  String  getItemID() {
        return ItemID;
    }

    public String getIdescription() {
        return Idescription;
    }

    public String getIName() {
        return IName;
    }

    public String getIPicture() {
        return IPicture;
    }
}
