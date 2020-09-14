package DTO;

public class CategoryNews {
    private String category;
    private String url;
    private String title;
    private String aid;


    public CategoryNews(String category, String title, String url, String aid){
        this.category=category;
        this.title = title;
        this.url = url;
        this.aid = aid;
    }
    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    private String context;
}
