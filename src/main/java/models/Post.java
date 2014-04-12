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

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Timestamp postedAt) {
		this.postedAt = postedAt;
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
				.fetch("author")
				.where()
				.lt("postedAt", this.postedAt)
				.orderBy("postedAt desc")
				.setMaxRows(1)
				.findUnique();
	}

	public Post next() {
		return Ebean.find(Post.class)
				.fetch("author")
				.where()
				.gt("postedAt", this.postedAt)
				.orderBy("postedAt asc")
				.setMaxRows(1)
				.findUnique();
	}

	public static List<Post> findRecent(int maxrows) {
		return Ebean.find(Post.class)
				.fetch("author")
				.orderBy("postedAt desc")
				.setMaxRows(maxrows)
				.findList();
	}

	public static Post findById(long id) {
		return Ebean.find(Post.class)
				.fetch("author")
				.where()
				.eq("id", id)
				.findUnique();
	}

	public String getContent() {
		return content;
	}
}
