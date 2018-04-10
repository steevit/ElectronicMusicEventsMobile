package pl.steevit.electronicmusiceventspl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by steev on 24.11.2017.
 */

public class Event
{
    private int id;
    private String name;
    private String location;
    private String date;
    private String date2;
    private String musicType;
    private String description;
    private String www;
    private double lat;
    private double lng;
    private String image;

    public Event(int id, String name, String location, String date, String date2, String musicType, String description, String www, double lat, double lng, String image) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.date2 = date2;
        this.musicType = musicType;
        this.description = description;
        this.www = www;
        this.lat = lat;
        this.lng = lng;
        this.image = image;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() { return date; }

    public String getDate2() { return date2; }

    public String getMusicType() {
        return musicType;
    }

    public String getDescription() { return description; }

    public String getWww() {
        return www;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getImage() { return image; }

}
