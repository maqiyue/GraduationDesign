package vo;

/**
 * Created by maqiyue on 2018/5/20
 */
public class NoticeHtml {
    private String url;
    private String html;

    public NoticeHtml(String url, String html) {
        this.url = url;
        this.html = html;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
