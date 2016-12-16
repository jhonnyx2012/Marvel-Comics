package jhonny.marvelcomics.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ViewUtils {
    public static Drawable getTintedDrawable(Context context, int imageId, int colorId) {
        Drawable inputDrawable=context.getResources().getDrawable(imageId);
        int color=context.getResources().getColor(colorId);
        Drawable wrapDrawable = DrawableCompat.wrap(inputDrawable);
        DrawableCompat.setTint(wrapDrawable, color);
        DrawableCompat.setTintMode(wrapDrawable, PorterDuff.Mode.SRC_IN);
        return wrapDrawable;    }

    public static void showToast(int stringId, Context context) {
        if(context==null)return;
        Toast.makeText(context,stringId,Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String  string, Context context) {
        if(context==null)return;
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
    }

    public static RequestListener<? super String, GlideDrawable> getGlideListener(final View progressBar) {
        return new RequestListener<String, GlideDrawable>() {

            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        };
    }

    public static void fillOrHideTextView(TextView tv, String value) {
        if(showOrHideView(tv,value))
            tv.setText(value);
    }

    public static boolean showOrHideView(View view, String s) {
        if(s==null)
            view.setVisibility(View.GONE);
        else
            view.setVisibility(View.VISIBLE);
        return s!=null;
    }
}