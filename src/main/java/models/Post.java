package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
public class Post {


    private static final String POSTED_AT_FIELD = "postedAt";

    private static final String POSTED_AT_FIELD_ASC = POSTED_AT_FIELD + " asc";

    private static final String POSTED_AT_FIELD_DESC = POSTED_AT_FIELD + " desc";

    private static final String AUTHOR_FIELD = "author";

    @Id
    private Long id;

    private final String title;

    @CreatedTimestamp
    private Timestamp postedAt;

    @Lob
    private final String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private final User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Post(User author, String title, String content) {
        this.content = content;
        this.title = title;
        this.author = author;
        this.comments = new ArrayList<Comment>();
    }

    public Long getId() {
        return id;
    }

    public Timestamp getPostedAt() {
        return postedAt;
    }

    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Post addComment(String author, String content) {
        Comment newComment = new Comment(author, content, this);
        Ebean.save(newComment);
        comments.add(newComment);
        Ebean.save(this);
        return this;
    }

    public Post previous() {
        return Ebean.find(Post.class)
                .fetch(AUTHOR_FIELD)
                .where()
                .lt(POSTED_AT_FIELD, this.postedAt)
                .orderBy(POSTED_AT_FIELD_DESC)
                .setMaxRows(1)
                .findUnique();
    }

    public Post next() {
        return Ebean.find(Post.class)
                .fetch(AUTHOR_FIELD)
                .where()
                .gt(POSTED_AT_FIELD, this.postedAt)
                .orderBy(POSTED_AT_FIELD_ASC)
                .setMaxRows(1)
                .findUnique();
    }

    public static List<Post> findRecent(int maxrows) {
        return Ebean.find(Post.class)
                .fetch(AUTHOR_FIELD)
                .orderBy(POSTED_AT_FIELD_DESC)
                .setMaxRows(maxrows)
                .findList();
    }

    public static Post findById(long id) {
        return Ebean.find(Post.class)
                .fetch(AUTHOR_FIELD)
                .where()
                .eq("id", id)
                .findUnique();
    }

    public String getContent() {
        return content;
    }
}
