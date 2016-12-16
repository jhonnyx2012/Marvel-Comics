package jhonny.marvelcomics.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.interfaces.NavigationListener;
import jhonny.marvelcomics.managers.FacebookManager;
import jhonny.marvelcomics.managers.PreferencesManager;
import jhonny.marvelcomics.models.UserLogged;
import jhonny.marvelcomics.utils.Utils;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileFragment extends BaseFragment {

    private NavigationListener navListener;
    TextView tvName,tvEmail, tvGender;
    ImageView ivBackground,ivProfile;
    Button bLogout,bOpenProfile;

    public static ProfileFragment newInstance(NavigationListener listener) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setNavListerner(listener);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected int getTitle() {
        return R.string.profile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        navListener.updateMenu(R.menu.menu_void,null);
        assert view != null;
        tvName= (TextView) view.findViewById(R.id.tvName);
        tvEmail= (TextView) view.findViewById(R.id.tvEmail);
        tvGender= (TextView) view.findViewById(R.id.tvGender);
        ivBackground= (ImageView) view.findViewById(R.id.ivBackground);
        ivProfile= (ImageView) view.findViewById(R.id.ivProfile);
        bLogout= (Button) view.findViewById(R.id.bLogout);
        bOpenProfile= (Button) view.findViewById(R.id.bOpenFacebookProfile);
        setData();
        return view;
    }

    private void setData() {
        final UserLogged userLogged= PreferencesManager.getLoggedUser(activity);
        tvEmail.setText(userLogged.getEmail());
        tvGender.setText(userLogged.getGender());
        tvName.setText(userLogged.getName());
        Glide.with(this).load(userLogged.getProfileImageUrl())
                .bitmapTransform(new BlurTransformation(activity))
                .into(ivBackground);
        Glide.with(this).load(userLogged.getProfileImageUrl())
                .bitmapTransform(new CropCircleTransformation(activity))
                .into(ivProfile);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FacebookManager.logout(activity);
                activityListener.goLoginActivity();
            }
        });
        bOpenProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openURL(userLogged.getLink(),activity);
            }
        });
    }

    private void setNavListerner(NavigationListener listener) {
        this.navListener=listener;
    }

    @Override
    protected boolean haveToolbar() {
        return false;
    }

    @Override
    public void onShow() {
        navListener.updateMenu(getMenu(),null);
        navListener.setTitle(getTitle());
    }
}
