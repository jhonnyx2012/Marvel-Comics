package jhonny.marvelcomics.models;

class Thumbnail {
    private String path;
    private String extension;
    String getUrl() {
        return path+"."+extension;
    }
}