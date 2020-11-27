package com.fenixarts.nenektrivia.game.domain.models;

/**
 * NenekTrivia
 * Created by terry0022 on 17/01/18 - 18:22.
 */

@SuppressWarnings("unused")
public class Questions {

    /**
     * id : -L0MzzB1iq0ZmkSHS3xl
     * category : Geografia
     * answerbad01 : Tamazunchale
     * answerbad02 : Valles
     * answerbad03 : Tamasopo
     * answergood : Aquismon
     * question : ¿Dónde se ubica Tamul?
     */

    private String id;
    private String category;
    private String answerbad01;
    private String answerbad02;
    private String answerbad03;
    private String answergood;
    private String question;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnswerbad01() {
        return answerbad01;
    }

    public void setAnswerbad01(String answerbad01) {
        this.answerbad01 = answerbad01;
    }

    public String getAnswerbad02() {
        return answerbad02;
    }

    public void setAnswerbad02(String answerbad02) {
        this.answerbad02 = answerbad02;
    }

    public String getAnswerbad03() {
        return answerbad03;
    }

    public void setAnswerbad03(String answerbad03) {
        this.answerbad03 = answerbad03;
    }

    public String getAnswergood() {
        return answergood;
    }

    public void setAnswergood(String answergood) {
        this.answergood = answergood;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
