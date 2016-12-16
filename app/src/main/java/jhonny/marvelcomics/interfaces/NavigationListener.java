package jhonny.marvelcomics.interfaces;

import android.view.Menu;

public interface NavigationListener {

    void setTitle(int id);
    void updateMenu(int id, MenuListener listener);
    Menu getMenuObject();
}
