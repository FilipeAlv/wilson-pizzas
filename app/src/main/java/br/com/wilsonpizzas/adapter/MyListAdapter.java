package br.com.wilsonpizzas.adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.activity.AdicionarSaborPizza;
import br.com.wilsonpizzas.controller.Controller;
import br.com.wilsonpizzas.models.Produto;
import java.io.Serializable;
import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<Produto> {
    private final Context context;
    private final ArrayList<Produto> elementos;
    private double valorT;
    public static TextView valorTotal;
    public MyListAdapter(Context context, ArrayList<Produto> elementos, double valorT, TextView valorTotal) {
        super(context, R.layout.listview_model, elementos);
        this.context=context;
        this.elementos=elementos;
        this.valorT = valorT;
        this.valorTotal = valorTotal;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview_model, parent, false);

       // ImageView imageProduto = view.findViewById(R.id.listImageProduto);
        //Util.carregarImagem(imageProduto, elementos.get(position).getURLImagem());
        final TextView nomeProduto = view.findViewById(R.id.listNomeProduto);
        final TextView valorProduto = view.findViewById(R.id.listValorProduto);
        final ImageButton iconDelete = view.findViewById(R.id.listIconDelete);
        final ViewHolderList holderList = new ViewHolderList(view);
        final TextView quantidade = view.findViewById(R.id.Quantidade);

        nomeProduto.setText(elementos.get(position).getNome());
        valorProduto.setText("R$"+elementos.get(position).getValor());
        quantidade.setText(""+elementos.get(position).getQuantidade());
        iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (final RecycleViewAdapter.MyViewHolder hold: RecycleViewAdapter.HOLDERS) {
                    final RecycleViewAdapter.MyViewHolder holder = hold;
                    if (holder.tvNome.getText().equals(elementos.get(position).getNome())){
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Excluir pedido")
                                .setMessage("Tem certeza que deseja excluir esse pedido?")
                                .setPositiveButton("Sim",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                holder.fab.setImageResource(R.drawable.fab_disponivel_24dp);
                                                holder.fab.setTag("disponivel");
                                                holder.card.setBackground(Controller.background);
                                                valorT -= Double.parseDouble(elementos.get(position).getValor())*Integer.parseInt(holderList.qnt_text.getText().toString());
                                                valorTotal.setText("R$" + valorT + "0");
                                                elementos.remove(elementos.get(position));
                                                notifyDataSetChanged();
                                            }

                                        })
                                .setNegativeButton("não", null)
                                .show();

                        break;
                    }
                }
            }
        });

        holderList.qnt_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.parseInt(holderList.qnt_text.getText().toString());
                if(quant<=1){
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Esta é a quantidade minima para este pedido. ")
                            .setPositiveButton("OK", null)
                            .show();
                }else{
                    quant--;
                    elementos.get(position).setQuantidade(quant);
                    holderList.qnt_text.setText(""+quant);
                    valorT-= Double.parseDouble(elementos.get(position).getValor());
                    valorTotal.setText("R$" + valorT + "0");
                }
            }
        });

        holderList.qnt_mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.parseInt(holderList.qnt_text.getText().toString());
                    quant++;
                    elementos.get(position).setQuantidade(quant);
                    holderList.qnt_text.setText(""+quant);
                    valorT+= Double.parseDouble(elementos.get(position).getValor());
                    valorTotal.setText("R$" + valorT + "0");

            }
        });
        if(elementos.get(position).getCategoria().equals("Pizzas")){

            holderList.observacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Alterar Pedido")
                            .setMessage("Você pode adicionar mais sabores a sua pizza ou remover alguns ingredientes")
                            .setPositiveButton("Adicionar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(context, AdicionarSaborPizza.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putDouble("valorT", valorT);
                                            bundle.putString("tamanho", elementos.get(position).getTamanho());
                                            intent.putExtras(bundle);
                                            context.startActivity(intent);
                                        }

                                    }
                                    )
                            .setNegativeButton("Remover",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext())
                                                    .setTitle("Remover Ingredientes");
                                            final EditText input = new EditText(getContext());
                                            input.setHint("Ex.: Sem ervilha, sem tomate");
                                            alertDialog.setView(input);
                                            alertDialog.setPositiveButton("Salvar",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            String ob = elementos.get(position).getObservacao();
                                                            if (ob==null) {
                                                                elementos.get(position).setObservacao(ob + " | " + input.getText().toString());

                                                            }else {
                                                                elementos.get(position).setObservacao(input.getText().toString());
                                                            }
                                                        }
                                                    })
                                                    .setNegativeButton("Cancelar", null);
                                            alertDialog.show();
                                        }
                                    }).show();
                }
            });

        }else{

            holderList.observacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Remover Ingredientes");
                    final EditText input = new EditText(getContext());
                    input.setHint("Ex.: Sem ervilha, sem tomate");
                    alertDialog.setView(input);
                    alertDialog.setPositiveButton("Salvar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String ob = elementos.get(position).getObservacao();
                                    if (ob==null) {
                                        elementos.get(position).setObservacao(ob + " | " + input.getText().toString());
                                    }else {
                                        elementos.get(position).setObservacao(input.getText().toString());
                                    }
                                    }
                            })
                            .setNegativeButton("Cancelar", null);
                    alertDialog.show();
                }
            });

        }

        return view;
    }

    public class ViewHolderList implements Serializable{

        final TextView qnt_mais;
        final TextView qnt_menos;
        final TextView qnt_text;
        final ImageView  observacao;
        final TextView outroSabor;

        public ViewHolderList(View view) {
            qnt_mais = (TextView) view.findViewById(R.id.qnt_mais);
            qnt_menos = (TextView) view.findViewById(R.id.qnt_menos);
            qnt_text = view.findViewById(R.id.Quantidade);
            observacao = view.findViewById(R.id.Observacao);
            outroSabor = view.findViewById(R.id.outroSabor);
        }

    }
}