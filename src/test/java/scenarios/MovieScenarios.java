package scenarios;

import config.Config;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class MovieScenarios {

    private static final String LOGIN_BODY = String.format(
            "{\"username\": \"%s\", \"password\": \"%s\"}",
            Config.AUTH_USERNAME, Config.AUTH_PASSWORD
    );

    public static final HttpProtocolBuilder HTTP_PROTOCOL = http
            .baseUrl(Config.BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .userAgentHeader("Gatling Performance Tests");

    static final ChainBuilder login = exec(
            http("POST /auth/login")
                    .post("/api/v1/auth/login")
                    .body(StringBody(LOGIN_BODY))
                    .check(status().is(200))
                    .check(jsonPath("$.token").saveAs("jwtToken"))
    );

    public static final ScenarioBuilder getMovies = scenario("Get Movies")
            .exec(login)
            .exec(
                    http("GET /movies")
                            .get("/api/v1/movies")
                            .header("Authorization", "Bearer #{jwtToken}")
                            .check(status().is(200))
                            .check(jsonPath("$.content[0].mid").exists())
            );

    public static final ScenarioBuilder getMovie = scenario("Get Movie by ID")
            .exec(login)
            .exec(
                    http("GET /movie/1001")
                            .get("/api/v1/movie/1001")
                            .header("Authorization", "Bearer #{jwtToken}")
                            .check(status().is(200))
                            .check(jsonPath("$.mid").is("1001"))
            );

    public static final ScenarioBuilder getStudios = scenario("Get Studios")
            .exec(login)
            .exec(
                    http("GET /studios")
                            .get("/api/v1/studios")
                            .header("Authorization", "Bearer #{jwtToken}")
                            .check(status().is(200))
            );

    public static final ScenarioBuilder browseMoviesFlow = scenario("Browse Movies Flow")
            .exec(login)
            .exec(
                    http("GET /movies")
                            .get("/api/v1/movies")
                            .header("Authorization", "Bearer #{jwtToken}")
                            .check(status().is(200))
            )
            .pause(1)
            .exec(
                    http("GET /movie/1001")
                            .get("/api/v1/movie/1001")
                            .header("Authorization", "Bearer #{jwtToken}")
                            .check(status().is(200))
            )
            .pause(1)
            .exec(
                    http("GET /movies?genre=Action")
                            .get("/api/v1/movies?genre=Action")
                            .header("Authorization", "Bearer #{jwtToken}")
                            .check(status().is(200))
            );
}
