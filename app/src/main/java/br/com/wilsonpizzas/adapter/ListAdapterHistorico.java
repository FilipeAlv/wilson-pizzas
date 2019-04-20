package br.com.wilsonpizzas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.activity.Hitorico;
import br.com.wilsonpizzas.models.Pedido;
import br.com.wilsonpizzas.models.PedidoRet;
import br.com.wilsonpizzas.retrofit.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapterHistorico extends ArrayAdapter<PedidoRet> {
    private final Context context;
    private final List<PedidoRet> elementos;
    private final ArrayAdapter<PedidoRet> adapter;
    private DateFormat formatUS = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public ListAdapterHistorico(Context context, List<PedidoRet> elementos) {
        super(context, R.layout.listview_historico_model, elementos);
        this.context=context;
        this.elementos=elementos;
        this.adapter = this;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview_historico_model, parent, false);

        final TextView dataPedido = view.findViewById(R.id.listDataPedido);
        final TextView valorPedido = view.findViewById(R.id.listValorPedido);
        final TextView statusPedido = view.findViewById(R.id.listStatusPedido);
        final ImageButton iconCancelar = view.findViewById(R.id.cancelar);
        final ViewHolderList holderList = new ViewHolderList(view);

        dataPedido.setText(formatarData(elementos.get(position).getData()));
        valorPedido.setText(elementos.get(position).getValor());

        if (elementos.get(position).getStatus().equals("ok")){
            statusPedido.setText( "Estado do Pedido: recebido");
        }else{
            statusPedido.setText( "Estado do Pedido: "+elementos.get(position).getStatus());
        }
        iconCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(elementos.get(position).getStatus().equals("cancelado")) && validarTempo(elementos.get(position))){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Cancelar Pedido")
                            .setMessage("Tem certeza que deseja cancelar este pedido?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Call<PedidoRet> call = new RetrofitConfig().getPedidoService().editStatusPedido(
                                            elementos.get(position).getId(), "cancelado"
                                    );

                                    call.enqueue(new Callback<PedidoRet>() {
                                        @Override
                                        public void onResponse(Call<PedidoRet> call, Response<PedidoRet> response) {
                                            elementos.get(position).setStatus("cancelado");
                                            notifyDataSetChanged();


                                        }

                                        @Override
                                        public void onFailure(Call<PedidoRet> call, Throwable t) {
                                            elementos.get(position).setStatus("cancelado");
                                            notifyDataSetChanged();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Não", null).show();

                }else{
                    mensagem(elementos.get(position));
                }
            }
        });

        return view;
    }
    private boolean validarTempo(PedidoRet pedido) {
        Calendar agora, pedidoH;
        agora = new GregorianCalendar();
        pedidoH = new GregorianCalendar();
        Date dataPedido;

        agora.setTime(new Date());
        try {
            dataPedido = formatUS.parse(pedido.getData());
            dataPedido.setMinutes(dataPedido.getMinutes()+15);
            pedidoH.setTime(dataPedido);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (agora.getTime().after(pedidoH.getTime())) {
            Toast.makeText(context, "Não é mais possivel cancelar o pedido. O tempo de cancelamento é 15min.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    private void mensagem(PedidoRet pedido){
        if(pedido.getStatus().equals("cancelado")) {
            Toast.makeText(context, "Este pedido já foi cancelado", Toast.LENGTH_LONG).show();
        }
    }
    public class ViewHolderList implements Serializable{

       // final ImageView  detalhes;
        final ImageView cancelar;

        public ViewHolderList(View view) {

            //detalhes = view.findViewById(R.id.detalhes);
            cancelar = view.findViewById(R.id.cancelar);
        }

    }

    private String formatarData(String data){
        String dataFinal = null;
        Date date = null;

        try {
            date = formatUS.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat formatBR = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dataFinal = formatBR.format(date);

        return dataFinal;
    }
}