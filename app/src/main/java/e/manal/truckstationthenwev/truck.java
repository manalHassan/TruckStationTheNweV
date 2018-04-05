package e.manal.truckstationthenwev;

/**
 * Created by amerah on 2/14/2018 AD.
 */

public class truck {


    public String getTruckname() {
        return truckname;
    }

    public void setTruckname(String truckname) {
        this.truckname = truckname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String truckname;
    private String url;

    public truck(String truckname,String url){
        this.truckname=truckname;
        this.url=url;


    }

public truck(){}


}
