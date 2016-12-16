package jhonny.marvelcomics.models;

class Price {
    private static final String PRINT_PRICE = "printPrice";

    private String type; //printPrice para precio de impresion
    private String price;

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

}