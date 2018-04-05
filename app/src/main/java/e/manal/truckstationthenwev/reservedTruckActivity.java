package e.manal.truckstationthenwev;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class reservedTruckActivity extends AppCompatActivity {


    private RecyclerView privateList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Query mQueryCurrentCustomerList;

    @Nullable
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_truck);

        privateList= findViewById(R.id.privateFollowingList1);
        privateList.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(reservedTruckActivity.this);
        layoutManager.setStackFromEnd(false);
        privateList.setLayoutManager(layoutManager);

    }


    @Override
    public void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        String currentUID= mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("PreviousReservedTrucks");
        if(databaseReference.child(currentUID).equals(null)){
            Toast.makeText(reservedTruckActivity.this, "لا يوجد لدبك عربات محجوزة", Toast.LENGTH_LONG).show();
        }
        else{
            databaseReference = FirebaseDatabase.getInstance().getReference().child("PreviousReservedTrucks").child(currentUID);
            mQueryCurrentCustomerList = databaseReference.orderByChild("name");

            FirebaseRecyclerAdapter<PreviousReservedTrucks,PostHolder> FBRA =new FirebaseRecyclerAdapter< PreviousReservedTrucks, PostHolder>(
                    PreviousReservedTrucks.class,
                    R.layout.activity_reserved_truck_row,
                    PostHolder.class,
                    mQueryCurrentCustomerList

            ) {
                @Override
                protected void populateViewHolder(PostHolder viewHolder, PreviousReservedTrucks model, int position) {
                    final String truckID=getRef(position).getKey();
                    viewHolder.setName(model.getName());
                    viewHolder.setPic(reservedTruckActivity.this.getApplicationContext(), model.getPic());

                }
            };
            privateList.setAdapter(FBRA);
        }



    }


    public static class PostHolder extends RecyclerView.ViewHolder{

        public PostHolder(View itemView) {
            super(itemView);
            View mView= itemView;

        }

        public void setName(String name){
            TextView postName= (TextView) itemView.findViewById(R.id.truckReName);
            postName.setText(name);
        }


        public void setPic(Context ctx, String image){
            ImageView post_image=(ImageView) itemView.findViewById(R.id.truckReImage);
            Picasso.with(ctx).load(image).into(post_image);
        }



    }



}
