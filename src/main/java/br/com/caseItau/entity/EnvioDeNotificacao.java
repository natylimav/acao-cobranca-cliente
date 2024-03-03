package br.com.caseItau.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
public class EnvioDeNotificacao {
    @Getter
    public Boolean viaSms;
    @Getter
    public Boolean viaEmail;
    @Getter
    public Boolean viaLigacao;
    @Getter
    public Boolean viaCorrespondencia;

}
