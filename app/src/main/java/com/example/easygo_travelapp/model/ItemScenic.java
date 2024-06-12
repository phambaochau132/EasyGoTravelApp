package com.example.easygo_travelapp.model;

import java.io.Serializable;

public class ItemScenic implements Serializable {
    private int idScenic;
    private String imageScenic;
    private String nameScenic;
    private String location;

    public ItemScenic() {
    }

    public ItemScenic(int idScenic, String imageScenic, String nameScenic, String location) {
        this.idScenic = idScenic;
        this.imageScenic = imageScenic;
        this.nameScenic = nameScenic;
        this.location = location;
    }

    public int getIdScenic() {
        return idScenic;
    }

    public void setIdScenic(int idScenic) {
        this.idScenic = idScenic;
    }

    public String getImageScenic() {
        return imageScenic;
    }

    public void setImageScenic(String imageScenic) {
        this.imageScenic = imageScenic;
    }

    public String getNameScenic() {
        return nameScenic;
    }

    public void setNameScenic(String nameScenic) {
        this.nameScenic = nameScenic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ItemScenic{" +
                "idScenic=" + idScenic +
                ", imageScenic='" + imageScenic + '\'' +
                ", nameScenic='" + nameScenic + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
