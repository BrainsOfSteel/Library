package com.library.Library.request;

public class BookInventoryRequest {
    private String bookType;
    private String bookTitle;
    private String barcode;

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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "BookInventoryRequest{" +
                "bookType='" + bookType + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
