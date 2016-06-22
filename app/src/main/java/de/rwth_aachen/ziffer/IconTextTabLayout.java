package de.rwth_aachen.ziffer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

public class IconTextTabLayout extends TabLayout {

    private static int[] ICONS = new int[] { android.R.drawable.ic_dialog_dialer,
                                             android.R.drawable.ic_dialog_dialer,
                                             android.R.drawable.ic_dialog_dialer,
                                             android.R.drawable.stat_notify_chat };

    public IconTextTabLayout(Context context) {
        super(context);
    }

    public IconTextTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconTextTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTabsFromPagerAdapter(@NonNull PagerAdapter adapter) {
        this.removeAllTabs();
        int i = 0;
        for (int count = adapter.getCount(); i < count; ++i) {
            this.addTab(this.newTab().setCustomView(R.layout.custom_tab)
                    .setIcon(ICONS[i])
                    .setText(adapter.getPageTitle(i)));
        }
    }
}
