package jhonny.marvelcomics.models;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import org.joda.time.DateTime;
import java.util.ArrayList;
import jhonny.marvelcomics.MarvelDB;
import jhonny.marvelcomics.R;
import jhonny.marvelcomics.managers.ComicManager;

@Table(database = MarvelDB.class)
public class Comic extends BaseModel {
    @PrimaryKey(autoincrement = true)
    long idTable;

    @Column
    int id;

    @Column
    String title;

    @Column
    String description;

    @Column
    int pageCount;

    @Column
    String thumbnailUrl;

    @Column
    String price;

    @Column
    String date;

    @Column
    String webUrl;

    @Column
    String jsonCharacters;

    @Column
    String jsonCreators;

    @Column
    String jsonSeries;

    private Thumbnail thumbnail;
    private ArrayList<Url> urls;
    private Resource series;
    private ArrayList<Date> dates;
    private ArrayList<Price> prices;
    private Collection creators;
    private Collection characters;

    public Collection getCharacters() {
        return characters;
    }

    public Collection getCreators() {
        return creators;
    }

    public ArrayList<Price> getPrices() {
        return prices;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public Resource getSeries() {
        return series;
    }

    public ArrayList<Url> getUrls() {
        return urls;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isFavorite() {
        return ComicManager.isFavorite(this);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJsonCharacters() {
        return jsonCharacters;
    }

    public void setJsonCharacters(String jsonCharacters) {
        this.jsonCharacters = jsonCharacters;
    }

    public String getJsonCreators() {
        return jsonCreators;
    }

    public void setJsonCreators(String jsonCreators) {
        this.jsonCreators = jsonCreators;
    }

    public String getJsonSeries() {
        return jsonSeries;
    }

    public void setJsonSeries(String jsonSeries) {
        this.jsonSeries = jsonSeries;
    }

    public String getThumbnailUrl() {
        if(thumbnailUrl==null)
            thumbnailUrl=getThumbnail().getUrl();
        return thumbnailUrl;
    }

    public String getDate() {
        if(date==null) {
            String saleDate=(dates == null || dates.size() == 0)?null:dates.get(0).getDate();
            date = saleDate==null?null:new DateTime(saleDate).toString("dd MMM yyyy");
        }
        return date;
    }

    public String getPrice(Context context) {
        if(price==null) {
            String printPrice=(prices == null || prices.size() == 0)?null:prices.get(0).getPrice();
            price = printPrice==null||printPrice.equals("0")?context.getString(R.string.bushed):"$"+printPrice;
        }
        return price;
    }
    public String getWebUrl() {
        if(webUrl==null) {
            webUrl=(urls == null || urls.size() == 0)?null:urls.get(0).getUrl();
        }
        return webUrl;
    }

    public  ArrayList<Resource> getArraySeries() {
        if(jsonSeries==null) {
            jsonSeries = new Gson().toJson(series);
        }
        ArrayList<Resource> series=new ArrayList<>();
        if(jsonSeries!=null)
            series.add(new Gson().fromJson(jsonSeries,Resource.class));
        return series;
    }

    public ArrayList<Resource> getArrayCreators() {
        if(jsonCreators==null) {
            jsonCreators =  new Gson().toJson(creators==null?new ArrayList<>():creators.getItems());
        }
        return new Gson().fromJson(jsonCreators,new TypeToken<ArrayList<Resource>>(){}.getType());
    }

    public ArrayList<Resource> getArrayCharacters() {
        if(jsonCharacters==null) {
            jsonCharacters =new Gson().toJson(characters==null?new ArrayList<>():characters.getItems());
        }
        return new Gson().fromJson(jsonCharacters,new TypeToken<ArrayList<Resource>>(){}.getType());
    }
}