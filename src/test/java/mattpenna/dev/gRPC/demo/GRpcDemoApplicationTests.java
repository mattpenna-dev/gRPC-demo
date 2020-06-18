package mattpenna.dev.gRPC.demo;

import mattpenna.dev.gRPC.demo.proto.HelloRequest;
import mattpenna.dev.gRPC.demo.proto.HelloResponse;
import mattpenna.dev.gRPC.demo.proto.HelloServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;

@SpringBootTest()
@TestPropertySource(locations = "classpath:test-application.properties")
class GRpcDemoApplicationTests {

	@GrpcClient("inProcess")
	private HelloServiceGrpc.HelloServiceBlockingStub helloServiceClient;

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

}
