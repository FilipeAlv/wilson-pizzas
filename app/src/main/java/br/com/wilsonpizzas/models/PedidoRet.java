package br.com.wilsonpizzas.models;

import java.util.ArrayList;
import java.util.Date;

public class PedidoRet {
    int id;
    String status;
    String descricao;
    String data;
    String forma_pagamento;
    String troco;
    String valor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public String getForma_pagamento() {
        return forma_pagamento;
    }

    public String getTroco() {
        return troco;
    }

    public String getValor() {
        return valor;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
