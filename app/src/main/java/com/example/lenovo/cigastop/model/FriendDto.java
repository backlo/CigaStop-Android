package com.example.lenovo.cigastop.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by trycatch on 2017. 9. 23..
 */

public class FriendDto {
    @SerializedName("picture")
    private Picture picture;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;

    public Picture getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public class Picture{
        @SerializedName("data")
        private Data data;

        public Data getData() {
            return data;
        }
    }

    public class Data{
        @SerializedName("is_silhouette")
        private boolean is_silhouette;
        @SerializedName("url")
        private String url;

        public boolean is_silhouette() {
            return is_silhouette;
        }

        public String getUrl() {
            return url;
        }
    }
}
