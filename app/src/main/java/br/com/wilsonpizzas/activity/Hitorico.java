package br.com.wilsonpizzas.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.adapter.ListAdapterHistorico;
import br.com.wilsonpizzas.dao.DAOUsuario;
import br.com.wilsonpizzas.models.ClienteRet;
import br.com.wilsonpizzas.models.PedidoRet;
import br.com.wilsonpizzas.models.Usuario;
import br.com.wilsonpizzas.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hitorico extends AppCompatActivity {
    private ProgressBar pb;
    List<PedidoRet> pedidos = new ArrayList<>();
    public static ArrayAdapter<PedidoRet> pedidosAdapter;
    int cliente_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitorico);

        pb = findViewById(R.id.progress_bar_historico);
        ArrayList<Usuario> usuarios = DAOUsuario.getInstance(getApplicationContext()).select();
        Usuario usuario = usuarios.get(0);

        listarPedidos(usuario);
    }


    private boolean validarDia() {
        Calendar hoje = new GregorianCalendar();
        hoje.setTime(new Date());
        if (hoje.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            return true;
        }
        return false;
    }



    public void listarPedidos(final Usuario usuario) {
        pb.setVisibility(View.VISIBLE);
        Call<ClienteRet> call = new RetrofitConfig().getClienteService().buscarCliente(usuario.getLogin());
        call.enqueue(new Callback<ClienteRet>() {
            @Override
            public void onResponse(Call<ClienteRet> call, Response<ClienteRet> response) {
                ClienteRet cliente = response.body();
                cliente_id = cliente.getId();

                Call<List<PedidoRet>> call2 = new RetrofitConfig().getPedidoService().listPedidos(
                        cliente_id);

                call2.enqueue(new Callback<List<PedidoRet>>() {
                    @Override
                    public void onResponse(Call<List<PedidoRet>> call, Response<List<PedidoRet>> response) {
                        pedidos = response.body();
                        preencher();
                        pb.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<List<PedidoRet>> call, Throwable t) {
                        pb.setVisibility(View.GONE);

                    }
                });


            }

            @Override
            public void onFailure(Call<ClienteRet> call, Throwable t) {
                cliente_id =  -1;

            }
        });




    }

    private void preencher(){
        if (pedidos.size()==0) {
            new AlertDialog.Builder(this)
                    .setTitle("Ops!")
                    .setMessage("Você ainda não fez nenhum pedido")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onBackPressed();
                        }
                    }).show();
        } else {
            pedidosAdapter = new ListAdapterHistorico(this, pedidos);
            ListView lvPedidos = (ListView) findViewById(R.id.lv_historico);
            lvPedidos.setAdapter(pedidosAdapter);
        }
    }


}

