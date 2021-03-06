package conf;


import java.io.IOException;

import models.Post;
import models.User;
import ninja.lifecycle.Start;
import ninja.utils.Crypto;

import com.avaje.ebean.Ebean;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Bootstrap {

    private static final String DEFAULT_PASSWORD = "secret";

    @Inject
    private Crypto cryto;

    @Start(order = 90)
    public void startService() throws IOException {
        User user1 = new User("tarou@test.com", cryto.signHmacSha1(DEFAULT_PASSWORD), "Tarou");
        User user2 = new User("suzuki@test.com", cryto.signHmacSha1(DEFAULT_PASSWORD), "Suzuki");

        Ebean.save(user1);
        Ebean.save(user2);

        Post post1 = new Post(user1, "Scala Programming Language",
        "Scala is a multi-paradigm programming language designed to\n"
        + "integrate features of object-oriented programming and functional programming.\n\n"
        + "The name Scala is a portmanteau of \"scalable\" and \"language\",\n"
        + "signifying that it is designed to grow with the demands of its users.");

        Post post2 = new Post(user1, "test data of YABE", "test data !!");
        Post post3 = new Post(user2, "Ninja Framework!!!", "Cool!!!!!");

        Ebean.save(post1);
        Ebean.save(post2);
        Ebean.save(post3);

        post1.addComment("Guest", "I knew that ...");
        post1.addComment("Igamon", "ninnin");
        post3.addComment("Hanzo", "You are right!");


    }



}
