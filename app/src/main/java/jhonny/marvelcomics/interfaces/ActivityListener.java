package jhonny.marvelcomics.interfaces;

import android.support.v4.app.Fragment;

public interface ActivityListener {
    void addFragment(Fragment fragment, String tag);
    void replaceFragment(Fragment fragment, String tag);
    void goLoginActivity();
}