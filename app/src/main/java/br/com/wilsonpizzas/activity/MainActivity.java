package br.com.wilsonpizzas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import br.com.wilsonpizzas.R;
import br.com.wilsonpizzas.adapter.PageAdapterPrincipal;
import br.com.wilsonpizzas.adapter.RecycleViewAdapter;
import br.com.wilsonpizzas.controller.Controller;
import br.com.wilsonpizzas.dao.DAOUsuario;
import br.com.wilsonpizzas.models.Pedido;
import br.com.wilsonpizzas.util.Util;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static ImageButton fabPedidos;
    public  DAOUsuario daoUsuario = DAOUsuario.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Util.PEDIDO  = new Pedido();
        fabPedidos = findViewById(R.id.fabPedidos);
        fabPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityMeusPedidos.class);
                startActivity(intent);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutPrincipal);
        viewPager = (ViewPager) findViewById(R.id.viewPagerPrincipal);

        viewPager.setAdapter(new PageAdapterPrincipal(getSupportFragmentManager(), getResources().getStringArray(R.array.titles_Principal),
                getResources().getStringArray(R.array.titles_Secundaria_tab_1),
                getResources().getStringArray(R.array.titles_Secundaria_tab_2)));

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Sair")
                .setMessage("Tem certeza que deseja sair do aplicativo?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("Não", null).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.STATUS) {
            for (RecycleViewAdapter.MyViewHolder holder : RecycleViewAdapter.HOLDERS) {
                if (holder.fab.getTag().equals("selecionado")) {
                    holder.fab.setTag("disponivel");
                    holder.fab.setImageResource(R.drawable.fab_disponivel_24dp);
                    holder.card.setBackground(Controller.background);
                }
            }
            Util.STATUS = false;
            fabPedidos.setBackgroundResource(R.drawable.ic_pedidos);
        }
        if(Util.PEDIDO.getProdutos().size()==0){
            fabPedidos.setBackgroundResource(R.drawable.ic_pedidos);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_meusPedidos) {
            Intent intent = new Intent(MainActivity.this, Hitorico.class);
            startActivity(intent);
        }
        if(id == R.id.action_logOf){
            if(Login.logado == true) {
                new AlertDialog.Builder(this)
                        .setTitle("Sair")
                        .setMessage("Tem certeza que deseja sair da sua conta?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                daoUsuario.deleteAll();

                                Intent intent = new Intent(MainActivity.this, Login.class);
                                startActivity(intent);
                                finish();
                                Login.logado = false;
                            }
                        })
                        .setNegativeButton("Não", null).show();

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static ImageButton getFavProdutos(){
        return fabPedidos;
    }
}
