package e.manal.truckstationthenwev;

/**
 * Created by amerah on 2/12/2018 AD.
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class MyHolder {
    //////////////////////////
    TextView nameTxt;
    TextView cusin ;
    RatingBar ratingBar;
    TextView numofrate;
    ImageView img;
    public MyHolder(View itemView) {

        ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar);
        nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        cusin =(TextView) itemView.findViewById(R.id.cusin);
        img=(ImageView) itemView.findViewById(R.id.dogimage);
        numofrate=(TextView) itemView.findViewById(R.id.numrating);



    }
}
