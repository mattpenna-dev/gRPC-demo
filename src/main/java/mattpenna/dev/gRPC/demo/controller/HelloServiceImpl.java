package mattpenna.dev.gRPC.demo.controller;

import io.grpc.stub.StreamObserver;
import mattpenna.dev.gRPC.demo.proto.HelloRequest;
import mattpenna.dev.gRPC.demo.proto.HelloResponse;
import mattpenna.dev.gRPC.demo.proto.HelloServiceGrpc;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GRpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        LOGGER.info("server received {}", request);
        String greeting = new StringBuilder()
                .append("Hello, ")
                .append(request.getFirstName())
                .append(" ")
                .append(request.getLastName())
                .toString();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        LOGGER.info("server replied {}", response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
