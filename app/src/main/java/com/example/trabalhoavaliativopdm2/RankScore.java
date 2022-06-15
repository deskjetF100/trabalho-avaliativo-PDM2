package com.example.trabalhoavaliativopdm2;

public class RankScore {
    private String userNikeName;
    private int score;

    public RankScore(String userNikeName, int score) {
        this.userNikeName = userNikeName;
        this.score = score;
    }

    public RankScore() {
    }

    public String getUserNikeName() {
        return userNikeName;
    }

    public void setUserNikeName(String userNikeName) {
        this.userNikeName = userNikeName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "RankScore{" +
                "userNikeName='" + userNikeName + '\'' +
                ", score=" + score +
                '}';
    }
}
