package e.manal.truckstationthenwev;

/**
 * Created by amerah on 3/11/2018 AD.
 */


public class sumRate {
    int sum;





    public sumRate(){


    }

    public sumRate(int sum, int numCus) {
        this.sum = sum;
        this.numCus = numCus;

    }

    int numCus;





    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getNumCus() {
        return numCus;
    }

    public void setNumCus(int numCus) {
        this.numCus = numCus;
    }
}
