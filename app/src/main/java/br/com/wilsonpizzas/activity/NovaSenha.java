package br.com.wilsonpizzas.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.models.ClienteRet;
import br.com.wilsonpizzas.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovaSenha extends AppCompatActivity {
    private EditText edNovaSenha, edConfNovaSenha;
    private Button btnSalvar;
    private String senha, cpf;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_senha);
        pb=findViewById(R.id.progressBarNovaSenha);
        edNovaSenha = findViewById(R.id.ednovaSenha);
        edConfNovaSenha = findViewById(R.id.edConfirmarNovaSen);
        btnSalvar = findViewById(R.id.btn_salvar);
        cpf = getIntent().getStringExtra("cpf");
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edNovaSenha.getText().length()==0||
                        edConfNovaSenha.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show();
                }else if(!(edNovaSenha.getText().toString().trim().equals(edConfNovaSenha.getText().toString().trim()))){
                    Toast.makeText(getApplicationContext(), "As senhas inseridas não são iguais", Toast.LENGTH_LONG).show();
                }else{
                    senha = edNovaSenha.getText().toString().trim();
                    pb.setVisibility(View.VISIBLE);
                    atualizarSenha();
                }
            }
        });
    }

    private void atualizarSenha(){
        Call<ClienteRet> call = new RetrofitConfig().getClienteService().atualizarSenha(senha, cpf);
        call.enqueue(new Callback<ClienteRet>() {
            @Override
            public void onResponse(Call<ClienteRet> call, Response<ClienteRet> response) {
                final ClienteRet cliente = response.body();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(cliente.getNome()!=null&&cliente.getSenha()!=null){
                            Toast.makeText(getApplicationContext(), "Senha alterada com sucesso", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            finish();
                        }else{
                            pb.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Erro ao tentar se conectar com o servidor", Toast.LENGTH_LONG).show();
                        }
                    }
                },1000);

            }

            @Override
            public void onFailure(Call<ClienteRet> call, Throwable t) {

            }
        });
    }
}
