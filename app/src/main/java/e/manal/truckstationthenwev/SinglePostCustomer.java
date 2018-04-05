package e.manal.truckstationthenwev;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SinglePostCustomer extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String post_id = null;
    private ImageView postImage;
    private TextView post_title;
    private TextView post_dec;
    private TextView post_OwnerID;
    private TextView post_OwnerName;
    private EditText CommentText;
    private DatabaseReference databaseCustomer;
    private FirebaseUser customerID;
    DatabaseReference databaseReferenceComment;
    private Query mQueryCurrentPost;
    FirebaseAuth mAuth;
    private RecyclerView mCommentList;
    private int commentCounter;
    private TextView commentsCountTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post_customer);
        commentsCountTxt=(TextView) findViewById(R.id.commentsCountTextView);
        commentsCountTxt.setText("0");
        mCommentList= findViewById(R.id.commentsRecyclerView);
        //mCommentList.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(SinglePostCustomer.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mCommentList.setLayoutManager(layoutManager);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("postsTest6");
        post_id = getIntent().getExtras().getString("post_id");
        mAuth = FirebaseAuth.getInstance();
        databaseReferenceComment = FirebaseDatabase.getInstance().getReference().child("commentTest");
        mQueryCurrentPost = databaseReferenceComment.orderByChild("postID").equalTo(post_id);


        postImage = (ImageView) findViewById(R.id.single_post_image);
        post_title = (TextView) findViewById(R.id.single_imageTitle);
        post_dec = (TextView) findViewById(R.id.single_imageDesc);
        post_OwnerName = (TextView) findViewById(R.id.single_userName);
        CommentText = (EditText) findViewById(R.id.commentEditText);
        mDatabase.child(post_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String postTitle = (String) dataSnapshot.child("name").getValue();
                String postDesc = (String) dataSnapshot.child("desc").getValue();
                String postOwnerID = (String) dataSnapshot.child("uid").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();
                String postUsername = (String) dataSnapshot.child("username").getValue();

                post_title.setText(postTitle);
                post_dec.setText(postDesc);
                post_OwnerName.setText(postUsername);

                Picasso.with(SinglePostCustomer.this).load(post_image).into(postImage);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        customerID=mAuth.getCurrentUser();

        databaseCustomer=FirebaseDatabase.getInstance().getReference().child("Customer").child(customerID.getUid());

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<CommentT, PostHolderComment> FBRA =new FirebaseRecyclerAdapter<CommentT, PostHolderComment>(
                CommentT.class,
                R.layout.comment_list_item,
                PostHolderComment.class,
                mQueryCurrentPost

        ) {
            @Override
            protected void populateViewHolder(PostHolderComment viewHolder, CommentT model, int position) {
                viewHolder.setComment(model.getComment());
                viewHolder.setUsername(model.getUsername());

                commentCounter= getItemCount();
                String com=""+commentCounter;
                commentsCountTxt.setText(com);

            }
        };

        mCommentList.setAdapter(FBRA);

    }

    public void sendComment(View view) {
        final String comment = CommentText.getText().toString().trim();
        if (!TextUtils.isEmpty(comment)) {
            databaseCustomer.addValueEventListener(new ValueEventListener() {
                final DatabaseReference newComment= databaseReferenceComment.push();
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newComment.child("comment").setValue(comment);
                    newComment.child("postID").setValue(post_id);
                    newComment.child("uid").setValue(customerID.getUid());
                    newComment.child("username").setValue(dataSnapshot.child("cfirstName").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast toast=Toast.makeText(SinglePostCustomer.this, "تم إرسال التعليق", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                CommentText.setText("");
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

    public static class PostHolderComment extends RecyclerView.ViewHolder{

        public PostHolderComment(View itemView) {
            super(itemView);
            View mView= itemView;
        }

        public void setComment(String commentTxt){
            TextView comment= (TextView) itemView.findViewById(R.id.commentText);
            comment.setText(commentTxt);
        }

        public void setUsername(String username){
            TextView userName= (TextView) itemView.findViewById(R.id.usernameComment);
            userName.setText(username);
        }



    }

}
