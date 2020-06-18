package mattpenna.dev.gRPC.demo.controller;

import io.grpc.stub.StreamObserver;
import mattpenna.dev.gRPC.demo.proto.ChatMessage;
import mattpenna.dev.gRPC.demo.proto.ChatMessageFromServer;
import mattpenna.dev.gRPC.demo.proto.ChatServiceGrpc;

import java.util.LinkedHashSet;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    private static LinkedHashSet<StreamObserver<ChatMessageFromServer>> chatClients = new LinkedHashSet<>();

    @Override
    public StreamObserver<ChatMessage> chat(StreamObserver<ChatMessageFromServer> responseObserver) {
        chatClients.add(responseObserver);
        
        return new StreamObserver<>() {
            @Override
            public void onNext(ChatMessage chatMessage) {
                for (StreamObserver<ChatMessageFromServer> chatClient : chatClients) {
                    chatClient.onNext(ChatMessageFromServer.newBuilder().setMessage(chatMessage).build());
                }

            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                chatClients.remove(responseObserver);
            }
        };
    }
}
