package vo;

/**
 * Created by maqiyue on 2018/5/20
 */
public class Participle {
    private int id;
    private String title;
    private String article;
    private String url;
    private String time;

    public Participle(int id, String title, String article, String url, String time) {
        this.id = id;
        this.title = title;
        this.article = article;
        this.url = url;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Participle(int id, String title, String article, String url) {
        this.id = id;
        this.title = title;
        this.article = article;
        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
