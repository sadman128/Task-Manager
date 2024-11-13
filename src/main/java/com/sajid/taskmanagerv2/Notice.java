package com.sajid.taskmanagerv2;

public class Notice {
    String author;
    String date;
    String detail;
    String title;

    public Notice(String author,String title, String detail, String date) {
        this.author = author;
        this.date = date;
        this.detail = detail;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
