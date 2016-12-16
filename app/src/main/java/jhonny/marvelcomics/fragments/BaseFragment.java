package jhonny.marvelcomics.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.interfaces.ActivityListener;
import jhonny.marvelcomics.interfaces.MenuListener;
import jhonny.marvelcomics.interfaces.NavigationListener;

public abstract class BaseFragment extends Fragment implements NavigationListener {

    public static final String TAG = "MT-BaseFragment";
    protected AppCompatActivity activity;
    private TextView title;
    protected Toolbar toolbar;
    private Menu menu;
    private MenuListener menuListener;
    protected ActivityListener activityListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayout(), container, false);
        if(haveToolbar())
        {
            toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            title= (TextView) toolbar.findViewById(R.id.title);
            toolbar.setTitle("");
            setTitle(getTitle());
            activity.setSupportActionBar(toolbar);
        }
        return view;
    }

    @Override
    public Menu getMenuObject() {
        return menu;
    }

    @Override
    public void setTitle(int id) {
        title.setText(id);
    }

    @Override
    public void updateMenu(int id, MenuListener listener) {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(id);
        menu=toolbar.getMenu();
        this.menuListener=listener;
    }

    protected abstract boolean haveToolbar();

    protected int getTitle() {
        return R.string.empty_string;
    }

    protected abstract int getLayout();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityListener= (ActivityListener) activity;
        this.activity= (AppCompatActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(haveToolbar())
            setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(getMenu(), menu);
        this.menu=menu;
        if(menuListener!=null)
            menuListener.onCreateOptionsMenu(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(menuListener!=null)
            menuListener.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    protected int getMenu() {
        return R.menu.menu_void;
    }

    @Override
    public void onDetach() {
        activity=null;
        activityListener=null;
        super.onDetach();
    }

    public void onShow(){}
}