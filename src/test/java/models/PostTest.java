package models;

import java.util.List;

import ninja.NinjaTest;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PostTest extends NinjaTest {


    @Test
    public void testPostFindById() {
        Post post = Post.findById(1);
        assertNotNull(post);
        assertEquals(Long.valueOf(1), post.getId());
        assertNotNull(post.getPostedAt());
        assertNotNull(post.getContent());
        assertNotNull(post.getTitle());
        assertNotNull(post.getAuthor());
    }

    @Test
    public void testPostFindRecent() {
        List<Post> posts = Post.findRecent(11);
        assertNotNull(posts);
        assertFalse(posts.isEmpty());
        assertEquals(3, posts.size());
    }

//    @Test
//    public void testPrevious() {
//        Post post = Post.findById(2);
//        Post previous = post.previous();
//        assertNotNull(previous);
//        assertEquals(Long.valueOf(1), previous.getId());
//    }
//
//    @Test
//    public void testNext() {
//        Post post = Post.findById(2);
//        Post next = post.next();
//        assertNotNull(next);
//        assertEquals(Long.valueOf(3), next.getId());
//    }

    @Test
    public void testAddComment() {
        Post post = Post.findById(1);
        int nbComment = post.getComments().size();
        Post postUpdated = post.addComment("test", "test");
        assertNotNull(postUpdated);
        assertEquals(nbComment + 1, postUpdated.getComments().size());
    }

    @Test
    public void testFindByIdNull() {
        Post post = Post.findById(0);
        assertNull(post);
    }
}
