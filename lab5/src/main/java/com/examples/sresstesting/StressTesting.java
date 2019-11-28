package com.examples.sresstesting;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpMethods;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class StressTesting {

    private static ActorRef controlActor;
    private static final String LOCALHOST = "localhost";
    private static final String SERVER_INFO = "Server online at http://localhost:8080/\nPress RETURN to stop...";
    private static final String PACKAGE_ID = "packageId";
    private static final String POST_MESSAGE = "Message was posted";
    private static final int SERVER_PORT = 8080;
    private static final int TIMEOUT_MILLIS = 5000;

    public static void main(String[] args) throws IOException {

        //Инициализация http сервера в akka
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");

        controlActor = system.actorOf(Props.create(CacheActor.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        //HttpRequest (этот запрос пришел снаружи) преобразуется в HttpResponse
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = Flow.of(HttpRequest.class).map(
                req -> {
                    if (req.method() == HttpMethods.GET) {

//                        С помощью Patterns.ask посылаем запрос в кеширующий актор — есть ли результат. Обрабатываем ответ с помощью метода thenCompose
//                        если результат уже посчитан, то возвращаем его как completedFuture
//                        если нет, то создаем на лету flow из данных запроса, выполняем его и возвращаем СompletionStage<Long> :
//                        Source.from(Collections.singletonList(r))
//                                .toMat(testSink, Keep.right()).run(materializer);
                        Patterns.ask(
                                controlActor, new FindingResult(), data.second())),
                        Duration.ofMillis(TIME_MILLIS)
                        )
                        Patterns.ask(
                                controlActor, new TestingResult(), data.second())),
                        Duration.ofMillis(TIME_MILLIS)
                        )
                    }
                }

        <вызов метода которому передаем Http, ActorSystem и ActorMaterializer>;
        )

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(LOCALHOST, SERVER_PORT),
                materializer
        );
        System.out.println(SERVER_INFO);
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate()); // and shutdown when done
    }
}
