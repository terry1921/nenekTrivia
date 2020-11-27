package com.fenixarts.nenektrivia.game.domain.models;

/**
 * NenekTrivia
 * Created by terry0022 on 30/01/18 - 17:27.
 */

@SuppressWarnings("unused")
public class Points {

    private String username;
    private String photo;
    private int point;

    public Points() {}

    public Points(String username, String photo, int point) {
        this.username = username;
        this.photo = photo;
        this.point = point;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
