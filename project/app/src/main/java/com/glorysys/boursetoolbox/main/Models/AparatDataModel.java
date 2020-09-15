package com.glorysys.boursetoolbox.main.Models;

public class AparatDataModel {
    private String title;
    private int visit_cnt;
    private String poster;
    private int duration;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVisit_cnt() {
        return visit_cnt;
    }

    public void setVisit_cnt(int visit_cnt) {
        this.visit_cnt = visit_cnt;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

}
