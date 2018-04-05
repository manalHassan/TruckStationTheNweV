package e.manal.truckstationthenwev;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Follow extends AppCompatActivity {
Button FUnfBtn;

DatabaseReference FUnfRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        FUnfBtn=(Button)findViewById(R.id.followUnfollowBtn);

        FUnfRef= FirebaseDatabase.getInstance().getReference("Subscription");
    }

    public void followUnfollow(View view){

        String type=FUnfBtn.getText().toString().trim();
        if(type=="متابَع"){
            FUnfRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String id = user.getUid();//customer id is the same as rating id to make it easy to refer

                    final String FID="FIDtst";
                    final String CID=id;
                    final String subscribtionId=FID+CID;
                    final String Funfnumber="0";

                    if ( !TextUtils.isEmpty(FID) && !TextUtils.isEmpty(CID) ) {

                        Subscription subscription=new Subscription(CID,FID,Funfnumber);

                        FUnfRef.child(subscribtionId).setValue(subscription);
                        FUnfBtn.setText("تَابع");
                        FUnfBtn.setBackground(getResources().getDrawable(R.drawable.background_signup));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
        else {

            FUnfRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String id = user.getUid();//customer id is the same as rating id to make it easy to refer

                    final String FID="FIDtst";
                    final String CID=id;
                    final String subscribtionId=FID+CID;
                    final String Funfnumber="1";

                    if ( !TextUtils.isEmpty(FID) && !TextUtils.isEmpty(CID) ) {

                        Subscription subscription=new Subscription(CID,FID,Funfnumber);

                        FUnfRef.child(subscribtionId).setValue(subscription);
                        FUnfBtn.setText("متابَع");
                        FUnfBtn.setBackground(getResources().getDrawable(R.drawable.background_follow));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    }
}
