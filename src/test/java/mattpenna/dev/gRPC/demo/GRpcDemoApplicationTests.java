package mattpenna.dev.gRPC.demo;

import io.grpc.stub.StreamObserver;
import mattpenna.dev.gRPC.demo.proto.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest()
@TestPropertySource(locations = "classpath:test-application.properties")
class GRpcDemoApplicationTests {

	@GrpcClient("inProcess")
	private HelloServiceGrpc.HelloServiceBlockingStub helloServiceClient;

	@GrpcClient("inProcess")
	private ChatServiceGrpc.ChatServiceStub chatClient1;

	@GrpcClient("inProcess")
	private ChatServiceGrpc.ChatServiceStub chatClient2;

	@Test
	void contextLoads() {
	}

	@Test
	public void testHelloService() {
		HelloRequest request = HelloRequest.newBuilder()
				.setFirstName("MyName")
				.setLastName("MyLastName")
				.build();

		HelloResponse response = helloServiceClient.hello(request);
		Assert.notNull(response, "Validate response isn't null");
	}

	@Test
	public void testChat()
	{
		List messages = new ArrayList<ChatMessageFromServer>();

		StreamObserver chatClient1Observer = generateObserver(messages);
		StreamObserver chatClient2Observer = generateObserver(messages);

		chatClient1.chat(chatClient1Observer);
		chatClient2.chat(chatClient2Observer);

		chatClient1Observer.onNext(generateChatMessage("Hello Chat Client2", "ChatClient1"));
		chatClient2Observer.onNext(generateChatMessage("Hello Chat Client1", "ChatClient2"));

		Assert.notEmpty(messages, "Validate Messages are populated");
	}

	private StreamObserver<ChatMessage> generateObserver(List messages)
	{
		return new StreamObserver<>() {
			@Override
			public void onNext(ChatMessage chatMessage) {
				messages.add(chatMessage);
			}

			@Override
			public void onError(Throwable throwable) {

			}

			@Override
			public void onCompleted() {

			}
		};
	}

	private ChatMessage generateChatMessage(String message, String from)
	{
		return ChatMessage.newBuilder()
				.setMessage(message)
				.setFrom(from)
				.build();
	}

}
