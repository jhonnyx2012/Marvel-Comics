package jhonny.marvelcomics.managers;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import jhonny.marvelcomics.models.UserLogged;
import jhonny.marvelcomics.utils.Constants;

public class PreferencesManager {
    private static final String MODE = "MODE";
    private static final String USER = "USER";
    private static final String PREFERENCE_NAME = "MPreferences";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static int getMode(Context context) {
        return getPreferences(context).getInt(MODE,Constants.GRID_MODE);
    }

    private static void setMode(Context context, int mode) {
        getEditor(context).putInt(MODE, mode).commit();
    }

    public static void switchMode(Context context) {
        setMode(context,PreferencesManager.getMode(context)==Constants.LIST_MODE?Constants.GRID_MODE:Constants.LIST_MODE);
    }

    public static void doLogin(Context context,UserLogged userLogged) {
        getEditor(context).putString(USER, userLogged.getAsString()).commit();
    }

    public static void logout(Context context) {
        getEditor(context).putString(USER,"").commit();
    }

    public static boolean isAuth(Context context) {
        return !getPreferences(context).getString(USER,"").equals("");
    }

    public static UserLogged getLoggedUser(Context context) {
        return new Gson().fromJson(getPreferences(context).getString(USER,""),UserLogged.class);
    }
}