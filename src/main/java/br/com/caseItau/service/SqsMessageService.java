package br.com.caseItau.service;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class SqsMessageService {


	@Autowired
	private SqsTemplate sqsTemplate;

	public void sendMessage(String queueName, String message){

		try {

			sqsTemplate.send(queueName, MessageBuilder.withPayload(message).build());
			log.info("Mandando mensagem para fila: "+ queueName );

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}


}
