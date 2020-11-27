package com.fenixarts.nenektrivia.bestplayers.domain.model;

/**
 * NenekTrivia
 * Created by terry0022 on 12/01/18 - 13:31.
 */

public class BestPlayersItem {

    /**
     * id : qwertyu1
     * image : www.thisisexample.com/thephoto.png
     * username : terry
     * points : 10
     */

    private String id;
    private String image;
    private String username;
    private int points;

    public BestPlayersItem() {
    }

    public BestPlayersItem(String id, String image, String username, int points) {
        this.id = id;
        this.image = image;
        this.username = username;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
