package br.com.caseItau.core.notifications;

import br.com.caseItau.entity.Cliente;

public interface SendNotificationDataBase {

    void sendDataNotificationClient(Cliente dadosCliente);

}
