package jhonny.marvelcomics.interfaces;

public interface RequestListener<T> {
    void onSuccess(T response);
    void onFailure(Throwable error);
}