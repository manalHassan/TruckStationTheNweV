package e.manal.truckstationthenwev;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/////////////
public class Itemarrayforcustomer extends ArrayAdapter<Item> {
    private Activity context;
    List<Item> artists;

    public Itemarrayforcustomer(Activity context, List<Item> artists) {
        super(context, R.layout.layout_itemlist, artists);
        this.context = context;
        this.artists = artists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_itemlist, null, true);
        TextView itemName = (TextView) listViewItem.findViewById(R.id.name);
        TextView itemprice = (TextView) listViewItem.findViewById(R.id.itemprice);

        Item artist = artists.get(position);
        itemName.setText(artist.getIName());
        itemprice.setText(""+artist.getIPrice());

        return listViewItem;
    }

}