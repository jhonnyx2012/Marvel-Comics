package jhonny.marvelcomics.interfaces;

import android.view.Menu;
import android.view.MenuItem;

public interface MenuListener {
    boolean onOptionsItemSelected(MenuItem item);
    void onCreateOptionsMenu(Menu menu);
}
