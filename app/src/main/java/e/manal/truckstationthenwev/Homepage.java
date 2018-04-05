package e.manal.truckstationthenwev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

    }

    public void test(View view){

            Intent intent = new Intent(Homepage.this, MyMain.class);
      startActivity(intent);

}
}