package com.examples.sresstesting;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class CacheActor {
    public class StorageResultsActor extends AbstractActor {
        private HashMap<Integer, ArrayList<StoreMessage>> store = new HashMap<>();

        @Override
        public Receive createReceive() {
            return ReceiveBuilder.create()
                    .match(MessageProcessingActor.class, req ->
                            getSender().tell(
                                    store.get(req.getPackageId()).toArray(),
                                    ActorRef.noSender()
                            )
                    )
                    .match(StoreCommand.class, msg -> {
                                if (store.containsKey(msg.getPackageId())) {
                                    ArrayList<StoreMessage> tests = store.get(msg.getPackageId());
                                    tests.add(msg.getStorageMessage());
                                    store.put(msg.getPackageId(), tests);
                                } else {
                                    ArrayList<StoreMessage> tests = new ArrayList<>();
                                    tests.add(msg.getStorageMessage());
                                    store.put(msg.getPackageId(), tests);
                                }
                            }
                    ).build();
        }
}
