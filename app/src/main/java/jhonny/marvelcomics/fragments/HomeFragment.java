package jhonny.marvelcomics.fragments;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.View;
import java.util.ArrayList;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.interfaces.NavigationListener;
import jhonny.marvelcomics.interfaces.RequestListener;
import jhonny.marvelcomics.managers.ComicManager;
import jhonny.marvelcomics.models.Comic;
import jhonny.marvelcomics.models.ComicDataWrapper;
import jhonny.marvelcomics.utils.ViewUtils;

public class HomeFragment extends ComicListFragment implements RequestListener<ComicDataWrapper>{

    public static HomeFragment newInstance(NavigationListener listener) {
        HomeFragment fragment = new HomeFragment();
        fragment.setNavListerner(listener);
        return fragment;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_home;
    }

    @Override
    protected int getTitle() {
        return R.string.comics;
    }

    @Override
    protected void refreshList(String search) {
        if(swipeRefreshLayout!=null&&!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
        ComicManager.getRemoteRandomComics(this,search);
    }

    @Override
    public void onSuccess(ComicDataWrapper response) {
        ArrayList<Comic> results=response.getData().getResults();
        adapter.setList(results);
        if(swipeRefreshLayout!=null&&swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        list.setVisibility(results.isEmpty()? View.GONE:View.VISIBLE);
        tvMessage.setText(getMessageEmpty());
    }

    @Override
    protected int getMessageEmpty() {
        return R.string.message_no_results;
    }

    @Override
    protected int  getMessageProblem() {
        return R.string.message_problem;
    }

    @Override
    public void onFailure(Throwable error) {
        if(swipeRefreshLayout!=null&&swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        ViewUtils.showToast(R.string.problem_loading_comics,activity);
        list.setVisibility(View.GONE);
        tvMessage.setText(getMessageProblem());
    }

    @Override
    public void onShow() {
        super.onShow();
        adapter.notifyDataSetChanged();
        initSearchView();
    }

    private void initSearchView() {
        SearchView searchView = (SearchView) MenuItemCompat.getActionView( navListerner.getMenuObject().findItem(R.id.search));
        searchView.setQueryHint(getString(R.string.search_by_name));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.trim().isEmpty())
                {
                    refreshList(query.trim());
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {return false;}
        });
    }
}
