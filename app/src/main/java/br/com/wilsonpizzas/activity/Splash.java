package br.com.wilsonpizzas.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.dao.DAOUsuario;
import br.com.wilsonpizzas.models.Produto;
import br.com.wilsonpizzas.models.Usuario;
import br.com.wilsonpizzas.retrofit.RetrofitConfig;

import java.util.List;

import br.com.wilsonpizzas.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (Util.verificaConexao(this)) {
            Call<List<Produto>> call = new RetrofitConfig().getProdutosService().listProdutos();
            call.enqueue(new Callback<List<Produto>>() {
                @Override
                public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                    Util.PRODUTOS = response.body();
                }

                @Override
                public void onFailure(Call<List<Produto>> call, Throwable t) {

                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i;
                    DAOUsuario daoUsuario = DAOUsuario.getInstance(getApplicationContext());

                    if (daoUsuario.select().size() == 0) {
                        i = new Intent(Splash.this, Login.class);

                    } else {
                        i = new Intent(Splash.this, MainActivity.class);
                        Login.logado = true;
                    }

                    startActivity(i);
                    finish();
                }


            }, SPLASH_TIME_OUT);

        } else

        {
            new AlertDialog.Builder(this)
                    .setTitle("Sem conexão")
                    .setMessage("Verifique sua conexão com a Internet e tente novamente...")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                    System.exit(0);
                                }

                            }).show();
        }


    }


}
