package models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
public class Comment {
    @Id
    private Long id;


    private String author;

    @CreatedTimestamp
    private Timestamp postedAt;

    @Lob
    private final String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private final Post post;

    public Comment(String author, String content, Post post) {
        this.author = author;
        this.content = content;
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Timestamp postedAt) {
        this.postedAt = postedAt;
    }

    public Post getPost() {
        return post;
    }

}
