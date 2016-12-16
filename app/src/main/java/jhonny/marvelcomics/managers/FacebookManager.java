package jhonny.marvelcomics.managers;

import android.content.Context;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginResult;
import org.json.JSONObject;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.interfaces.LoginListener;
import jhonny.marvelcomics.models.UserLogged;
import jhonny.marvelcomics.utils.ViewUtils;

public class FacebookManager {
    public static FacebookCallback<LoginResult> getLoginCallback(final Context context, final LoginListener listener) {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ViewUtils.showToast(R.string.login_success,context);
                importFbProfile(context,listener);
            }

            @Override
            public void onCancel() {
                ViewUtils.showToast(R.string.login_canceled,context);
            }

            @Override
            public void onError(FacebookException exception) {
                ViewUtils.showToast(R.string.login_error,context);
            }
        };
    }

    private static void importFbProfile(final Context context, final LoginListener listener) {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject me, GraphResponse response) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                if (me != null) {
                                    String profileImageUrl = ImageRequest.getProfilePictureUri(me.optString("id"), 500, 500).toString();
                                    String email = me.optString("email");
                                    String name = me.optString("first_name")+" "+me.optString("last_name");
                                    String gender = me.optString("gender");
                                    Profile profile = Profile.getCurrentProfile();
                                    String link = profile.getLinkUri().toString();
                                    PreferencesManager.doLogin(context,new UserLogged(email,name,gender,link,profileImageUrl));
                                    listener.onLoginCompleted();
                                }
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,email,first_name,last_name,gender");
            request.setParameters(parameters);
            GraphRequest.executeBatchAsync(request);
        }
    }

    public static void logout(Context context) {
        AccessToken.setCurrentAccessToken(null);
        Profile.setCurrentProfile(null);
        PreferencesManager.logout(context);
    }
}