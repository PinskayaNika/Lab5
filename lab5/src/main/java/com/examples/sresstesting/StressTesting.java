package com.examples.sresstesting;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;

import java.io.IOException;

public class StressTesting {
    public static void main(String[] args) throws IOException {

        static ActorRef mainActor;
        private static final String LOCALHOST = "localhost";
        private static final String SERVER_INFO = "Server online at http://localhost:8080/\nPress RETURN to stop...";
        private static final String PACKAGE_ID = "packageId";
        private static final String POST_MESSAGE = "Message was posted";
        private static final int SERVER_PORT = 8080;
        private static final int TIMEOUT_MILLIS = 5000;

        //Инициализация http сервера в akka
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = <вызов метода которому передаем Http, ActorSystem и ActorMaterializer>;

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate()); // and shutdown when done
    }
}
