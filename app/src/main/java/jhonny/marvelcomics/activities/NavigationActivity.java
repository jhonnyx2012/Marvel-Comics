package jhonny.marvelcomics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.fragments.NavigationFragment;
import jhonny.marvelcomics.interfaces.ActivityListener;

public class NavigationActivity extends AppCompatActivity implements ActivityListener {

    private static final String TAG = "NavigationActivity";
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        fragmentManager = getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        replaceFragment(new NavigationFragment(),NavigationFragment.TAG);
    }

    @Override
    public void addFragment(Fragment fragment, String tag) {
        fragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    public void replaceFragment(Fragment fragment, String tag) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
    }

    @Override
    public void goLoginActivity() {
        startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
        finish();
    }
}