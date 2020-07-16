package com.example.application.infrastructure.message

import com.example.application.domain.message.MessageReceiver
import com.example.application.domain.model.message.Message
import spock.lang.Shared
import spock.lang.Specification

class MessageDatabaseExtractorTest extends Specification {

    MessageRepository messageRepository = Mock(MessageRepository)
    MessageReceiver messageDatabaseExtractor = new MessageDatabaseExtractor(messageRepository)

    @Shared
    Message shareMessage=new Message(1,"HELLO-WORLD")

    def "Verify receiving data correctly"() {
        given:
        def message = shareMessage
        when:
        messageDatabaseExtractor.receiveMessage(1) >> message
        then:
        message.getId() == 1 && message.getMessage() == "HELLO-WORLD"
    }

}
