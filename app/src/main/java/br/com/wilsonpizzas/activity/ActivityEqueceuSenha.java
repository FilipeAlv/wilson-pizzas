package br.com.wilsonpizzas.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.models.ClienteRet;
import br.com.wilsonpizzas.retrofit.RetrofitConfig;
import br.com.wilsonpizzas.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEqueceuSenha extends AppCompatActivity {
    private EditText edCpf, edTelefone, edDataNascimento;
    private String cpf, telefone, dtNascimetno;
    private Button bnt;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_minha_senha);

        edCpf = findViewById(R.id.edCpfEsqSen);
        edDataNascimento = findViewById(R.id.edData_nascimentoEsqSen);
        edTelefone = findViewById(R.id.edTelefoneEsqSen);
        bnt=findViewById(R.id.button_recuperar);
        Util.mascararEditText("NNN.NNN.NNN-NN", edCpf);
        Util.mascararEditText("(NN)NNNNN-NNNN", edTelefone);
        Util.mascararEditText("NN/NN/NNNN", edDataNascimento);

        pb = findViewById(R.id.progressBarEsqueciSenha);



        bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edCpf.getText().length()!=0&&
                   edDataNascimento.getText().length()!=0&&
                   edTelefone.getText().length()!=0) {
                    cpf = edCpf.getText().toString().trim();
                    dtNascimetno = edDataNascimento.getText().toString().trim();
                    telefone = edTelefone.getText().toString().trim();
                    pb.setVisibility(View.VISIBLE);
                    verificarUsuario();
                } else
                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos.", Toast.LENGTH_LONG).show();
            }
        });


    }


    private void verificarUsuario(){
        Call<ClienteRet> call = new RetrofitConfig().getClienteService().validarEsqueceuSenha(cpf, dtNascimetno, telefone);
        call.enqueue(new Callback<ClienteRet>() {
            @Override
            public void onResponse(Call<ClienteRet> call, Response<ClienteRet> response) {
                final ClienteRet cliente = response.body();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(cliente.getNome()!=null&&cliente.getSenha()!=null){
                            Intent intent = new Intent(getApplicationContext(), NovaSenha.class);
                            intent.putExtra("cpf", cliente.getCpf());
                            startActivity(intent);
                            finish();
                        }else{
                            pb.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Dados não encontrados.", Toast.LENGTH_LONG).show();
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
