package br.com.gedai.bibliotecagedai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "Biblioteca";
    private static int VERSAO = 1;

    public static class Livro {
        public static final String TABELA = "LIVRO";
        public static final String _ID = "_ID";
        public static final String TITULO = "TITULO";
        public static final String AUTOR = "AUTOR";
        public static final String RESUMO = "RESUMO";
        public static final String CLASSIFICACAO = "CLASSIFICACAO";
        public static final String CUTTER = "CUTTER";
        public static final String OBSERVACAO = "OBSERVACAO";
        public static final String PATH_IMAGE = "PATH_IMAGE";
        public static final String AVALIACAO = "AVALIACAO";

        public static final String[] COLUNAS = new String[]{
                _ID, TITULO, AUTOR, RESUMO,
                CLASSIFICACAO, CUTTER, OBSERVACAO, PATH_IMAGE, AVALIACAO};
    }


    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LIVRO (" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TITULO VARCHAR(45) NOT NULL," +
                "AUTOR VARCHAR(45) NOT NULL," +
                "RESUMO VARCHAR(45) NOT NULL," +
                "CLASSIFICACAO VARCHAR(45)," +
                "CUTTER VARCHAR(45)," +
                "OBSERVACAO TEXT," +
                "PATH_IMAGE VARCHAR(125)," +
                "AVALIACAO DOUBLE);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}