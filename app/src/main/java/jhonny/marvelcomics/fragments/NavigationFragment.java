package jhonny.marvelcomics.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import jhonny.marvelcomics.R;
import jhonny.marvelcomics.adapters.PagerNavigationAdapter;
import jhonny.marvelcomics.widgets.LockableViewPager;

public class NavigationFragment extends BaseFragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "NavigationFragment";
    private LockableViewPager pager;
    private PagerNavigationAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_navigation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=super.onCreateView(inflater, container, savedInstanceState);
        assert view!=null;
        adapter = new PagerNavigationAdapter(getChildFragmentManager(), this);
        pager = (LockableViewPager) view.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(PagerNavigationAdapter.COUNT);
        pager.setSwipeLocked(true);
        pager.setAdapter(adapter);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        return view;
    }

    @Override
    protected boolean haveToolbar() {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        adapter.setSelected(pager,item.getItemId());
        return true;
    }
}