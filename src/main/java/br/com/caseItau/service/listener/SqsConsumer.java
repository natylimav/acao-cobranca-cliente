package br.com.caseItau.service.listener;

import br.com.caseItau.service.AtivaAcaoCobrancaService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SqsConsumer {
    private String mensagem;

    @Autowired
    private AtivaAcaoCobrancaService ativaAcaoCobrancaService;

    @SqsListener("${acoesCobrancaQueue}")
    public void listen(String message) {

        this.mensagem = message;

        try {
            log.info(" mensagem recebida: "+ message);
            this.ativaAcaoCobrancaService.validaTipoNotificacao(this.mensagem);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
