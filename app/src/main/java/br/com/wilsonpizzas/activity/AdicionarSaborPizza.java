package br.com.wilsonpizzas.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.adapter.ListAdapterAdicionarSabor;
import br.com.wilsonpizzas.models.Produto;
import br.com.wilsonpizzas.util.Util;

import java.util.ArrayList;

public class AdicionarSaborPizza extends AppCompatActivity {

    private double valorT;
    private String tamanho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        tamanho = bundle.getString("tamanho");
        valorT = bundle.getDouble("valorT");
        Produto produto = new Produto();
        ArrayList<Produto> sabores = new ArrayList<>();
        for (Produto pizza : Util.PRODUTOS) {
            if (pizza.getCategoria().equals("Pizzas") && pizza.getTamanho().equals(tamanho)){
                    sabores.add(pizza);
            }

        }

        setContentView(R.layout.activity_adicionar_sabor_pizza);
        ArrayAdapter<Produto> produtoAdapter = new ListAdapterAdicionarSabor(this,sabores, produto, valorT);
        ListView lvPedidos = (ListView) findViewById(R.id.lv_sabores);
        lvPedidos.setAdapter(produtoAdapter);


    }
}
