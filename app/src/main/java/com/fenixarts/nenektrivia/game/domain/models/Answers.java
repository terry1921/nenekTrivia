package com.fenixarts.nenektrivia.game.domain.models;

/**
 * NenekTrivia
 * Created by terry0022 on 21/01/18 - 00:14.
 */

public class Answers {

    private String id;
    private String answer;
    private boolean isCorrect;

    public Answers(String id, String answer, boolean isCorrect) {
        this.id = id;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
