package e.manal.truckstationthenwev;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 3/16/2018.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mfragmentList=new ArrayList<>();
    private final List<String> mfragmentTitleList=new ArrayList<>();

    public void addFragment(Fragment fragment, String title){
        mfragmentList.add(fragment);
        mfragmentTitleList.add(title);

    }



    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mfragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {//return fragment itself
       return mfragmentList.get(position);

    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
}
