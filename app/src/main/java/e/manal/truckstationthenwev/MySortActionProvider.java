package e.manal.truckstationthenwev;

/**
 * Created by amerah on 3/18/2018 AD.
 */

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class MySortActionProvider extends ActionProvider {

    //implements OnMenuItemClickListener

    private Context mContext;

    public MySortActionProvider(Context context) {
        super(context);
        mContext = context;
    }
    @Override
    public View onCreateActionView(){
        return null;
    }
    @Override
    public boolean hasSubMenu(){
        return true;
    }
    @Override
    public void onPrepareSubMenu(SubMenu subMenu){
        subMenu.clear();
       // subMenu.add("Sort in ascending").setOnMenuItemClickListener();
        MenuItem item1=subMenu.getItem(0);
        item1.setIcon(android.R.drawable.arrow_up_float);
       // subMenu.add("Sort in descedning").setOnMenuItemClickListener(this);
        MenuItem item2=subMenu.getItem(1);
        item2.setIcon(android.R.drawable.arrow_down_float);

    }
    /*
    @Override
    public boolean onMenuItemClick(MenuItem item){
      //  if(item.getTitle().equals("Sort in ascending"))
            //LstFragment.sortList(-1);
       // else
           // LstFragment.sortList(1);
        //return true;
    }*/
}


