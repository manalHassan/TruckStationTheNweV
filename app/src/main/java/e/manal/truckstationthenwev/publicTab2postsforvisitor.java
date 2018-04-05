package e.manal.truckstationthenwev;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

/**
 * Created by manal on 3/19/2018.
 */

public class publicTab2postsforvisitor extends Fragment {

    private RecyclerView mPostList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Query mQueryCurrentOwner;
    String currentUID="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.public_tab2_postsforcustomer, container, false);
        mPostList= rootView.findViewById(R.id.insta_list);
        //  Bundle b = getActivity().getIntent().getExtras();
        // currentUID = b.getString("id");
        currentUID=((Publicownerforvisitor)getActivity()).getuser1();
        mPostList.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mPostList.setLayoutManager(layoutManager);

        //mAuth = FirebaseAuth.getInstance();
        // String currentUID= mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("postsTest6");
        mQueryCurrentOwner = databaseReference.orderByChild("uid").equalTo(currentUID);
        return rootView;
    }
    //من الفود ترك اي دي اجيب الاوجكت اللي نزل الصورة بعدين قيت اليوزر نيم
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Post,publicTab2postsforvisitor.PostHolder> FBRA =new FirebaseRecyclerAdapter<Post, publicTab2postsforvisitor.PostHolder>(
                Post.class,
                R.layout.public_card,
                publicTab2postsforvisitor.PostHolder.class,
                mQueryCurrentOwner

        ) {
            @Override
            protected void populateViewHolder(publicTab2postsforvisitor.PostHolder viewHolder, Post model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setUserName(model.getUsername());
                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());

            }
        };
        mPostList.setAdapter(FBRA);
    }


    public static class PostHolder extends RecyclerView.ViewHolder{

        public PostHolder(View itemView) {
            super(itemView);
            View mView= itemView;
        }

        public void setName(String name){
            TextView postName= (TextView) itemView.findViewById(R.id.imageTitle);
            postName.setText(name);
        }

        public void setDesc(String desc){
            TextView postDesc= (TextView) itemView.findViewById(R.id.imageDesc);
            postDesc.setText(desc);
        }

        public void setImage(Context ctx, String image){
            ImageView post_image=(ImageView) itemView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }

        public void setUserName(String UserName){
            TextView post_username=(TextView) itemView.findViewById(R.id.userName);
            post_username.setText(UserName);
        }


    }



}


