package e.manal.truckstationthenwev;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hadeel on 2/10/18.
 */

public class publicTab3menu extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    // String id="";
    ListView listViewArtists;
    List<Category> artists;
    EditText editTextName;
    String mid1="";
    FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.public_tab3_menu, container, false);

        //getting views
        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        Button b= rootView.findViewById(R.id.category);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addcategory();
            }
        });
        listViewArtists = (ListView) rootView.findViewById(R.id.listViewTracks);
        //list to store artists
        artists = new ArrayList<>();
        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category artist = artists.get(i);
                showUpdateDeleteDialog(mid1,artist.getCatID(),artist.getCatName() );
                return true;
            }
        });

        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Category artist = artists.get(position);
                //   String
                Intent intent = new Intent(getActivity(), itemlist.class);
                Bundle b=new Bundle();
                b.putString("cid",artist.getCatID());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        myRef.child("Menu").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mid1 = dataSnapshot.child("mid").getValue(String.class);
                myRef.child("Category").child(mid1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        artists.clear();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Category artist =new Category(ds.getValue(Category.class));
                            artists.add(artist);
                        }


                        //creating adapter
                        Category_list artistAdapter = new Category_list(getActivity(), artists);
                        //attaching adapter to the listview
                        listViewArtists.setAdapter(artistAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }



    public void addcategory() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        myRef.child("Menu").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mid = dataSnapshot.child("mid").getValue(String.class);

                String name = editTextName.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    String cid =myRef.push().getKey();
                    Category c=  new Category (name, mid , cid);
                    myRef.child("Category").child(mid).child(cid).setValue(c);
                    editTextName.setText("");
                    Toast.makeText(getActivity(), "تمت اللإضافة", Toast.LENGTH_LONG).show();
                } else {
                    //if the value is not given displaying a toast
                    Toast.makeText(getActivity(), "الرجاء ادخال فئة", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


    }




    private void showUpdateDeleteDialog(final String mid ,final String artistId,final String n) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_category, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.cat);
        editTextName.setText(n);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.category);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.delete);

        dialogBuilder.setTitle("تعديل الفئة");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateCategory(name,mid,artistId);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog diaBox = AskOption(artistId,mid);
                diaBox.show();
                b.dismiss();

            }
        });
    }

    private void deleteCategory(String cid,String mid) {


        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Category").child(mid).child(cid);

        //removing artist
        dR.removeValue();

        //getting the tracks reference for the specified artist
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("Item").child(mid).child(cid);
        //removing all tracks
        drTracks.removeValue();
        Toast.makeText(getActivity(), "تم الحـــذف", Toast.LENGTH_LONG).show();

    }

    private boolean updateCategory(String name, String mid, String cid) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Category").child(mid).child(cid);
        //updating artist
        Category artist = new Category(name,mid,cid);
        dR.setValue(artist);
        Toast.makeText(getActivity(), "تم تحديث الفئة", Toast.LENGTH_LONG).show();
        return true;
    }

    public void addmenu(View view) {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        String mid =myRef.push().getKey();
        Menu menu =new Menu(id,mid);
        myRef.child("Menu").child(id).setValue(menu);
        Toast.makeText(getActivity(), " "+mid+"تم اضافه المنيو", Toast.LENGTH_LONG).show();
    }
    public void additem(View view) {



        /*
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        String tid =myRef.push().getKey();
        Item t= new Item ("vegan pizza" ,"https://www.cicis.com/media/1176/pizza_trad_pepperonibeef.png" ,"this is pizza for vegan" , 33  ,"-L5iy2Lnxk-3j5BtRKZY" , tid );
        myRef.child("Item").child(id).child("-L5iy2Lnxk-3j5BtRKZY").child(tid).setValue(t);
        */
    }
    private AlertDialog AskOption(final String artistId, final String mid)
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("حذف فئة")
                .setMessage("هل تريد حذف الفئة")
                .setIcon(R.drawable.ic_delete)

                .setPositiveButton("حذف", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        deleteCategory(artistId,mid);

                        dialog.dismiss();
                    }

                })



                .setNegativeButton("إالغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}