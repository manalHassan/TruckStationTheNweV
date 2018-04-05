package e.manal.truckstationthenwev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Logout1 extends AppCompatActivity {
    private FirebaseAuth auth;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout1);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        btn=findViewById(R.id.button4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Logout:
                auth.signOut();
                if (auth.getCurrentUser() == null) {
                    // User is not logged in

                    Toast.makeText(Logout1.this, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Logout1.this, CustomerLogInPage.class));
                    finish();
                }
// this listener will be called when there is change in firebase user session
                FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user == null) {
                            Toast.makeText(Logout1.this, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Logout1.this, CustomerLogInPage.class));
                            finish();
                        }
                    }
                };
            break;
        }
        return true;
    }
    public void rating(View view){
        startActivity(new Intent(Logout1.this, Rating.class));

    }
}
