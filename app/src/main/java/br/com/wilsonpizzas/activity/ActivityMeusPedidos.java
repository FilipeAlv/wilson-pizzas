package br.com.wilsonpizzas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.adapter.MyListAdapter;
import br.com.wilsonpizzas.models.Produto;
import br.com.wilsonpizzas.util.Util;


public class ActivityMeusPedidos extends AppCompatActivity {
    private double valorT=0;
    private TextView valorTotal;
    Button confirmarPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        confirmarPedido = (Button) findViewById(R.id.bnt_confirmarPedido);
        confirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.PEDIDO.getProdutos().size()==0){
                    Toast.makeText(getApplicationContext(),"Voce não possui pedidos no momento", Toast.LENGTH_SHORT).show();
                }//else if(validarDia()){
                   // Toast.makeText(getApplicationContext(), "Desculpe! Estamos fechados para descanço. Abriremos amanhã.", Toast.LENGTH_LONG).show();
                //}
                else if(!validarHorario()) {
                    Toast.makeText(getApplicationContext(), "Desculpe! O horário de pedidos é de 17:00 às 23:30.", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(ActivityMeusPedidos.this, ConfirmarPedido.class);
                    intent.putExtra("valorT", valorT);
                    startActivity(intent);
                }
            }
        });

        if (Util.PEDIDO.getProdutos().size()==0){
            Toast.makeText(this, "Voce não possui pedidos no momento", Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(this, "Adicionado R$4,00 do valor de entrega.", Toast.LENGTH_LONG).show();


            somarProdutos();

            valorTotal = findViewById(R.id.listValorTotal);
            ArrayAdapter<Produto> pedidosAdapter = new MyListAdapter(this, Util.PEDIDO.getProdutos(), valorT, valorTotal);
            ListView lvPedidos = (ListView) findViewById(R.id.lv_pedidos);
            lvPedidos.setAdapter(pedidosAdapter);

            valorTotal.setText("R$"+valorT+"0");
        }

    }

    @Override
    protected void onStop() {
        valorT = 0;
        super.onStop();
    }

    private boolean validarDia(){
        Calendar hoje = new GregorianCalendar();
        hoje.setTime(new Date());
        if(hoje.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
            return true;
        }
        return false;
    }

    private boolean validarHorario(){
        Calendar agora, aberto, fechado;
        agora = new GregorianCalendar();
        aberto = new GregorianCalendar();
        fechado = new GregorianCalendar();

        agora.setTime(new Date());
        aberto.set(Calendar.HOUR_OF_DAY, 17);
        aberto.set(Calendar.MINUTE, 0);
        aberto.set(Calendar.SECOND, 0);
        aberto.set(Calendar.MILLISECOND, 0);

        fechado.set(Calendar.HOUR_OF_DAY, 23);
        fechado.set(Calendar.MINUTE, 30);
        fechado.set(Calendar.SECOND, 0);
        fechado.set(Calendar.MILLISECOND, 0);

        if(agora.getTime().after(aberto.getTime()) && agora.getTime().before(fechado.getTime())){
            return true;
        }

        return false;
    }


    public void somarProdutos(){
        for (Produto produto: Util.PEDIDO.getProdutos()) {
            valorT+=Double.parseDouble(produto.getValor());
            valorT+=4;
            produto.setQuantidade(1);
        }
    }
}
