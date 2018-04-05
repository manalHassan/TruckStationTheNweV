package e.manal.truckstationthenwev;

/**
 * Created by wafa0 on 19/03/18.
 */

public class Chatroom {
    private String Cname;
        private String CID ;
        private String FID ;
        private  String room;



    public Chatroom (){

    }
    public Chatroom (Chatroom c){
        this.setFID(c.FID);
        this.setCID(c.CID);
        this.setCname(c.Cname);
        this.setroom(c.room);}

    public Chatroom(String CID, String FID, String room, String cname) {
        this.CID = CID;
        this.FID = FID;
        this.room = room;
       this.Cname = cname;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        this.Cname = cname;
    }
        public void setFID(String FID) {
            this.FID = FID;
        }
        public void setCID(String CID) {
            this.CID = CID;
        }
        public  String getroom() { return room;}
        public String getFID() {
            return FID;
        }
        public  String getCID() {
            return CID;
        }


    public void setroom(String room) {
        this.room = room;
    }
}
