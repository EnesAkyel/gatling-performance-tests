package scenarios;

import config.Config;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PostScenarios {

    public static final HttpProtocolBuilder HTTP_PROTOCOL = http
            .baseUrl(Config.BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .userAgentHeader("Gatling Performance Tests");

    public static final ScenarioBuilder getAllPosts = scenario("Get All Posts")
            .exec(
                    http("GET /posts")
                            .get("/posts")
                            .check(status().is(200))
                            .check(jsonPath("$[0].id").exists())
                            .check(jsonPath("$").ofList().transform(List -> List.size()).is(100))
            );

    public static final ScenarioBuilder getSinglePost = scenario("Get Single Post")
            .exec(
                    http("GET /posts/1")
                            .get("/posts/1")
                            .check(status().is(200))
                            .check(jsonPath("$.id").is("1"))
                            .check(jsonPath("$.title").exists())
                            .check(jsonPath("$.body").exists())
            );

    public static final ScenarioBuilder createPost = scenario("Create Post")
            .exec(
                    http("POST /posts")
                            .post("/posts")
                            .body(StringBody(
                                    """
                                    {
                                        "title": "Performance Test Post",
                                        "body": "This is a load test post body",
                                        "userId": 1
                                    }
                                    """
                            ))
                            .check(status().is(201))
                            .check(jsonPath("$.id").exists())
                            .check(jsonPath("$.title").is("Performance Test Post"))
            );

    public static final ScenarioBuilder updatePost = scenario("Update Post")
            .exec(
                    http("PUT /posts/1")
                            .put("/posts/1")
                            .body(StringBody(
                                    """
                                    {
                                        "id": 1,
                                        "title": "Updated Post Title",
                                        "body": "Updated body content",
                                        "userId": 1
                                    }
                                    """
                            ))
                            .check(status().is(200))
                            .check(jsonPath("$.title").is("Updated Post Title"))
            );

    public static final ScenarioBuilder deletePost = scenario("Delete Post")
            .exec(
                    http("DELETE /posts/1")
                            .delete("/posts/1")
                            .check(status().is(200))
            );

    public static final ScenarioBuilder browsePostsFlow = scenario("Browse Posts Flow")
            .exec(
                    http("GET /posts")
                            .get("/posts")
                            .check(status().is(200))
            )
            .pause(1)
            .exec(
                    http("GET /posts/1")
                            .get("/posts/1")
                            .check(status().is(200))
            )
            .pause(1)
            .exec(
                    http("GET /comments for post 1")
                            .get("/comments?postId=1")
                            .check(status().is(200))
                            .check(jsonPath("$[0].postId").is("1"))
            );
}
