package br.com.wilsonpizzas.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import br.com.wilsonpizzas.dao.DAOUsuario;
import br.com.wilsonpizzas.models.Cliente;
import br.com.wilsonpizzas.models.ClienteRet;
import br.com.wilsonpizzas.retrofit.RetrofitConfig;
import br.com.wilsonpizzas.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CadastroLoginSenhaFragment extends Fragment {
    Button btnCadastrar;
    Button btnAnteriorConfirmarCodigo;
    static EditText edNumeroTelefone, edSenha, edConfSenha;
    private ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_login_e_senha, container, false);

        pb = view.findViewById(R.id.progressBarLogineSenha);
        btnAnteriorConfirmarCodigo = (Button)view.findViewById(R.id.btn_anteriorConfirmarCodigo);
        btnCadastrar = (Button)view.findViewById(R.id.btn_cadastrar);

        edNumeroTelefone = (EditText)view.findViewById(R.id.edTelefone);
        Util.mascararEditText("(NN)NNNNN-NNNN", edNumeroTelefone);
        edSenha = (EditText) view.findViewById(R.id.edSenha);
        edConfSenha = view.findViewById(R.id.edConfirmarSenha);

        final DAOUsuario daoUsuario = DAOUsuario.getInstance(getContext());

        btnAnteriorConfirmarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_cadastro, new CadastroEnderecoFragment()).commit();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                        edNumeroTelefone.getText().length()==0||
                        edSenha.getText().length()==0||
                        edConfSenha.getText().length()==0){
                    Toast.makeText(view.getContext(), "Verifique se todos os dados estão preenchidos corretamente", Toast.LENGTH_SHORT).show();
                }else if (!edConfSenha.getText().toString().trim().equals(edSenha.getText().toString().trim())){
                    Toast.makeText(view.getContext(), "As senhas inseridas não são iguais", Toast.LENGTH_SHORT).show();

                }else{
                    pb.setVisibility(View.VISIBLE);
                    telefoneExiste(edNumeroTelefone.getText().toString().trim());
                }
            }
        });

        return view;
    }

    private void telefoneExiste(String telefone){
        Call<ClienteRet> call = new RetrofitConfig().getClienteService().buscarCliente(telefone);
        call.enqueue(new Callback<ClienteRet>() {
            @Override
            public void onResponse(Call<ClienteRet> call, Response<ClienteRet> response) {
                ClienteRet cliente = response.body();
                if(cliente.getNome()==null&&cliente.getSenha()==null){
                    ActivityCadastro.CLIENTE.setTelefone(edNumeroTelefone.getText().toString());
                    ActivityCadastro.CLIENTE.setPassword(edSenha.getText().toString());
                    cadastrarCliente(ActivityCadastro.CLIENTE);
                    Toast.makeText(getContext(),"Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
                    getActivity().finish();

                }else{
                    Toast.makeText(getContext(),"Este número de telefone já está sendo utilizado", Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ClienteRet> call, Throwable t) {
                Toast.makeText(getContext(),"Problemas ao conectar com o servidor. Verifique sua conexão e tente novamente", Toast.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
            }
        });


    }

    private void cadastrarCliente(final Cliente cliente){
                        Call<String> call = new RetrofitConfig().getClienteService().insertUser(
                        cliente.getEndereco().getRua(),
                        cliente.getEndereco().getNumero(),
                        cliente.getEndereco().getBairro(),
                        cliente.getEndereco().getCidade(),
                        cliente.getEndereco().getCep(),
                        cliente.getEndereco().getPonto_referencia(),
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getRg(),
                        cliente.getData_nascimento(),
                        cliente.getLogin(),
                        cliente.getPassword(),
                        cliente.getTelefone());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                pb.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                pb.setVisibility(View.GONE);

                            }
                        });



    }

}
