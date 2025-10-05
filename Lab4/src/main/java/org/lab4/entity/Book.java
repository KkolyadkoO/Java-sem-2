package org.lab4.entity;

public class Book extends Entity {
    private String title;
    private int authorId;
    private int publisherId;
    private int publishYear;

    public Book() {}

    public Book(int id, String title, int authorId, int publisherId, int publishYear) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.publishYear = publishYear;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public int getPublishYear() {
        return publishYear;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", publisherId=" + publisherId +
                ", publishYear=" + publishYear +
                '}';
    }
}
