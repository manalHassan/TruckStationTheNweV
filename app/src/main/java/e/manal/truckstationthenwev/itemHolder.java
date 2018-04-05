package e.manal.truckstationthenwev;

import android.view.View;
import android.widget.TextView;

/**
 * Created by amerah on 3/20/2018 AD.
 */
///////////////////
public class itemHolder {

    TextView nameitem;
    TextView nameprice;

    public itemHolder(View itemView) {


        nameitem = (TextView) itemView.findViewById(R.id.nameitem);
        nameprice = (TextView) itemView.findViewById(R.id.nameprice);

    }

}
