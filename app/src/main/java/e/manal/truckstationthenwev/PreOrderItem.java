package e.manal.truckstationthenwev;

/**
 * Created by manal on 2/8/2018.
 */

public class PreOrderItem {
    private  String PreID ,ItemID  ;
    private int  quantity ;

    public PreOrderItem (String  PreID , String  ItemID , int quantity ){
        this.setItemID(ItemID);
        this.setPreID(PreID);
        this.setQuantity(quantity);
    }

    public void setItemID(String  itemID) {
        ItemID = itemID;
    }

    public void setPreID(String preID) {
        PreID = preID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public  String  getPreID() {
        return PreID;
    }

    public String  getItemID() {
        return ItemID;
    }

    public int getQuantity() {
        return quantity;
    }
}
