package e.manal.truckstationthenwev;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Tab1FragmentPrivateFollow extends Fragment {

    private RecyclerView privateList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Query mQueryCurrentCustomerList;
    DatabaseReference FUnfRef;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab1_fragment_private,container,false);

        privateList= view.findViewById(R.id.privateFollowingList);
        privateList.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(false);
        privateList.setLayoutManager(layoutManager);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        String currentUID= mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("PublicFowllower");
        if(databaseReference.child(currentUID).equals(null)){
            Toast.makeText(getActivity(), "اطلع برا", Toast.LENGTH_LONG).show();
        }
        else{
            databaseReference = FirebaseDatabase.getInstance().getReference().child("PublicFowllower").child(currentUID);
            mQueryCurrentCustomerList = databaseReference.orderByChild("type").equalTo("private");

            FirebaseRecyclerAdapter<PublicFowllower,Tab1FragmentPrivateFollow.PostHolder> FBRA =new FirebaseRecyclerAdapter< PublicFowllower, Tab1FragmentPrivateFollow.PostHolder>(
                    PublicFowllower.class,
                    R.layout.row,
                    Tab1FragmentPrivateFollow.PostHolder.class,
                    mQueryCurrentCustomerList

            ) {
                @Override
                protected void populateViewHolder(Tab1FragmentPrivateFollow.PostHolder viewHolder, PublicFowllower model, int position) {
                    final String truckID=getRef(position).getKey();
                    viewHolder.setName(model.getName());
                    viewHolder.setImage(getActivity().getApplicationContext(), model.getPic());

                    viewHolder.unFollow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FUnfRef= FirebaseDatabase.getInstance().getReference("PublicFowllower");
                            FUnfRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                                    String id = user.getUid();//customer id is the same as rating id to make it easy to refer
                                    FUnfRef.child(id).child(truckID).removeValue();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            });

                        }
                    });

                }
            };
            privateList.setAdapter(FBRA);
        }



    }


    public static class PostHolder extends RecyclerView.ViewHolder{
        public Button unFollow;
        public PostHolder(View itemView) {
            super(itemView);
            View mView= itemView;
            unFollow= (Button)itemView.findViewById(R.id.unFollow);
        }

        public void setName(String name){
            TextView postName= (TextView) itemView.findViewById(R.id.ftruckName);
            postName.setText(name);
        }


        public void setImage(Context ctx,String image){
            ImageView post_image=(ImageView) itemView.findViewById(R.id.ftruckImage);
            Picasso.with(ctx).load(image).into(post_image);
        }



    }


}
