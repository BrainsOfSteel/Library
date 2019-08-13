package com.library.Library.request;

public class BookAndAuthorRequest {
    private String bookTitle;
    private String authorName;
    private String bookType;

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "BookAndAuthorRequest{" +
                "bookTitle='" + bookTitle + '\'' +
                ", authorName='" + authorName + '\'' +
                ", bookType='" + bookType + '\'' +
                '}';
    }
}
