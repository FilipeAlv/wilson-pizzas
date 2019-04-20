package br.com.wilsonpizzas.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.dao.DAOUsuario;
import br.com.wilsonpizzas.models.ClienteRet;
import br.com.wilsonpizzas.models.Usuario;
import br.com.wilsonpizzas.retrofit.RetrofitConfig;

import br.com.wilsonpizzas.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private DAOUsuario daoUsuario = DAOUsuario.getInstance(getBaseContext());
    static TextView tv_esqueceu_senha;
    static boolean logado;
    private EditText edPassword;
    private ClienteRet usuario;
    private EditText edLogin;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        pb=findViewById(R.id.progressBarLogin);
        edLogin  = findViewById(R.id.edit_txt_login);
        Util.mascararEditText("(NN)NNNNN-NNNN", edLogin);
        edPassword = findViewById(R.id.edit_txt_password);
        final Button bnt_login = findViewById(R.id.button_login);
        final Button btnCadastrar = findViewById(R.id.btn_Cadastrar);

        tv_esqueceu_senha = (TextView)findViewById(R.id.txt_esqueceu_senha);

        tv_esqueceu_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ActivityEqueceuSenha.class);
                startActivity(intent);
            }
        });



        bnt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                validarUsuario(edLogin.getText().toString(), edPassword.getText().toString());

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ActivityCadastro.class);
                startActivity(intent);
            }
        });


    }

    private void validarUsuario(final String telefone, final String senha) {
        Call<ClienteRet> call = new RetrofitConfig().getClienteService().validarCliente(telefone, senha);
        call.enqueue(new Callback<ClienteRet>() {
            @Override
            public void onResponse(Call<ClienteRet> call, Response<ClienteRet> response) {
                usuario = response.body();
                if(usuario.getLogin()!=null) {
                    mudarActivity(usuario);
                }else {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Telefone ou senha incorretos.", Toast.LENGTH_SHORT).show();
                    edPassword.setText("");
                }
            }

            @Override
            public void onFailure(Call<ClienteRet> call, Throwable t) {

            }
        });

    }

    private void mudarActivity(final ClienteRet cliente){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = new Usuario(cliente.getLogin(), cliente.getSenha());

                daoUsuario.deleteAll();
                daoUsuario.insert(usuario);
                Log.d("", daoUsuario.select().get(0).toString());

                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                logado = true;
                pb.setVisibility(View.GONE);

                finish();
            }
        },1000);



    }
}
