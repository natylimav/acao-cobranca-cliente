package br.com.caseItau.service.listener;

import br.com.caseItau.service.AtivaAcaoCobrancaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class SqsConsumerTest {

    @Mock
    private AtivaAcaoCobrancaService ativaAcaoCobrancaService;

    @Autowired
    @InjectMocks
    private SqsConsumer sqsConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void listen() throws JsonProcessingException {
        // Dados fictícios para o teste
        String message = "Mensagem de teste";

        // Chamando o método a ser testado
        sqsConsumer.listen(message);

        // Verificando se o método validaTipoNotificacao foi chamado com a mensagem correta
        verify(ativaAcaoCobrancaService, times(1)).validaTipoNotificacao(eq(message));
    }
}