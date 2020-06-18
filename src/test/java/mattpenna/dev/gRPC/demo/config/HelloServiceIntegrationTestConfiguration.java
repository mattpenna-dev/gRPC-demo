package mattpenna.dev.gRPC.demo.config;

import mattpenna.dev.gRPC.demo.controller.HelloServiceImpl;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@ImportAutoConfiguration({
})
public class HelloServiceIntegrationTestConfiguration {

//    @Bean
//    HelloServiceImpl helloServiceImpl() {
//        return new HelloServiceImpl();
//    }
}
