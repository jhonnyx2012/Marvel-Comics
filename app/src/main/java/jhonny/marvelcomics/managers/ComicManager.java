package jhonny.marvelcomics.managers;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.Date;
import java.util.List;
import jhonny.marvelcomics.interfaces.ComicService;
import jhonny.marvelcomics.interfaces.RequestListener;
import jhonny.marvelcomics.models.Comic;
import jhonny.marvelcomics.models.ComicDataWrapper;
import jhonny.marvelcomics.models.Comic_Table;
import jhonny.marvelcomics.utils.Constants;
import jhonny.marvelcomics.utils.RequestCallback;
import jhonny.marvelcomics.utils.Utils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicManager {
    public static void getRemoteRandomComics(RequestListener<ComicDataWrapper> listener, String search) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ComicService service = retrofit.create(ComicService.class);
        long timeStamp=new Date().getTime();
        String filter=search.equals("")?null:search;
        int offset=search.trim().isEmpty()?Utils.getNewRandom():0;
        service.listComics(Utils.getHash(timeStamp),timeStamp,offset,filter).enqueue(new RequestCallback<>(listener));
    }

    public static void addOrRemove(Comic comic) {
        if(exist(comic))
            delete(comic);
        else
            insert(comic);
    }

    public static boolean isFavorite(Comic comic) {
        return exist(comic);
    }

    public static List<Comic> getAll() {
        return SQLite.select()
                .from(Comic.class)
                .queryList();
    }

    private static void insert(Comic comic) {
        comic.getArraySeries();
        comic.getWebUrl();
        comic.getArrayCharacters();
        comic.getArrayCreators();
        comic.insert();
    }

    private static void delete(Comic comic) {
        SQLite.delete(Comic.class).where(Comic_Table.id.eq(comic.getId())).execute();
    }

    private static boolean exist(Comic comic) {
        return !SQLite.select()
                .from(Comic.class)
                .where(Comic_Table.id.eq(comic.getId()))
                .queryList().isEmpty();
    }

    public static Comic get(int id) {
        return SQLite.select()
                .from(Comic.class)
                .where(Comic_Table.id.eq(id))
                .querySingle();
    }
}