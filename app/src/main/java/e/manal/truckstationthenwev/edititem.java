package e.manal.truckstationthenwev;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by wafaa7maD on 21/02/18.
 */

public class edititem extends AppCompatActivity {

String mid="";
    Context context;
    EditText name,des;
    EditText price;
    String cid="";
    //private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edititem);
        Bundle b = getIntent().getExtras();
        cid = b.getString("cid");
        name = (EditText) findViewById(R.id.name);
        price = (EditText) findViewById(R.id.price);
        des = (EditText) findViewById(R.id.des);


        

        //Start Image code

}

    public void additem(View view) {
        final String p=price.getText().toString().trim();
        final double pr;
        try{
             pr = Double.parseDouble(p);}
        catch (Exception e){
            Toast.makeText(this, "الرجاء ادخال السعر بشكل صحيح ", Toast.LENGTH_LONG).show();
            return;
        }
        final String d = des.getText().toString().trim();
        final String n = name.getText().toString().trim();

        if (!TextUtils.isEmpty(n) && !TextUtils.isEmpty(p) && !TextUtils.isEmpty(d)) {
           //1 get menu id
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            String id = user.getUid();//customer id is the same as rating id to make it easy to refer
            myRef.child("Menu").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mid = dataSnapshot.child("mid").getValue(String.class);
                    //2 add item
                    String tid =myRef.push().getKey();
                    Item t=new Item();
                    t.setCatID(cid);
                    t.setIdescription(d);
                    t.setIName(n);
                    t.setIPicture("here url");
                    t.setIPrice(pr);
                    t.setItemID(tid);

                    myRef.child("Item").child(mid).child(cid).child(tid).setValue(t);;
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //3 back to items list
            Toast.makeText(this, "تمت اللإضافة", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, itemlist.class);
            Bundle b=new Bundle();
            b.putString("cid",cid);
            intent.putExtras(b);
            startActivity(intent);

        }
          else {
            Toast.makeText(this, "الرجاء تعبئة جميع الحقول ", Toast.LENGTH_LONG).show();
            return;

        }

}



}