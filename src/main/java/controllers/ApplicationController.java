/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import java.util.List;
import java.util.Map;

import models.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.session.FlashScope;
import ninja.validation.Length;
import ninja.validation.Required;
import ninja.validation.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;


@Singleton
public class ApplicationController {

	public Logger logger = LoggerFactory.getLogger(ApplicationController.class);


    public Result index() {
    	List<Post> posts = Post.findRecent(11);
    	Post frontPost = posts.isEmpty() ? null : posts.remove(0);
    	Map<String, Object> map = Maps.newHashMap();
    	map.put("frontPost", frontPost);
    	map.put("olderPosts", posts);

        return Results.html().render(map);

    }

    public Result helloWorldJson() {

        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }

    public static class SimplePojo {

        public String content;

    }

    public Result show(@PathParam("id") Long id) {
    	return Results.html().render(getPostMap(id));
    }

    private Map<String, Object> getPostMap(Long id) {
    	Map<String, Object> map = Maps.newHashMap();
    	Post post = Post.findById(id);
    	map.put("post", post);

    	map.put("previous", post.previous());
    	map.put("next", post.next());

		return map;
	}

	public Result postComment(@PathParam("postId") Long postId,
			@Param("author") @Required @Length(min = 1) String author,
			@Param("content") @Required @Length(min = 1) String content,
			Validation validation,
			FlashScope flashScope) {

		logger.info("post comment : " + content);

		Map<String, Object> map = getPostMap(postId);

		if (validation.hasViolations()) {
			flashScope.error("All fields are required!");
			map.put("author", author);
			map.put("content", content);
		} else {
			Post post = Post.findById(postId);
			post.addComment(author, content);
			flashScope.success(String.format("Thanks for posting %s", author));

		}

    	return Results.html().template("views/ApplicationController/show.ftl.html").render(map);
    }
}
