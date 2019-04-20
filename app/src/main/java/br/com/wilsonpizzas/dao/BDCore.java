package br.com.wilsonpizzas.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {
    private static  final String NOME_BD = "usuario";
    private static  final int VERSAO_BD = 8;

    public BDCore(Context ctx){
        super(ctx,NOME_BD,null, VERSAO_BD);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table usuario(_id integer primary key autoincrement, login text not null, senha text not null);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //RECONSTRUIR SE MUDAR A VERSÃO
        db.execSQL("drop table usuario;");
        onCreate(db);
    }
}
