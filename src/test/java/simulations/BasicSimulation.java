package simulations;

import config.Config;
import scenarios.PostScenarios;
import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.global;

public class BasicSimulation extends Simulation {
    {
        setUp(
                PostScenarios.getAllPosts
                        .injectOpen(atOnceUsers(1)),

                PostScenarios.getSinglePost
                        .injectOpen(atOnceUsers(1)),

                PostScenarios.createPost
                        .injectOpen(atOnceUsers(1)),

                PostScenarios.updatePost
                        .injectOpen(atOnceUsers(1)),

                PostScenarios.deletePost
                        .injectOpen(atOnceUsers(1))
        )
                .protocols(PostScenarios.HTTP_PROTOCOL)
                .assertions(
                        global().responseTime().max().lt((int) Config.MAX_RESPONSE_TIME_MS),
                        global().successfulRequests().percent().gt(100 - Config.MAX_ERROR_RATE_PERCENT)
                );
    }
}
