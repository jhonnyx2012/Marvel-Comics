package jhonny.marvelcomics.interfaces;

import jhonny.marvelcomics.models.ComicDataWrapper;
import jhonny.marvelcomics.utils.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ComicService {
    @GET("/v1/public/comics?format=comic&limit=30&formatType=comic&apikey="+Constants.PUBLIC_KEY)
    Call<ComicDataWrapper> listComics(@Query("hash") String hash,
                                      @Query("ts") long ts,
                                      @Query("offset") int offset,
                                      @Query("titleStartsWith") String titleStartsWith);
}