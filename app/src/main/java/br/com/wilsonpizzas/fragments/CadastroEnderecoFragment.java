package br.com.wilsonpizzas.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.activity.ActivityCadastro;
import br.com.wilsonpizzas.models.Endereco;
import br.com.wilsonpizzas.util.Util;

public class CadastroEnderecoFragment extends Fragment {
    Button btnAnteriorDadosUsuario;
    Button btnProximoTelefone;

    static EditText edNomeRua, edBairro, edNumero, edPontoDeReferencia, edLogradouro, edCep;
    static Spinner edCidade;


    public CadastroEnderecoFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_endereco, container, false);

        edNomeRua = (EditText)view.findViewById(R.id.edNomeRua);
        edBairro = (EditText)view.findViewById(R.id.edBairro);
        edCidade = view.findViewById(R.id.edCidade);
        edCep = (EditText)view.findViewById(R.id.edCep);
        edLogradouro = (EditText)view.findViewById(R.id.edLograduouro);
        edNumero = (EditText)view.findViewById(R.id.edNumeroCasa);
        edPontoDeReferencia = (EditText)view.findViewById(R.id.edPontoDeReferencia);


        Util.mascararEditText("NNNNN-NNN", edCep);

        btnAnteriorDadosUsuario =  (Button)view.findViewById(R.id.btn_anteriorDadosUsuario);
        btnProximoTelefone =  (Button)view.findViewById(R.id.btn_proximoTelefone);






        btnAnteriorDadosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_cadastro, new CadastroDadosUsuarioFragment()).commit();
            }
        });

        btnProximoTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                        edNomeRua.getText().length()==0||
                                edNumero.getText().length()==0||
                                edCep.getText().length()==0||
                                edPontoDeReferencia.getText().length()==0||
                                edBairro.getText().length()==0){
                    Toast.makeText(view.getContext(), "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                }else {
                    Endereco endereco = new Endereco();
                    endereco.setRua(edNomeRua.getText().toString());
                    endereco.setBairro(edBairro.getText().toString());
                    endereco.setCep(edCep.getText().toString());
                    endereco.setNumero(edNumero.getText().toString());
                    endereco.setCidade(edCidade.getSelectedItem().toString());
                    endereco.setPonto_referencia(edPontoDeReferencia.getText().toString());
                    ActivityCadastro.CLIENTE.setEndereco(endereco);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_cadastro, new CadastroLoginSenhaFragment()).commit();

                    }
                }
        });




        return view;
    }

}
