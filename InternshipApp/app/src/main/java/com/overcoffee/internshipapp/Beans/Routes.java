package com.overcoffee.internshipapp.Beans;

/**
 * Created by AbdelRahmanTarek on 8/3/17.
 */

public class Routes {
    public String by_user;
    public String description;
    public double distance;
    public double fare;
    public String from;
    public String to;
    public int time;
    public String title;
    public String objectId;

    public Routes(String by_user, String description, double distance, double fare, String from, String to, int time, String title, String objectId) {
        this.by_user = by_user;
        this.description = description;
        this.distance = distance;
        this.fare = fare;
        this.from = from;
        this.to = to;
        this.time = time;
        this.title = title;
        this.objectId = objectId;
    }
}
