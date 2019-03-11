package gateway;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author Ryan Baxter
 */
// tag::code[]
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {"httpbin=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
public class ApplicationTest {

	@Autowired
	private WebTestClient webClient;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * 测试发送消息
	 */
	@Test
	public void sendMq(){
		for (int i = 0; i < 10; i++) {
			rabbitTemplate.convertAndSend("queue1", "hello");
//			rabbitTemplate.convertAndSend("topicExchange","topic", "hello");
		}
	}

	@Test
	public void contextLoads() throws Exception {
		//Stubs
		stubFor(get(urlEqualTo("/get"))
				.willReturn(aResponse()
					.withBody("{\"headers\":{\"Hello\":\"World\"}}")
					.withHeader("Content-Type", "application/json")));
		stubFor(get(urlEqualTo("/delay/3"))
			.willReturn(aResponse()
				.withBody("no fallback")
				.withFixedDelay(3000)));

		webClient
			.get().uri("/get")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.headers.Hello").isEqualTo("World");

		webClient
			.get().uri("/delay/3")
			.header("Host", "www.hystrix.com")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.consumeWith(
				response -> assertThat(response.getResponseBody()).isEqualTo("fallback".getBytes()));
	}
}
// end::code[]