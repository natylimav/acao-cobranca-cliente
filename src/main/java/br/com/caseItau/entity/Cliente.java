package br.com.caseItau.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

//   @JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente {
    @Getter
    public int conta;
    @Getter
    public int agencia;
    @Getter
    public String nome;
    @Getter
    public String email;
    @Getter
    public String telefone;
    @Getter
    public BigDecimal saldoDevedor;
    @Setter
    public String mensagem;
    @Getter
    public int diasAtraso;
    @Getter
    public Endereco endereco;
    @Getter
    public StatusPagamento statusPagamento;
    @Getter
    public EnvioDeNotificacao envioDeNotificacao;

    public Cliente(int conta, int agencia, String nome, String email, String telefone, BigDecimal saldoDevedor, String mensagem, int diasAtraso, Endereco endereco, StatusPagamento statusPagamento, EnvioDeNotificacao envioDeNotificacao) {
        this.conta = conta;
        this.agencia = agencia;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.saldoDevedor = saldoDevedor;
        this.mensagem = mensagem;
        this.diasAtraso = diasAtraso;
        this.endereco = endereco;
        this.statusPagamento = statusPagamento;
        this.envioDeNotificacao = envioDeNotificacao;
    }
}
