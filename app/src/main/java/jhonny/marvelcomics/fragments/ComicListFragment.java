package jhonny.marvelcomics.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.adapters.ComicAdapter;
import jhonny.marvelcomics.interfaces.MenuListener;
import jhonny.marvelcomics.interfaces.NavigationListener;
import jhonny.marvelcomics.managers.PreferencesManager;
import jhonny.marvelcomics.utils.Constants;

public abstract class ComicListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, MenuListener {

    protected NavigationListener navListerner;
    protected RecyclerView list;
    protected ComicAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected TextView tvMessage;

    @Override
    protected int getLayout() {
        return R.layout.fragment_comic_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        assert view!=null;
        list= (RecyclerView) view.findViewById(R.id.list);
        tvMessage = (TextView) view.findViewById(R.id.message);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter=new ComicAdapter(activity, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                activityListener.addFragment(ComicDetailFragment.newInstance(adapter,i),ComicDetailFragment.TAG);
            }
        });
        initList();
        refreshList("");
        return view;
    }

    protected abstract void refreshList(String search);
    protected abstract int getMessageEmpty();
    protected abstract int getMessageProblem();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.switchMode:
                switchListMode();
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu) {}

    private void switchListMode() {
        PreferencesManager.switchMode(activity);
        refreshModeIcon(navListerner.getMenuObject());
        initList();
    }

    private void initList() {
        int mode=PreferencesManager.getMode(activity);
        list.setLayoutManager(mode==Constants.LIST_MODE? new LinearLayoutManager(activity) : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        list.setAdapter(adapter);
    }

    private void refreshModeIcon(Menu menu) {
        int mode=PreferencesManager.getMode(activity);
        MenuItem item=menu!=null?menu.findItem(R.id.switchMode):null;
        if(item!=null)
           item.setIcon(mode== Constants.LIST_MODE?R.drawable.ic_view_module_white_24dp:R.drawable.ic_view_list_white_24dp);
    }

    @Override
    protected boolean haveToolbar() {
        return false;
    }

    public void setNavListerner(NavigationListener navListerner) {
        this.navListerner = navListerner;
    }

    @Override
    public void onRefresh() {
        refreshList("");
    }

    @Override
    public void onShow() {
        navListerner.updateMenu(getMenu(),this);
        navListerner.setTitle(getTitle());
    }
}