package br.com.caseItau.entity;

import lombok.Getter;

public class Endereco {
    @Getter
    public String logradouro;
    @Getter
    public String numero;
    @Getter
    public String cep;
    @Getter
    public String cidade;
    @Getter
    public String estado;
}
