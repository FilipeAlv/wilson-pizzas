package br.com.wilsonpizzas.service;

import br.com.wilsonpizzas.models.ClienteRet;
import br.com.wilsonpizzas.models.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClienteService {
        @GET("app/cadastro_cliente.php")
        Call<String> insertUser(
                @Query("rua") String rua,
                @Query("numero") String numero,
                @Query("bairro") String bairro,
                @Query("cidade") String cidade,
                @Query("cep") String cep,
                @Query("ponto_referencia") String pnt_ref,
                @Query("nome") String nome,
                @Query("cpf") String cpf,
                @Query("rg") String rg,
                @Query("data_nascimento") String dt_nasc,
                @Query("login") String login,
                @Query("senha") String senha,
                @Query("telefone") String telefone
        );

        @GET("app/listar_endereco_telefone.php")
        Call<Endereco> buscarEndereco(@Query("telefone") String login );

        @GET("app/listar_cliente_telefone.php")
        Call<ClienteRet> buscarCliente(@Query("telefone") String telefone );

        @GET("app/listar_cliente_telefone_senha.php")
        Call<ClienteRet> validarCliente(@Query("telefone") String login, @Query("senha") String senha);

        @GET("app/listar_cliente_cpf.php")
        Call<ClienteRet> buscarClientePorCpf(@Query("cpf") String cpf);

        @GET("app/validar_esqueceu_senha.php")
        Call<ClienteRet> validarEsqueceuSenha(
                @Query("cpf") String cpf,
                @Query("dataNascimento") String dtNescimento,
                @Query("telefone") String telefone);

        @GET("app/atualizar_senha_cliente.php")
        Call<ClienteRet> atualizarSenha(@Query("senha") String senha, @Query("cpf") String cpf);
        
}