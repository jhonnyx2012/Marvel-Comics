package jhonny.marvelcomics.fragments;

import android.view.View;
import java.util.ArrayList;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.interfaces.NavigationListener;
import jhonny.marvelcomics.managers.ComicManager;
import jhonny.marvelcomics.models.Comic;

public class FavoritesFragment extends ComicListFragment{

    public static FavoritesFragment newInstance(NavigationListener listener) {
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setNavListerner(listener);
        return fragment;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_fav;
    }

    @Override
    protected int getTitle() {
        return R.string.favorites;
    }

    @Override
    protected int getMessageEmpty() {
        return R.string.message_no_favorites;
    }

    @Override
    protected int  getMessageProblem() {
        return R.string.message_problem;
    }

    @Override
    protected void refreshList(String search) {
        ArrayList<Comic> results=new ArrayList<>(ComicManager.getAll());
        adapter.setList(results);
        if(swipeRefreshLayout!=null&&swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        list.setVisibility(results.isEmpty()? View.GONE:View.VISIBLE);
        tvMessage.setText(getMessageEmpty());
    }

    @Override
    public void onShow() {
        super.onShow();
        refreshList("");
    }
}