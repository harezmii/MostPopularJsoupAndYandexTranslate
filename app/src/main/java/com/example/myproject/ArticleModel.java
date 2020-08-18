package com.example.myproject;

public class ArticleModel {
    private String photo;
    private String header;
    private String time;
    private String author;
    private  String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArticleModel(String author, String photo, String header, String time, String body) {
        this.photo =  photo;
        this.header = header;
        this.time = time;
        this.author = author;
    }


    public String getPhoto() {  return photo; }

    public void setPhoto(String photo) {  this.photo = photo; }

    public String getTime() {   return time; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }




}
