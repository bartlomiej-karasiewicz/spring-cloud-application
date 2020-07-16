package com.example.application.infrastructure.message

import com.example.application.domain.message.MessageManager
import com.example.application.domain.model.message.Message
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

@SpringBootTest(
        properties = [
                'spring.cloud.config.enabled=false',
                'eureka.client.enabled=false',
        ])
class RestMessageReceiverTest extends Specification {

    @Rule
    WireMockRule wireMockRule = new WireMockRule(8082)

    RestTemplate restTemplate = new RestTemplate()

    MessageManager manager = new RestMessageReceiver(restTemplate)

    def "Should receive message basing on the response content"() {
        given:
        wireMockRule.stubFor(get(urlEqualTo("/message/1"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody('{"id":1, "message": "HELLO"}')
                )
        )
        when:
        def collect = manager.sendAndCollect(1)
        then:
        println collect
    }
}
