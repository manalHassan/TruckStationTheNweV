package e.manal.truckstationthenwev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by manal on 3/28/2018.
 */

public class cartArray extends BaseAdapter {
    Context c;
    ArrayList<cartItem> artists;
    LayoutInflater inflater;


    public cartArray(Context c, ArrayList<cartItem> artists) {
        this.c = c;
        this.artists = artists;
    }
    @Override
    public int getCount() {
        return artists.size();

    }

    @Override
    public Object getItem(int i) {
        return artists.get(i);
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
            convertview= inflater.inflate(R.layout.listviewtracks_layout,viewGroup,false);

        }

        itemHolder holder= new itemHolder(convertview);
        holder.nameprice.setText((int) artists.get(i).getPrice1()+"رس");////لازم سترنق***********
        holder.nameitem.setText(artists.get(i).getCatItem()+"");//





        return convertview;
    }

}
