package br.com.wilsonpizzas.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.models.Produto;

import java.util.ArrayList;

public class ListAdapterAdicionarSabor extends ArrayAdapter<Produto> {
    private final Context context;
    private final ArrayList<Produto> elementos;
    private Produto produto;
    private double valorT;
    private TextView valorTotal;
    public ListAdapterAdicionarSabor(Context context, ArrayList<Produto> elementos, Produto produto, double valorT) {
        super(context, R.layout.listview_model, elementos);
        this.context=context;
        this.elementos=elementos;
        this.produto = produto;
        this.valorT = valorT;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.model_list_sabores, parent, false);

        final TextView nomeProduto = view.findViewById(R.id.listSaborNome);
        final TextView valorProduto = view.findViewById(R.id.listValorSaborProduto);
        final ImageButton iconDelete = view.findViewById(R.id.listIconAdicionar);
        final TextView descricaoProduto = view.findViewById(R.id.listSaborDescricao);

        nomeProduto.setText(elementos.get(position).getNome());
        valorProduto.setText("R$"+elementos.get(position).getValor());
        descricaoProduto.setText(elementos.get(position).getDescricao());
        iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Adicionar Sabor")
                                .setMessage("Ao adicionar um sabor o valor da pizza será o maior valor entre as duas")
                                .setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (Double.parseDouble(elementos.get(position).getValor()) >
                                                        Double.parseDouble(produto.getValor())) {
                                                    valorT -= Double.parseDouble(produto.getValor());
                                                    valorT+= Double.parseDouble(elementos.get(position).getValor());
                                                    MyListAdapter.valorTotal.setText("R$"+valorT+"0");
                                                }
                                                String ob=produto.getObservacao();
                                                if(ob!=null) {
                                                    produto.setObservacao(produto.getObservacao() + " | Metade " + produto.getNome() + ", metade " + elementos.get(position).getNome());
                                                }else{
                                                    produto.setObservacao(" Metade " + produto.getNome() + ", metade " + elementos.get(position).getNome());
                                                }
                                                    ((Activity)getContext()).finish();
                                            }

                                        })
                                .setNegativeButton("Cancelar", null)
                                .show();
                    }
        });

        return view;
    }


}