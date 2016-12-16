package jhonny.marvelcomics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.interfaces.LoginListener;
import jhonny.marvelcomics.managers.FacebookManager;
import jhonny.marvelcomics.managers.PreferencesManager;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private static final String TAG = "LoginActivity";
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(PreferencesManager.isAuth(this))
        {
            goNavigationActivity();
            return;
        }
        LoginButton loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager,FacebookManager.getLoginCallback(this,this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoginCompleted() {
        goNavigationActivity();
    }

    private void goNavigationActivity() {
        startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
        finish();
    }
}