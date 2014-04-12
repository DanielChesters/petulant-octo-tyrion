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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import ninja.NinjaTest;

import org.junit.Test;

import com.google.common.collect.Maps;

public class ApiControllerTest extends NinjaTest {

    String URL_INDEX = "/";
    String URL_HELLO_WORLD_JSON = "/hello_world.json";

    @Test
    public void testGetIndex() {
        String result = ninjaTestBrowser.makeRequest(getServerAddress() + "/");

        assertTrue(result.contains("yabe."));
        assertTrue(result.contains("Yet another blog"));

    }

    @Test
    public void testGetShow() {
        String result = ninjaTestBrowser.makeRequest(getServerAddress() + "/show/1");

        assertTrue(result.contains("Scala Programming Language"));
        assertTrue(result.contains("I knew that ... "));
    }

    @Test
    public void testPostCommentOk() {

        Map<String, String> formParameters = Maps.newHashMap();
        String author = "TestGuest";
        String content = "It's a content";
        formParameters.put("author", author);
        formParameters.put("content", content);

        String result = ninjaTestBrowser.makePostRequestWithFormParameters(getServerAddress() + "/posts/1/comments", null , formParameters );

        assertTrue(result.contains(author));
        assertTrue(result.contains(content));
        assertTrue(result.contains("Thanks for posting"));
        assertFalse(result.contains("All fields are required!"));
    }

    @Test
    public void testPostCommentFail() {

        Map<String, String> formParameters = Maps.newHashMap();
        String author = "TestGuest";
        String content = "";
        formParameters.put("author", author);
        formParameters.put("content", content);

        String result = ninjaTestBrowser.makePostRequestWithFormParameters(getServerAddress() + "/posts/1/comments", null , formParameters );

        assertFalse(result.contains("Thanks for posting"));
        assertTrue(result.contains("All fields are required!"));
    }

}
