package com.fenixarts.nenektrivia.promotions.domain.models;

/**
 * NenekTrivia
 * Created by terry0022 on 19/02/18 - 17:40.
 */

public class PromotionRow {

    private String title;
    private String image;
    private String content;

    public PromotionRow() {
    }

    public PromotionRow(String title, String image, String content) {
        this.title = title;
        this.image = image;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
