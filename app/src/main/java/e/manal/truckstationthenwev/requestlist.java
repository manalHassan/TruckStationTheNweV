package e.manal.truckstationthenwev;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by wafa0 on 15/03/18.
 */

public class requestlist  extends ArrayAdapter<Request> {
        private Activity context;
    private String first="";
    private String last="";
        List<Request> artists;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
        public requestlist(Activity context, List<Request> artists) {
            super(context, R.layout.requestlist, artists);
            this.context = context;
            this.artists = artists;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
          final View listViewItem = inflater.inflate(R.layout.requestlist, null, true);


            TextView info = (TextView) listViewItem.findViewById(R.id.info);

            Request artist = artists.get(position);


            myRef.child("Customer").child(artist.getCID()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String f = dataSnapshot.child("cfirstName").getValue(String.class);
                    String l= dataSnapshot.child("clastName").getValue(String.class);
                   first=f;
                   last=l;

                    TextView Name = (TextView) listViewItem.findViewById(R.id.name);

                    Name.setText(first+" "+last);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




            info.setText(artist.getRStatus());
            return listViewItem;
        }
    }