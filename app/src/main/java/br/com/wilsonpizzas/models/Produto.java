package br.com.wilsonpizzas.models;

public class Produto {
    int id;
    String nome, descricao, valor, status, tipo, categoria, imagem, observacao, tamanho ;
    int quantidade;
    boolean isMultiSabores;

    public String getNome(){
        return  nome;
    }
    public String getDescricao(){
        return  descricao;
    }
    public String getValor(){
        return valor;
    }
    public String getStatus(){return status; }
    public String getTipo(){ return tipo; }
    public String getCategoria(){ return categoria; }

    public String getURLImagem() {
        return this.imagem;
    }

    public void setURLImagem(String URLImagem) {
        this.imagem = URLImagem;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isMultiSabores() {
        return isMultiSabores;
    }



    public void setMultiSabores(boolean multiSabores) {
        isMultiSabores = multiSabores;
    }
}



