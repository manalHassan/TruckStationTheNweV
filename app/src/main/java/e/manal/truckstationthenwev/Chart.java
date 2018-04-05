package e.manal.truckstationthenwev;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Chart extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private long   NoOfPublic =0;
    private long  NoOfPrivate =0;
    private long NoOfCustoemrs = 0 ;
    private boolean first = false;
    private boolean first2 = false;
    private boolean first3 = false;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = db.getReference();
    private static String TAG = "MainActivity";

    static float[] yData = { 2f , 3f  ,12f};
    private String[] xData = {"عملاء", "عربات حفلات خاصه" , "عربات عامه"};
    PieChart pieChart;
FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_drawer);
        Log.d(TAG, "onCreate: starting to create chart");
        pieChart = (PieChart) findViewById(R.id.chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
      /*  dbRef.child("PublicFoodTruckOwner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                try {
                    NoOfPublic  = snapshot.getChildrenCount();
                    yData[2] = (float) NoOfPublic ;
                    first3 = true;
                    Toast.makeText(Chart.this,"private "+yData[1], Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(Chart.this, "حدث خطاء ما !!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbRef.child("PrivateFoodTruckOwner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                try {
                    NoOfPrivate  = snapshot.getChildrenCount();
                    yData[1] = (float) NoOfPrivate ;
                    first = true;
                    Toast.makeText(Chart.this,"private "+yData[1], Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(Chart.this, "حدث خطاء ما !!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbRef.child("Customer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                try {
                    NoOfCustoemrs  = snapshot.getChildrenCount();
                    yData[0] = (float) NoOfPrivate ;
                    first2 = true;
                    Toast.makeText(Chart.this,"customers "+yData[0], Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(Chart.this, "حدث خطاء ما !!", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
     setUpTheChart();

    }
public void setUpTheChart(){
    //  pieChart.setDescription(Sales by employee (In Thousands $));
    pieChart.setRotationEnabled(true);
    //pieChart.setUsePercentValues(true);
    //pieChart.setHoleColor(Color.BLUE);
    //pieChart.setCenterTextColor(Color.BLACK);
    pieChart.setHoleRadius(25f);
    pieChart.setTransparentCircleAlpha(0);
    pieChart.setCenterText("احصائيه مستخدمي التطبيق");
    pieChart.setCenterTextSize(10);
    pieChart.animateY(2000);
    //pieChart.setDrawEntryLabels(true);
    //pieChart.setEntryLabelTextSize(20);
    //More options just check out the documentation!

    addDataSet();

    pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            Log.d(TAG, "onValueSelected: Value select from chart.");
            Log.d(TAG, "onValueSelected: " + e.toString());
            Log.d(TAG, "onValueSelected: " + h.toString());

            int pos1 = e.toString().indexOf("(sum): ");
            String sales = e.toString().substring(pos1 + 7);
        }

        @Override
        public void onNothingSelected() {

        }
    });

}
    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , xData[i]));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "احصائيه مستخدمي التطبيق");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);


        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, (android.view.Menu) menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, customer_profilenew.class);
            Bundle b=new Bundle();
            //b.putString("id",user);
            //intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } else if (id == R.id.nav_publiclist) {
            Intent intent = new Intent(this, ListPuplic.class);
            Bundle b=new Bundle();
            // b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_privatelist) {
            Intent intent = new Intent(this, ListPrivate.class);
            Bundle b=new Bundle();
            // b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, VisitorHomePage.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }

        else if (id == R.id.nav_nearmap) {
            Intent intent = new Intent(this, NearByTrucks.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            // intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }

        else if (id == R.id.nav_pre_request) {
/*
            Intent intent = new Intent(this, ownermenu.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
            */
        }


        else if (id == R.id.nav_pre_preorder) {
/*
  Intent intent = new Intent(this, editprofile.class);
            Bundle b=new Bundle();
            b.putString("id",user);
            intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
 */
        }
        else if (id == R.id.nav_app) {

            Intent intent = new Intent(this, Chart.class);
            Bundle b=new Bundle();
            //  b.putString("id",user);
            //  intent.putExtras(b);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_logout) {

            auth.signOut();
            if(auth.getCurrentUser() == null){
                Toast.makeText(this , "تم تسجيل الدخول بنجاح" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent (this , MainActivity.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            }}

        return false;
    }

}
