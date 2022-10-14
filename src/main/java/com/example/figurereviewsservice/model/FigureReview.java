package com.example.figurereviewsservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "figureReviews")
public class FigureReview {

    @Id
    private String id;
    private String figureName;
    private String user;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date = new Date();
    private String textReview;
    private Integer stars;

    public FigureReview() {
    }

    public FigureReview(String figureName, String textReview, Integer stars, String user) {
        this.figureName = figureName;
        this.textReview = textReview;
        this.stars = stars;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFigureName() {
        return figureName;
    }

    public void setFigureName(String figureName) {
        this.figureName = figureName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}