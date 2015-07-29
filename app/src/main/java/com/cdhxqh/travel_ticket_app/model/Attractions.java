package com.cdhxqh.travel_ticket_app.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 景点门票表
 */
public class Attractions extends Zw_Model implements Parcelable {
    private static final String TAG = "Attractions";
    private static final long serialVersionUID = 2015050105L;


    /**
     * 自增Id
     */
    public int article_id;

    /**
     * 景点标题
     */
    public String title;
    /**
     * 景点描述
     */
    public String description;
    /**
     * 景点描述内容
     */
    public String content;

    /**
     * 经度
     */
    public String longitude;
    /**纬度**/
    public String latitude;

    /**音频路径**/
    public String file_url;

    /**图片路径**/
    public String image;





    public void parse(JSONObject jsonObject) throws JSONException {
        Log.i(TAG, "jsonObject=" + jsonObject.toString());
        article_id = jsonObject.getInt("article_id");
        title = jsonObject.getString("title");
        description = jsonObject.getString("description");
        content = jsonObject.getString("content");
        longitude = jsonObject.getString("longitude");
        latitude = jsonObject.getString("latitude");
        file_url = jsonObject.getString("file_url");
        image = jsonObject.getString("image");

    }

    public Attractions() {
    }

    private Attractions(Parcel in) {
        article_id = in.readInt();
        title = in.readString();
        description = in.readString();
        content = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        file_url = in.readString();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(article_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(file_url);
        dest.writeString(image);
    }

    public static final Creator<Attractions> CREATOR = new Creator<Attractions>() {
        @Override
        public Attractions createFromParcel(Parcel source) {
            return new Attractions(source);
        }

        @Override
        public Attractions[] newArray(int size) {
            return new Attractions[size];
        }
    };

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
