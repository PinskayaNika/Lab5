package com.examples.sresstesting;

//Общая логика требуемого flow
//HttpRequest (этот запрос пришел снаружи) преобразуется в HttpResponse
// Flow.of(HttpRequest.class)
//→ map в Pair<url сайта из query параметра, Integer количество запросов>
//→ mapAsync,
//С помощью Patterns.ask посылаем запрос в кеширующий актор — есть ли результат. Обрабатываем ответ с помощью метода thenCompose
//если результат уже посчитан, то возвращаем его как completedFuture
//если нет, то создаем на лету flow из данных запроса, выполняем его и возвращаем СompletionStage<Long> :
//Source.from(Collections.singletonList(r))
//.toMat(testSink, Keep.right()).run(materializer);
//
//→ map в HttpResponse с результатом а также посылка результата в кеширующий актор.


import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import scala.Int;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CacheActor extends AbstractActor {
        private HashMap<Integer, Map<Integer, Integer>> data = new HashMap<>();

        @Override
        public Receive createReceive() {
            return ReceiveBuilder.create()
                    .match(FindingResult.class, msg ->
                            getSender().tell(
                                    data.get(msg.getPackageId()).toArray(),
                                    ActorRef.noSender()
                            )
                    )
                    .match(TestingResult.class, msg -> {
                        Map<Integer, Integer> temp;
                                if (data.containsKey(msg.getURL())) {
                                    temp = data.get(msg.getURL());
                                } else {
                                    temp = new HashMap<>();
                                }
                                temp.put(msg.getCount(), msg.getTime());
                                data.put(msg.getURL(), temp);

                            }
                    ).build();
        }
}
