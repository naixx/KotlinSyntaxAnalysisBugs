package me.packbag.android.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Collections;
import java.util.Map;

import me.packbag.android.R;
import me.packbag.android.db.model.ItemStatus;

public class ItemListFragmentsAdapter extends FragmentPagerAdapter {

    static final int          PAGE_COUNT = 3;
    static final ItemStatus[] STATUSES   = new ItemStatus[]{ ItemStatus.CURRENT, ItemStatus.TAKEN, ItemStatus.USELESS };

    private Integer                  tabTitles[] = new Integer[]{ R.string.tab_title_current, R.string.tab_title_taken, R.string.tab_title_useless };
    private Map<ItemStatus, Integer> counts      = Collections.emptyMap();

    private Context context;

    public ItemListFragmentsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (counts.isEmpty()) {
            return context.getString(tabTitles[position]);
        } else {
            ItemStatus status = STATUSES[position];
            Integer count = counts.containsKey(status) ? counts.get(status) : 0;
            return context.getString(tabTitles[position]) + " (" + count + ")";
        }
    }

    public void onEvent(Map<ItemStatus, Integer> itemCounts) {
        counts = itemCounts;
    }
}
