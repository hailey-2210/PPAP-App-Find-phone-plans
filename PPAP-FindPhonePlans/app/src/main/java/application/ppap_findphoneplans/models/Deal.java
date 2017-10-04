package application.ppap_findphoneplans.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Deal {
    String name;
    String provider;
    String imgUrl;
    String url;

    public Deal() {

    }

    public Deal(String name, String provider, String imgUrl, String url) {
        this.name = name;
        this.provider = provider;
        this.imgUrl = imgUrl;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
