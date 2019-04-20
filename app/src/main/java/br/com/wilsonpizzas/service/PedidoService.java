package br.com.wilsonpizzas.service;

import java.util.List;

import br.com.wilsonpizzas.models.Pedido;
import br.com.wilsonpizzas.models.PedidoRet;

import br.com.wilsonpizzas.models.Produto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PedidoService {
        @GET("app/cadastro_pedido.php")
        Call<PedidoRet> insertPedido(
                @Query("descricao") String descricao,
                @Query("valor") String valor,
                @Query("forma_pagamento") String forma_pagamento,
                @Query("troco") String troco,
                @Query("cliente_id") int cliente_id,
                @Query("endereco_id") int endereco_id

        );

        @GET("app/cadastro_pedido.php")
        Call<PedidoRet> insertPedidoEndereco(
                @Query("descricao") String descricao,
                @Query("valor") String valor,                                                   
                @Query("forma_pagamento") String forma_pagamento,
                @Query("troco") String troco,
                @Query("cliente_id") int cliente_id,
                @Query("rua") String rua,
                @Query("numero") String numero,
                @Query("bairro") String bairro,
                @Query("cidade") String cidade,
                @Query("cep") String cep,
                @Query("ponto_referencia") String pnt_ref


        );

        @GET("app/cadastro_pedido_produto.php")
        Call<PedidoRet> insertPedidoProduto(
                @Query("pedido_id") int pedido_id,
                @Query("produto_id") int produto_id,
                @Query("quantidade") int quantidade,
                @Query("observacao") String observacao


        );

        @GET("app/listar_pedidos.php")
        Call<List<PedidoRet>> listPedidos(@Query("cliente") int cliente);

        @GET("app/editar_status_pedido.php")
        Call<PedidoRet> editStatusPedido(
                @Query("id") int id,
                @Query("status") String status);

}