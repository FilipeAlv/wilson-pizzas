package br.com.wilsonpizzas.activity;

public class CardViewProdutos {
     private String nome, descricao, valor;
     private boolean promocao;
     private String URLimagem;






    public String getURLImagem(){
         return this.URLimagem;
     }

     public void setURLimagem(String URLimagem){
         this.URLimagem = URLimagem;
     }



    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getValor() {
        return valor;
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

    public boolean isPromocao() {
        return promocao;
    }

    public void setPromocao(boolean promocao) {
        this.promocao = promocao;
    }
}
