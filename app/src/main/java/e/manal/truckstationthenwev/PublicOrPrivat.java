package e.manal.truckstationthenwev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by manal on 2/10/2018.
 */

public class PublicOrPrivat extends AppCompatActivity {
    Button Public , Private ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_or_privat);

        Public = (Button) findViewById(R.id.publico);
        Private =(Button) findViewById(R.id.privateO);



    }

    public void goToPublicLogIn (View view){
        Intent intent = new Intent(PublicOrPrivat.this , publicOnerLogIn.class );
        startActivity(intent);

    }


    public void goToPrivateLogInPage (View view){
        Intent intent = new Intent(PublicOrPrivat.this , privateOwnerLogIn.class );
        startActivity(intent);

    }


}
