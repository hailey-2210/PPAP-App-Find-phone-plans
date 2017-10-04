package application.ppap_findphoneplans.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Plan {
    private String id;
    private String planName;
    private String price;
    private String data;
    private String ntnCall;
    private String intCall;
    private String url;
    private String providerImgUrl;

    // private int rating;

    public Plan() {

    }

    public Plan(String id, String planName, String price, String url, String providerImgUrl, String data, String ntnCall, String intCall) {
        this.id = id;
        this.planName = planName;
        this.price = price;
        this.url = url;
        this.providerImgUrl = providerImgUrl;
        this.data = data;
        this.ntnCall = ntnCall;
        this.intCall = intCall;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProviderImgUrl() {
        return providerImgUrl;
    }

    public void setProviderImgUrl(String providerImgUrl) {
        this.providerImgUrl = providerImgUrl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNtnCall() {
        return ntnCall;
    }

    public void setNtnCall(String ntnCall) {
        this.ntnCall = ntnCall;
    }

    public String getIntCall() {
        return intCall;
    }

    public void setIntCall(String intCall) {
        this.intCall = intCall;
    }
}


