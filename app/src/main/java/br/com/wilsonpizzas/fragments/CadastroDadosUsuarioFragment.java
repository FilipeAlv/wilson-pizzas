package br.com.wilsonpizzas.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.activity.ActivityCadastro;
import br.com.wilsonpizzas.activity.ActivityEqueceuSenha;
import br.com.wilsonpizzas.models.ClienteRet;
import br.com.wilsonpizzas.retrofit.RetrofitConfig;
import br.com.wilsonpizzas.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CadastroDadosUsuarioFragment extends Fragment {
    Button btnProximoEndereco;
    ProgressBar pb;
    static EditText edNome, edCPF, edRG, edData_nascimento;
    boolean existe;
    public CadastroDadosUsuarioFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_cadastro_dados_usuario, container, false);

        btnProximoEndereco = (Button)view.findViewById(R.id.btn_ProximoEndereco);
        edNome = (EditText)view.findViewById(R.id.edNome);
        edCPF = (EditText)view.findViewById(R.id.edCPF);
       // edRG= (EditText)view.findViewById(R.id.edRG);
        edData_nascimento = (EditText)view.findViewById(R.id.edData_nascimento);
        pb= view.findViewById(R.id.progressBarCadastro);

        Util.mascararEditText("NNN.NNN.NNN-NN", edCPF);
        Util.mascararEditText("NN/NN/NNNN", edData_nascimento);


        btnProximoEndereco.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(
                       edNome.getText().length()==0||
                       edCPF.getText().length()==0||
                     //  edRG.getText().length()==0||
                       edData_nascimento.getText().length()==0 ){
                   Toast.makeText(view.getContext(), "Todos os campos devem ser preenchidos.", Toast.LENGTH_LONG).show();
               }else if(!Util.isCPF(edCPF.getText().toString())) {
                   Toast.makeText(getContext(), "CPF Inválido.", Toast.LENGTH_LONG).show();
               }else{
                   pb.setVisibility(View.VISIBLE);
                   CPFExiste(edCPF.getText().toString());
               }
           }
       });

        return view;


    }

    public void acaoProximoEndereco(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_cadastro, new CadastroEnderecoFragment()).commit();
    }




    public void CPFExiste(String cpf){
        Call<ClienteRet> call = new RetrofitConfig().getClienteService().buscarClientePorCpf(cpf);
        call.enqueue(new Callback<ClienteRet>() {
            @Override
            public void onResponse(Call<ClienteRet> call, Response<ClienteRet> response) {
                ClienteRet cliente = response.body();
                completarVerificacao(cliente);

            }

            @Override
            public void onFailure(Call<ClienteRet> call, Throwable t) {

            }
        });
    }

    private void completarVerificacao(ClienteRet clienteRet)  {
        if (clienteRet.getNome()!=null && clienteRet.getSenha()!=null){
            Util.CPFEXISTE = true;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Util.CPFEXISTE) {
                    pb.setVisibility(View.GONE);
                    new AlertDialog.Builder(getActivity(),R.style.DialogStyle)
                            .setTitle("CPF já cadastrado")
                            .setPositiveButton("Recuperar senha", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(getContext(), ActivityEqueceuSenha.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Ok", null).show();
                    Util.CPFEXISTE=false;
                }
                else {
                    pb.setVisibility(View.GONE);
                    ActivityCadastro.CLIENTE.setNome(edNome.getText().toString().trim());
                    ActivityCadastro.CLIENTE.setCpf(edCPF.getText().toString().trim());
                    ActivityCadastro.CLIENTE.setRg(null);
                    ActivityCadastro.CLIENTE.setData_nascimento(edData_nascimento.getText().toString().trim());
                    acaoProximoEndereco();
                }
            }
        },1000);


    }




}
