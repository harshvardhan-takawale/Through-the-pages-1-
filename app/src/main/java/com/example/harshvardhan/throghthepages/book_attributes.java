package com.example.harshvardhan.throghthepages;

/**
 * Created by lenovo on 6/6/2017.
 */

public class book_attributes {

    private String booktitle;
    private String author;
    private int no_pages;

    public book_attributes(String booktitle, String author, int no_pages) {
        this.booktitle = booktitle;
        this.author = author;
        this.no_pages = no_pages;
    }

    public int getNo_pages() {
        return no_pages;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public String getAuthor() {
        return author;
    }


}
