package br.com.caseItau.service;

import br.com.caseItau.core.notifications.NotificarCliente;
import br.com.caseItau.core.notifications.SendNotificationDataBase;
import br.com.caseItau.entity.Cliente;
import br.com.caseItau.entity.EmailClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@Service
public class AtivaAcaoCobrancaService implements NotificarCliente, SendNotificationDataBase {
    private final ObjectMapper objectMapper;

    @Value("${emailQueue}")
    private String emailNotificationQueue;
    @Value("${smsQueue}")
    private String smsNotificationQueue;
    @Value("${callQueue}")
    private String cartaNotificationQueue;
    @Value("${cartaQueue}")
    private String callNotificationQueue;
    @Value("${dataBaseQueue}")
    private String dataBaseQueue;
    @Value("${email.body}")
    private String emailBody;
    @Value("${email.subject}")
    private String emailSubject;
    Integer qtDiasAtrasado;
    boolean enviado;


    @Autowired
    private SqsMessageService sqsMessageService;

    @Autowired
    public AtivaAcaoCobrancaService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /*Faz a tratativa do tipo de notificacao a ser enviada ao cliente de acordo com a quantidade de
     dias em atraso e manda a mensagem para fila */
    public void validaTipoNotificacao(String dadosCliente) {

        try {
            Cliente cliente = objectMapper.readValue(dadosCliente, Cliente.class);

            this.tipoNotificacaoEmail(cliente);

            this.tipoNotificacaoSms(cliente);

            this.tipoNotificacaoCarta(cliente);

            this.tipoNotificacaoCall(cliente);

            this.sendDataNotificationClient(cliente);

            log.info("validaTipoNotificacao: " + dadosCliente);

        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void tipoNotificacaoEmail(Cliente dadosCliente) {
        /*Acao chamar Api responsavel por envio de email*/
        Integer qtDiasAtrasoEmail = 10;

        EmailClient emailClient = new EmailClient();

        this.qtDiasAtrasado = dadosCliente.getDiasAtraso();
        this.enviado = dadosCliente.getEnvioDeNotificacao().getViaEmail();

        log.info("validaTipoNotificacao: dias atraso " + this.qtDiasAtrasado);

        if (this.qtDiasAtrasado.equals(qtDiasAtrasoEmail) && !this.enviado) {

            emailClient.setBody(StringUtils.replace(this.emailBody, "xxx", String.valueOf(dadosCliente.getSaldoDevedor())));
            emailClient.setTo(dadosCliente.getEmail());
            emailClient.setSubject(emailSubject);
            String SQS = "https://localhost.localstack.cloud:4566/000000000000/emailQueueNotification";

            try {
                // Serializando o objeto Cliente em uma string JSON
                String json = objectMapper.writeValueAsString(emailClient);

                log.info("json:  " + json);

                sqsMessageService.sendMessage(SQS, json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            dadosCliente.getEnvioDeNotificacao().setViaEmail(true);
            log.info(StringUtils.replace("Envio de email para cliente, email: $ ", "$", dadosCliente.getEmail()));

        }
    }

    @Override
    public void tipoNotificacaoSms(Cliente dadosCliente) {
        /*Acao chamar Api responsavel por envio de SMS*/
        //sqsMessageService.sendMessage(smsNotificationQueue, dadosCliente.toString());

        log.info("Envio de SMS cliente: " + dadosCliente.getNome() + " telefone" + dadosCliente.telefone);
    }

    @Override
    public void tipoNotificacaoCarta(Cliente dadosCliente) {
        /*Acao chamar Api responsavel por envio de Carta*/
        // sqsMessageService.sendMessage(cartaNotificationQueue, dadosCliente.toString());

        log.info("Envio de Carta: " + dadosCliente.getNome());

    }

    @Override
    public void tipoNotificacaoCall(Cliente dadosCliente) {
        /*Acao chamar Api responsavel por envio de Call*/
        // sqsMessageService.sendMessage(callNotificationQueue, dadosCliente.toString());

        log.info("Envio de Call: " + dadosCliente.getNome());
    }

    @Override
    public void sendDataNotificationClient(Cliente dadosCliente) {
        /*Acao chamar Api responsavel por inserir os dados das notificacoes ao cliente no banco de dados*/
        sqsMessageService.sendMessage(dataBaseQueue, dadosCliente.toString());
        log.info("Envio dados cliente para database, cliente: " + dadosCliente.getNome());
    }
}
