package com.example.application.infrastructure.message

import com.example.application.domain.message.MessageManager
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

@SpringBootTest(
        properties = [
                'spring.cloud.config.enabled=false',
                'eureka.client.enabled=false',
                'secondService.address=http://localhost:8082/message/'
        ])
class RestMessageReceiverTest extends Specification {

    @Rule
    WireMockRule wireMockRule = new WireMockRule(8082)

    @Autowired
    RestMessageReceiver restMessageReceiver

    def "Should receive message basing on the response content"() {
        given:
        wireMockRule.stubFor(get(urlEqualTo("/message/1"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody('{"id":1, "message": "HELLO"}')
                )
        )
        when:
        def collect = restMessageReceiver.sendAndCollect(1)
        then:
        collect.getId()==1 && collect.getMessage()=="HELLO"
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate()
        }
    }
}
