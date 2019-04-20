package br.com.wilsonpizzas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.wilsonpizzas.models.Usuario;

import java.util.ArrayList;

public class DAOUsuario {
    private SQLiteDatabase bd;
    private static  DAOUsuario daoUsuario;
    Context contect;
    public DAOUsuario(Context ctx){
        this.contect = ctx;
        BDCore bdAux = new BDCore(ctx);
        bd = bdAux.getWritableDatabase();
    }

    public static DAOUsuario getInstance(Context context){
        if(daoUsuario == null){
            daoUsuario = new DAOUsuario(context);
        }
        return daoUsuario;
    }

    public void insert(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("login", usuario.getLogin());
        valores.put("senha", usuario.getPassword());

        bd.insert("usuario", null, valores);
    }
    public void update(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("login", usuario.getLogin());
        valores.put("senha", usuario.getLogin());

        bd.update("usuario",valores,"_id = ?", new String[]{""+usuario.getId()});

    }
    public void delete(Usuario usuario){
        bd.delete("usuario", "_id = "+usuario.getId(), null);
    }
    public void deleteAll(){
        bd.delete("usuario", null, null);
    }
    public ArrayList<Usuario> select(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String[] colunas = new String[]{"login", "senha"};
        Cursor cursor = bd.query("usuario",colunas, null, null, null, null, "login ASC");


        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                Usuario u =  new Usuario( cursor.getString(0), cursor.getString(1));
                usuarios.add(u);
            }while (cursor.moveToNext());

            }
        return  usuarios;
    }
    public Usuario select_verificarLogin(String login,String senha){
        Usuario usuario = null;
        String[] colunas = new String[]{"login", "senha"};
        Cursor cursor = bd.query("usuario",colunas, "login = " + "'" + login + "'" +" and senha = " + "'" + senha + "'" , null, null, null, "login ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                usuario =  new Usuario( cursor.getString(0), cursor.getString(1));
            }while (cursor.moveToNext());

        }
        return  usuario;
    }

}
