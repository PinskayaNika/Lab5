package com.examples.sresstesting;

import akka.NotUsed;
//import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.*;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.*;
import akka.japi.Pair;
import akka.util.ByteString;
//import akka.util.Collections;
//import javafx.util.Pair;
//import org.omg.CORBA.TIMEOUT;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.asynchttpclient.Dsl.asyncHttpClient;


public class StressTesting {

    //private static ActorRef controlActor;
    private static final String LOCALHOST = "localhost";
    private static final String SERVER_INFO = "Server online at http://localhost:8080/\nPress RETURN to stop...";
    private static final int SERVER_PORT = 8080; //(localhost)
    private static final int TIMEOUT_MILLIS = 5000;
    private static final String HOME_DIR = "/";
    private static final String EMPTY_STRING = "";
    private static final String COUNT = "count";
    private static final String TEST_URL = "testURL";
    private static final String URL_ERROR = "URL PARAMETER IS EMPTY";
    private static final String FINAL_ANSWER = "Medium response is in MS ->";
    private static final String NUMBER_ERROR = "NUMBER EXCEPTION";
    private static final String COUNT_ERROR = "COUNT PARAMETER IS EMPTY";
    private static final String GET_ERROR = "ONLY GET METHOD";
    private static final String PATH_ERROR = "BAD PATH";
    private static final int ZERO = 0;



//    //private static ActorRef controlActor;
//    //private static final Logger logger = LoggerFactory.getLogger(StressTesting.class);
//    private static final String ROUTES = "routes";
//    private static final String WELCOME_MSG = "start!";
//    private static final String HOME_DIR = "/";
//    private static final String TEST_URL = "testUrl";
//    private static final String COUNT = "count";
//    private static final String EMPTY_STRING = "";
//    private static final String URL_ERROR = "URL PARAMETER IS EMPTY";
//    private static final String COUNT_ERROR = "COUNT PARAMETER IS EMPTY";
//    private static final String FINAL_ANSWER = "Medium response is in MS ->";
//    private static final String NUMBER_ERROR = "NUMBER EXCEPTION";
//    private static final String PATH_ERROR = "BAD PATH";
//    private static final String GET_ERROR = "ONLY GET METHOD!";
//    private static final String SERVER_WELCOME_MSG = "Server online at http://localhost:8080/\nPress RETURN to stop...";
//    private static final String LOCALHOST = "localhost";
//    private static final int LOCALHOST_PORT = 8080;
//    private static final long TIME_MILLIS = 5000;
//    private static final int ZERO = 0;
//    private static final int NO_SUCH_DATA = -1;
//    private static final int PARALLELISM = 1;

//    public static void main(String[] args) throws IOException {
//
//        //Инициализация http сервера в akka
//        System.out.println("start!");
//        ActorSystem system = ActorSystem.create("routes");
//
//        //controlActor = system.actorOf(Props.create(CacheActor.class));
//
//        final Http http = Http.get(system);
//        final ActorMaterializer materializer = ActorMaterializer.create(system);

//        //HttpRequest (этот запрос пришел снаружи) преобразуется в HttpResponse
//        //<вызов метода которому передаем Http, ActorSystem и ActorMaterializer>
//        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = Flow.of(HttpRequest.class)
//                .map(
//                        req -> {
//                            if (req.method() == HttpMethods.GET) {
//                                if (req.getUri().path().equals(HOME_DIR)) {
//                                    String url = req.getUri().query().get(TEST_URL).orElse(EMPTY_STRING);
//                                    String count = req.getUri().query().get(COUNT).orElse(EMPTY_STRING);
//
//                                    if (url.isEmpty()) {
//                                        return HttpResponse.create().withEntity(ByteString.fromString(URL_ERROR));
//                                    }
//
//                                    if (count.isEmpty()) {
//                                        return HttpResponse.create().withEntity(ByteString.fromString(COUNT_ERROR));
//                                    }
//
//                                    try {
//                                        Integer countInteger = Integer.parseInt(count);
//                                        Pair<String, Integer> data = new Pair<>(url, countInteger);
//                                        Source<Pair<String, Integer>, NotUsed> source = Source.from(Collections.singletonList(data));
//
//                                        //создание FloW
//                                        Flow<Pair<String, Integer>, HttpResponse, NotUsed> testSink = Flow.<Pair<String, Integer>>create()
//                                                //map в Pair<url сайта из query параметра, Integer количество запросов>
//                                                .map(pair -> new Pair<>(HttpRequest.create().withUri(pair.first()), pair.second()))
//                                                //mapAsync,
//                                                .mapAsync(1, pair -> {
////                        С помощью Patterns.ask посылаем запрос в кеширующий актор — есть ли результат. Обрабатываем ответ с помощью метода thenCompose
////                        если результат уже посчитан, то возвращаем его как completedFuture
////                        если нет, то создаем на лету flow из данных запроса, выполняем его и возвращаем СompletionStage<Long> :
////                        Source.from(Collections.singletonList(r))
////                                .toMat(testSink, Keep.right()).run(materializer);
//                                                    return Patterns.ask(
//                                                            system.actorOf(Props.create(CacheActor.class)),
//                                                            new FindingResult(new javafx.util.Pair<>(data.first(), data.second())),
//                                                            Duration.ofMillis(TIMEOUT_MILLIS)
//                                                    ).thenCompose(r -> {
//                                                        if ((int) r != -1) {
//                                                            return CompletableFuture.completedFuture((int) r);
//                                                        }
//                                                        //fold for counting all time
//                                                        Sink<CompletionStage<Long>, CompletionStage<Integer>> fold = Sink
//                                                                .fold(0, (agg, next) -> {
//                                                                    int testNext = (int) (ZERO + next.toCompletableFuture().get());
//                                                                    return agg + testNext;
//                                                                });
//                                                        return Source.from(Collections.singletonList(pair))
//                                                                .toMat(
//                                                                        Flow.<Pair<HttpRequest, Integer>>create()
//                                                                                .mapConcat(p -> Collections.nCopies(p.second(), p.first()))
//                                                                                .mapAsync(1, req2 -> CompletableFuture.supplyAsync(System::currentTimeMillis
//                                                                                        /*return CompletableFuture.supplyAsync(() ->
//                                                                                        System.currentTimeMillis()*/
//                                                                                ).thenCompose(start -> CompletableFuture.supplyAsync(() -> {
//                                                                                    CompletionStage<Long> whenResponse = asyncHttpClient()
//                                                                                            .prepareGet(req2.getUri().toString())
//                                                                                            .execute()
//                                                                                            .toCompletableFuture()
//                                                                                            .thenCompose(answer ->
//                                                                                                    CompletableFuture
//                                                                                                            .completedFuture(System.currentTimeMillis() - start));
//                                                                                    return whenResponse;
//
//                                                                                    /*return CompletableFuture.supplyAsync(System::currentTimeMillis
//                                                                                            /*return CompletableFuture.supplyAsync(() ->
//                                                                                            System.currentTimeMillis()
//                                                                                    ).thenCompose(start -> CompletableFuture.supplyAsync(() -> {
//                                                                                        CompletionStage<Long> whenResponse = asyncHttpClient()
//                                                                                                .prepareGet(req2.getUri().toString())
//                                                                                                .execute()
//                                                                                                .toCompletableFuture()
//                                                                                                .thenCompose(answer ->
//                                                                                                        CompletableFuture
//                                                                                                                .completedFuture(System.currentTimeMillis() - start));
//                                                                                        return whenResponse;
//
//                                                                                        /*return asyncHttpClient()
//                                                                                                .prepareGet(req2.getUri().toString())
//                                                                                                .execute()
//                                                                                                .toCompletableFuture()
//                                                                                                .thenCompose(answer ->
//                                                                                                        CompletableFuture
//                                                                                                                .completedFuture(System.currentTimeMillis() - start));
//
//                                                                                    }));*/
//
//                                                                                    /*return asyncHttpClient()
//                                                                                            .prepareGet(req2.getUri().toString())
//                                                                                            .execute()
//                                                                                            .toCompletableFuture()
//                                                                                            .thenCompose(answer ->
//                                                                                                    CompletableFuture
//                                                                                                            .completedFuture(System.currentTimeMillis() - start));*/
//
//                                                                                })))
//                                                                                .toMat(fold, Keep.right()), Keep.right()).run(materializer);
//                                                    })
//                                                            .thenCompose(
//                                                                    sum -> {
//                                                                        Patterns.ask(
//                                                                                system.actorOf(Props.create(CacheActor.class)),
//                                                                                new TestingResult(
//                                                                                        new javafx.util.Pair<>(data.first(), new javafx.util.Pair<>(data.second(), sum))),
//                                                                                        TIMEOUT_MILLIS);
//                                                                        Double middleValue = (double) sum / (double) countInteger;
//                                                                        return CompletableFuture
//                                                                                .completedFuture(HttpResponse
//                                                                                        .create().withEntity(ByteString.fromString(FINAL_ANSWER + middleValue.toString())));
//                                                                    }
//                                                            );
//                                                });
//
//
////                        С помощью Patterns.ask посылаем запрос в кеширующий актор — есть ли результат. Обрабатываем ответ с помощью метода thenCompose
////                        если результат уже посчитан, то возвращаем его как completedFuture
////                        если нет, то создаем на лету flow из данных запроса, выполняем его и возвращаем СompletionStage<Long> :
////                        Source.from(Collections.singletonList(r))
////                                .toMat(testSink, Keep.right()).run(materializer);
//
//                                        RunnableGraph<CompletionStage<HttpResponse>> runnableGraph =
//                                                source.via(testSink).toMat(Sink.last(), Keep.right());
//                                        CompletionStage<HttpResponse> result = runnableGraph.run(materializer);
//                                        //CompletionStage<HttpResponse> result = source.via(testSink).toMat(Sink.last(), Keep.right()).run(materializer);
//                                        return result.toCompletableFuture().get();
//                                    } catch (NumberFormatException e) {
//                                        e.printStackTrace();
//                                        return HttpResponse
//                                                .create().withEntity(ByteString.fromString(NUMBER_ERROR));
//                                    }
//                                } else {
//                                    req.discardEntityBytes(materializer);
//                                    return HttpResponse
//                                            .create().withStatus(StatusCodes.NOT_FOUND).withEntity(PATH_ERROR);
//                                }
//                            } else {
//                                req.discardEntityBytes(materializer);
//                                return HttpResponse
//                                        .create().withStatus(StatusCodes.NOT_FOUND).withEntity(GET_ERROR);
//                            }
//                        });
//        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
//                routeFlow,
//                ConnectHttp.toHost(LOCALHOST, SERVER_PORT),
//                materializer
//        );
//        System.out.println(SERVER_INFO);
//        System.in.read();
//        binding
//                .thenCompose(ServerBinding::unbind)
//                .thenAccept(unbound -> system.terminate()); // and shutdown when done
//    }
//}


//import akka.NotUsed;
//import akka.actor.ActorRef;
//import akka.actor.ActorSystem;
//import akka.actor.Props;
//import akka.http.javadsl.ConnectHttp;
//import akka.http.javadsl.Http;
//import akka.http.javadsl.ServerBinding;
//import akka.http.javadsl.model.*;
//import akka.pattern.Patterns;
//import akka.stream.ActorMaterializer;
//import akka.stream.javadsl.Flow;
//import akka.stream.javadsl.Keep;
//import akka.stream.javadsl.Sink;
//import akka.stream.javadsl.Source;
//import akka.japi.Pair;
//import akka.util.ByteString;
//import akka.util.Timeout;
//import org.asynchttpclient.Response;
//import scala.concurrent.Await;
//import scala.concurrent.Future;
//import org.asynchttpclient.*;
//
//import static org.asynchttpclient.Dsl.asyncHttpClient;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.util.Collections;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.CompletionStage;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class StressTesting {
//    //private static ActorRef controlActor;
//    //private static final Logger logger = LoggerFactory.getLogger(StressTesting.class);
//    private static final String ROUTES = "routes";
//    private static final String WELCOME_MSG = "start!";
//    private static final String HOME_DIR = "/";
//    private static final String TEST_URL = "testUrl";
//    private static final String COUNT = "count";
//    private static final String EMPTY_STRING = "";
//    private static final String URL_ERROR = "URL PARAMETER IS EMPTY";
//    private static final String COUNT_ERROR = "COUNT PARAMETER IS EMPTY";
//    private static final String FINAL_ANSWER = "Medium response is in MS ->";
//    private static final String NUMBER_ERROR = "NUMBER EXCEPTION";
//    private static final String PATH_ERROR = "BAD PATH";
//    private static final String GET_ERROR = "ONLY GET METHOD!";
//    private static final String SERVER_WELCOME_MSG = "Server online at http://localhost:8080/\nPress RETURN to stop...";
//    private static final String LOCALHOST = "localhost";
//    private static final int LOCALHOST_PORT = 8080;
//    private static final long TIME_MILLIS = 5000;
//    private static final int ZERO = 0;
//    private static final int NO_SUCH_DATA = -1;
//    private static final int PARALLELISM = 1;
//
    public static void main(String[] args) throws IOException {

        System.out.println(SERVER_INFO);
        ActorSystem system = ActorSystem.create("routes");

        //controlActor = system.actorOf(Props.create(CacheActor.class));
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = Flow.of(HttpRequest.class).map(
                req -> {
                    if (req.method() == HttpMethods.GET) {
                        if (req.getUri().path().equals(HOME_DIR)) {
                            String url = req.getUri().query().get(TEST_URL).orElse(EMPTY_STRING);
                            String count = req.getUri().query().get(COUNT).orElse(EMPTY_STRING);
                            if (url.isEmpty()) {
                                return HttpResponse.create().withEntity(ByteString.fromString(URL_ERROR));
                            }
                            if (url.isEmpty()) {
                                return HttpResponse.create().withEntity(ByteString.fromString(COUNT_ERROR));
                            }
                            try {
                                Integer countInteger = Integer.parseInt(count);
                                Pair<String, Integer> data = new Pair<>(url, countInteger);
                                Source<Pair<String, Integer>, NotUsed> source = Source.from(Collections.singletonList(data));

                                Flow<Pair<String, Integer>, HttpResponse, NotUsed> testSink = Flow.<Pair<String, Integer>>create()
                                        .map(pair -> new Pair<>(HttpRequest.create().withUri(pair.first()), pair.second()))
                                        .mapAsync(1, pair -> {
                                                    return Patterns
                                                            .ask(
                                                                    system.actorOf(Props.create(CacheActor.class)),
                                                                    new FindingResult(new javafx.util.Pair<>(data.first(), data.second())),
                                                                    Duration.ofMillis(TIMEOUT_MILLIS)
                                                            ).thenCompose(r ->
                                                            {
                                                                if ((int) r != -1) {
                                                                    return CompletableFuture.completedFuture((int) r);
                                                                }
                                                                // fold for counting all time
                                                                Sink<CompletionStage<Long>, CompletionStage<Integer>> fold = Sink
                                                                        .fold(ZERO, (ac, el) -> {
                                                                            int testEl = (int) (ZERO + el.toCompletableFuture().get());
                                                                            return ac + testEl;
                                                                        });
                                                                return Source.from(Collections.singletonList(pair))
                                                                        .toMat(
                                                                                Flow.<Pair<HttpRequest, Integer>>create()
                                                                                        .mapConcat(p -> Collections.nCopies(p.second(), p.first()))
                                                                                        .mapAsync(1, req2 -> {
                                                                                            return CompletableFuture.supplyAsync(() ->
                                                                                                    System.currentTimeMillis()
                                                                                            ).thenCompose(start -> CompletableFuture.supplyAsync(() -> {
                                                                                                CompletionStage<Long> whenResponse = asyncHttpClient()
                                                                                                        .prepareGet(req2.getUri().toString())
                                                                                                        .execute()
                                                                                                        .toCompletableFuture()
                                                                                                        .thenCompose(answer ->
                                                                                                                CompletableFuture.completedFuture(System.currentTimeMillis() - start));
                                                                                                return whenResponse;
                                                                                            }));
                                                                                        })
                                                                                        .toMat(fold, Keep.right()), Keep.right()).run(materializer);
                                                            }).thenCompose(
                                                                    sum -> {
                                                                        Patterns.ask(system.actorOf(Props.create(CacheActor.class)), new TestingResult(new javafx.util.Pair<>(data.first(), new javafx.util.Pair<>(data.second(), sum))), TIMEOUT_MILLIS);
                                                                        Double middleValue = (double) sum / (double) countInteger;
                                                                        return CompletableFuture.completedFuture(HttpResponse.create().withEntity(ByteString.fromString(FINAL_ANSWER + middleValue.toString())));
                                                                    }
                                                            );
                                                }
                                        );
                                CompletionStage<HttpResponse> result = source.via(testSink).toMat(Sink.last(), Keep.right()).run(materializer);
                                return result.toCompletableFuture().get();
                            } catch(NumberFormatException e){
                                e.printStackTrace();
                                return HttpResponse.create().withEntity(ByteString.fromString(NUMBER_ERROR));
                            }
                        } else {
                            req.discardEntityBytes(materializer);
                            return HttpResponse.create().withStatus(StatusCodes.NOT_FOUND).withEntity(PATH_ERROR);
                        }
                    } else{
                        req.discardEntityBytes(materializer);
                        return HttpResponse.create().withStatus(StatusCodes.NOT_FOUND).withEntity(GET_ERROR);
                    }
                });
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(LOCALHOST, SERVER_PORT),
                materializer
        );
        System.out.println(SERVER_INFO);
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .

                        thenAccept(unbound ->system.terminate());
    }
}
