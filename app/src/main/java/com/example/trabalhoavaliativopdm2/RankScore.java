package com.example.trabalhoavaliativopdm2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RankScore {
    private String userNikeName;
    private int score;
    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

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

    public String getDate() {
        return date;
    }

    public RankScore setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "RankScore{" +
                "userNikeName='" + userNikeName + '\'' +
                ", score=" + score +
                '}';
    }
}
