package com.fenixarts.nenektrivia.profile.domain.models;

/**
 * NenekTrivia
 * Created by terry0022 on 01/02/18 - 13:04.
 */

@SuppressWarnings("unused")
public class ProfileItem {

    private String username;
    private String mail;
    private String place;
    private int points;

    public ProfileItem() {
    }

    public ProfileItem(String username, String mail, String place, int points) {
        this.username = username;
        this.mail = mail;
        this.place = place;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
