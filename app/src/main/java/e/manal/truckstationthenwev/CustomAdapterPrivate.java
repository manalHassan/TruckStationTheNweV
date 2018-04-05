package e.manal.truckstationthenwev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by amerah on 2/26/2018 AD.
 */

public class CustomAdapterPrivate extends BaseAdapter {

    Context c;
    ArrayList<PrivateFoodTruckOwner> dogies;
    LayoutInflater inflater;


    public CustomAdapterPrivate(Context c, ArrayList<PrivateFoodTruckOwner> dogies) {
        this.c = c;
        this.dogies = dogies;
    }



///////////
///////////////////////////
    @Override
    public int getCount() {
        return dogies.size();
    }

    @Override
    public Object getItem(int i) {
        return dogies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {

        if (inflater== null)
        {
            inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } if(convertview==null)
        {
            convertview= inflater.inflate(R.layout.listview_layout,viewGroup,false);

        }
        String a;
        MyHolderPrivate holder= new MyHolderPrivate(convertview);
        holder.nameTxt.setText(dogies.get(i).getFUsername());
        holder.cusin.setText(dogies.get(i).getQusins());
        holder.ratingBar.setRating(dogies.get(i).getSumRate());
        a= Integer.toString(dogies.get(i).getNumCus());
        holder.numofrate.setText(a);
        PicassoClient.downloadimg(c,dogies.get(i).getUrl(),holder.img);



        return convertview;
    }
}
