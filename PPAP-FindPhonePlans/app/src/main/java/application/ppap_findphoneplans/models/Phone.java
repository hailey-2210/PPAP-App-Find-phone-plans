
package application.ppap_findphoneplans.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Phone {
        private String phoneId;
        private String phoneName;
        private String phoneBrand;
        private String review;
        private String imgUrl;

        public Phone() {
        }

    public Phone(String phoneId, String phoneName, String phoneBrand, String review, String imgUrl) {
        this.phoneId = phoneId;
        this.phoneName = phoneName;
        this.phoneBrand = phoneBrand;
        this.review = review;
        this.imgUrl = imgUrl;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * Created by Mohsin on 10/1/2017.
     */

    public static class Deal {
    }
}

