package e.manal.truckstationthenwev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //////
Button customer , owner ;
TextView viseter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);

        customer = (Button) findViewById(R.id.customer);
       owner =(Button) findViewById(R.id.owner);
        viseter = (TextView) findViewById(R.id.visitor);


    }

    public void goToVisitorHomePage (View view){
      Intent intent = new Intent(MainActivity.this , viewCart.class);

       startActivity(intent);

    }


    public void goToCustomerLogInPage (View view){
        Intent intent = new Intent(MainActivity.this , CustomerLogInPage.class );
        startActivity(intent);

    }

   public void goToPublicOrPrivat (View view){
        Intent intent = new Intent(MainActivity.this , PublicOrPrivat.class );
        startActivity(intent);

    }


}
