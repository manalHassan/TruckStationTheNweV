package e.manal.truckstationthenwev;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class Category_list extends ArrayAdapter<Category> {
    private Activity context;
    List<Category> artists;

    public Category_list(Activity context, List<Category> artists) {
        super(context, R.layout.layout_catlist, artists);
        this.context = context;
        this.artists = artists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_catlist, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);

        Category artist = artists.get(position);
        textViewName.setText(artist.getCatName());
        return listViewItem;
    }
}