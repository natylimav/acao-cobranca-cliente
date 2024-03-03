package br.com.caseItau.core.notifications;

import br.com.caseItau.entity.Cliente;

public interface NotificarCliente {
    void tipoNotificacaoEmail(Cliente dadosCliente);
    void tipoNotificacaoSms(Cliente dadosCliente);
    void tipoNotificacaoCarta(Cliente dadosCliente);
    void tipoNotificacaoCall(Cliente dadosCliente);
}
