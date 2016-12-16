package jhonny.marvelcomics.models;

public class Date {

    private static final String ON_SALE_DATE = "onsaledate";
    private String type; //onsaledate para fecha de publicacion
    private String date;
    public String getDate() {
        return date;
    }
    public String getType() {
        return type;
    }
}
