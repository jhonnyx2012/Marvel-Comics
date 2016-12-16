package jhonny.marvelcomics.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.fragments.BaseFragment;
import jhonny.marvelcomics.fragments.FavoritesFragment;
import jhonny.marvelcomics.fragments.HomeFragment;
import jhonny.marvelcomics.fragments.ProfileFragment;
import jhonny.marvelcomics.interfaces.NavigationListener;
import jhonny.marvelcomics.widgets.LockableViewPager;

public class PagerNavigationAdapter extends FragmentPagerAdapter {

    private static final int PROFILE = 0;
    private static final int HOME = 1;
    private static final int FAVORITES = 2;
    public static final int COUNT = 3;
    private NavigationListener listener;
    private ProfileFragment profileFragment;
    private HomeFragment homeFragment;
    private FavoritesFragment favoritesFragment;

    public PagerNavigationAdapter(FragmentManager childFragmentManager,NavigationListener listener)
    {
        super(childFragmentManager);
        this.listener=listener;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:return getFragment(PROFILE);
            case 2:return getFragment(FAVORITES);
            default:return getFragment(HOME);
        }
    }

    public void setSelected(LockableViewPager pager, int itemId) {
        switch (itemId) {
            case R.id.profile:
                pager.setCurrentItem(0,true);getFragment(PROFILE).onShow();
                break;
            case R.id.home:
                pager.setCurrentItem(1,true);getFragment(HOME).onShow();
                break;
            case R.id.favorites:
                pager.setCurrentItem(2,true);getFragment(FAVORITES).onShow();
                break;
        }
    }

    private BaseFragment getFragment(int fragment) {
        switch (fragment)
        {
            case PROFILE:
                profileFragment=profileFragment==null?ProfileFragment.newInstance(listener):profileFragment;
                return profileFragment;
            case FAVORITES:
                favoritesFragment=favoritesFragment==null?FavoritesFragment.newInstance(listener):favoritesFragment;
                return favoritesFragment;
            default:
                homeFragment=homeFragment==null?HomeFragment.newInstance(listener):homeFragment;
                return homeFragment;
        }
    }
}