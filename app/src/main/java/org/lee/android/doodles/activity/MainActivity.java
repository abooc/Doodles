package org.lee.android.doodles.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.common.activities.SampleActivityBase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import org.lee.android.doodles.DoubleClickBackExit;
import org.lee.android.doodles.FragmentHandlerAdapter;
import org.lee.android.doodles.FragmentHandlerAdapter.TabInfo;
import org.lee.android.doodles.R;
import org.lee.android.doodles.fragment.CategorysFragment;
import org.lee.android.doodles.fragment.NavigationDrawerFragment;
import org.lee.android.doodles.fragment.PagerFragment;
import org.lee.android.doodles.fragment.SearchFragment;
import org.lee.android.doodles.fragment.TodayFragment;
import org.lee.android.util.Toast;


/**
 * 应用主Activity
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-2-22.
 */
public class MainActivity extends SampleActivityBase
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        CategorysFragment.OnYearChangedListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private FragmentHandlerAdapter mFragmentHandler;
    private FragmentManager mFragmentManager;
    /**
     * x
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        mTitle = getTitle();
        setContentView(R.layout.activity_main);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mFragmentHandler = new FragmentHandlerAdapter(mFragmentManager, this,
                mNavigationDrawerFragment.getTabInfos());
        mNavigationDrawerFragment.setUpDrawerMenu(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mNavigationDrawerFragment.setMenuSelection(0);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(mTitle);
    }

    private int section;

    /**
     * 侧滑菜单ListView
     *
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        section = position;
        TabInfo tabInfo = mFragmentHandler.getTabInfo(position);
        Fragment fragment = mFragmentHandler.getItem(position);
        mFragmentHandler.show(fragment, tabInfo.title);
    }

    /**
     * 当前正在显示的Fragment
     *
     * @param fragment
     */
    public void onShowFragment(Fragment fragment) {
        if (fragment instanceof TodayFragment) {
            TabInfo tabInfo = mFragmentHandler.getTabInfo(0);
            mTitle = tabInfo.title;
            return;
        }
        if (fragment instanceof CategorysFragment) {
            ((CategorysFragment) fragment).setOnYearChangedListener(this);
            TabInfo tabInfo = mFragmentHandler.getTabInfo(1);
            mTitle = tabInfo.title;
            return;
        }
        if (fragment instanceof PagerFragment) {
            TabInfo tabInfo = mFragmentHandler.getTabInfo(2);
            mTitle = tabInfo.title;
            if (mYear != null) {
                ((PagerFragment) fragment).setYear(mYear);
                ((PagerFragment) fragment).attachData(mYear);
                mYear = null;
            }
            return;
        }
        if (fragment instanceof SearchFragment) {
            TabInfo tabInfo = mFragmentHandler.getTabInfo(3);
            mTitle = tabInfo.title;
            return;
        }
    }

    private CategorysFragment.Year mYear;

    /**
     * 年份切换事件
     */
    @Override
    public void onYearChanged(CategorysFragment.Year newYear) {
        mYear = newYear;
        mNavigationDrawerFragment.setMenuSelection(2);
        TabInfo tabInfo = mFragmentHandler.getTabInfo(2);
        mTitle = tabInfo.title;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.activity_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * ActionBar菜单事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Settings:
                return true;
            case R.id.RemoveAd:
                return true;
            case R.id.AboutDoodles:
                return true;
            case R.id.Share:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.toggleDrawer();
            return;
        }
        if (section == 0) {
            if (DoubleClickBackExit.onBackPressed()) {
                finish();
            } else {
                Toast.show("双击返回键退出");
            }
        } else {
            super.onBackPressed();
        }
    }

}


//
//    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
//        private Fragment mFragment;
//        private final Activity mActivity;
//        private final String mTag;
//        private final Class<T> mClass;
//
//        /** Constructor used each time a new tab is created.
//         * @param activity  The host Activity, used to instantiate the fragment
//         * @param tag  The identifier tag for the fragment
//         * @param clz  The fragment's Class, used to instantiate the fragment
//         */
//        public TabListener(Activity activity, String tag, Class<T> clz) {
//            mActivity = activity;
//            mTag = tag;
//            mClass = clz;
//        }
//
//        @Override
//        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//            // Check if the fragment is already initialized
//            if (mFragment == null) {
//                // If not, instantiate and add it to the activity
//                mFragment = Fragment.instantiate(mActivity, mClass.getName());
//                ft.add(android.R.id.content, mFragment, mTag);
//            } else {
//                // If it exists, simply attach it in order to show it
//                ft.attach(mFragment);
//            }
//
//        }
//
//        @Override
//        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//            if (mFragment != null) {
//                // Detach the fragment, because another one is being attached
//                ft.detach(mFragment);
//            }
//        }
//
//        @Override
//        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//        }
//    }


//
//    private void addTab(){
//        ActionBar actionBar = getActionBar();
//        ActionBar.Tab tab = actionBar.newTab()
//                .setText("Tab")
//                .setTabListener(new TabListener<NavigationDrawerFragment>(
//                        this, "Tab", NavigationDrawerFragment.class));
//        actionBar.addTab(tab);
//
//        tab = actionBar.newTab()
//                .setText("album")
//                .setTabListener(new TabListener<NavigationDrawerFragment>(
//                        this, "album", NavigationDrawerFragment.class));
//        actionBar.addTab(tab);
//    }