package models;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

import ninja.NinjaTest;

@RunWith(MockitoJUnitRunner.class)
public class CommentTest extends NinjaTest  {

    @Test
    public void testComment() {
        Post post = Post.findById(1);
        List<Comment> comments = post.getComments();
        assertNotNull(comments);
        assertEquals(2, comments.size());
        Comment comment = comments.get(0);
        assertNotNull(comment);
        assertNotNull(comment.getAuthor());
        assertNotNull(comment.getContent());
        assertNotNull(comment.getId());
        assertEquals(post, comment.getPost());
        assertNotNull(comment.getPostedAt());
    }
}
