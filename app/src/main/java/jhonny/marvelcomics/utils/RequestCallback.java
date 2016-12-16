package jhonny.marvelcomics.utils;

import jhonny.marvelcomics.interfaces.RequestListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallback<T> implements Callback<T> {

    private static final String TAG = "RequestCallback";
    private RequestListener<T> listener;

    public RequestCallback(RequestListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful())
            this.listener.onSuccess(response.body());
        else
            this.listener.onFailure(null);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        this.listener.onFailure(t);
    }
}