package simulations;

import config.Config;
import scenarios.MovieScenarios;
import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.global;

public class BasicSimulation extends Simulation {
    {
        setUp(
                MovieScenarios.getMovies
                        .injectOpen(atOnceUsers(1)),

                MovieScenarios.getMovie
                        .injectOpen(atOnceUsers(1)),

                MovieScenarios.getStudios
                        .injectOpen(atOnceUsers(1))
        )
                .protocols(MovieScenarios.HTTP_PROTOCOL)
                .assertions(
                        global().responseTime().max().lt((int) Config.MAX_RESPONSE_TIME_MS),
                        global().successfulRequests().percent().gt(100 - Config.MAX_ERROR_RATE_PERCENT)
                );
    }
}