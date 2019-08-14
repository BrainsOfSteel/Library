package com.library.Library.com.library.Library.dto;

public class BooksDetailsDTO {
    private Integer totalBooks;
    private Integer availableBooks;
    private String bookType;
    private String bookTitle;

    public BooksDetailsDTO(Integer totalBooks, Integer availableBooks, String bookType, String bookTitle) {
        this.totalBooks = totalBooks;
        this.availableBooks = availableBooks;
        this.bookType = bookType;
        this.bookTitle = bookTitle;
    }

    public BooksDetailsDTO() {
    }

    public Integer getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(Integer totalBooks) {
        this.totalBooks = totalBooks;
    }

    public Integer getAvailableBooks() {
        return availableBooks;
    }

    public void setAvailableBooks(Integer availableBooks) {
        this.availableBooks = availableBooks;
    }

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
}
