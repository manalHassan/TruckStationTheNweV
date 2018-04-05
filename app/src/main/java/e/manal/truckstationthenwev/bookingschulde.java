package e.manal.truckstationthenwev;

/**
 * Created by wafa0 on 18/03/18.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class bookingschulde extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String id = "";
    String cid = "";
    String mid = "";
    ListView listViewArtists;
    List<Request> artists;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookingschulde);
        artists = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        //
        //attaching value event listener
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer

        myRef.child("Request").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artists.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Request artist = new Request(ds.getValue(Request.class));
                    artists.add(artist);
                    Event ev1 = new Event(Color.RED, 100  ,"lckcks");
                    Event ev2 = new Event(Color.RED, 152143462L  ,"kkk");
                    Event ev = new Event(Color.RED, 1521434620  ,"kkk");
                    compactCalendar.addEvent(ev1);
                    compactCalendar.addEvent(ev2);
                    compactCalendar.addEvent(ev);
                   // String date=artist.getRDate();
                   // Toast.makeText(getApplicationContext(), Long.parseLong(date)+ "لايوجد حجوزات في هذا اليوم=", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

      //  for(int i=0;i<artists.size()-1;i++) {
         //



      //  }
    //
        //Set an event for Teachers' Professional Day 2016 which is 21st of October

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                if (dateClicked.toString().compareTo("Fri Oct 21 00:00:00 AST 2016") == 0) {
                    Toast.makeText(context, "عيد ميلاد نوره", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "لايوجد حجوزات في هذا اليوم", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
              //  actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }
}