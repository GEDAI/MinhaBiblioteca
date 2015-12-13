package br.com.gedai.bibliotecagedai.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.gedai.bibliotecagedai.DatabaseHelper;
import br.com.gedai.bibliotecagedai.model.Livro;

/**
 * Created by ricardowerlang on 12/9/15.
 */
public class BibliotecaDAO {

    private DatabaseHelper helper;

    private SQLiteDatabase db;

    public BibliotecaDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDb() {
        if (db == null) {
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public void close() {
        helper.close();
    }

    public List<Livro> listarLivros() {
        Cursor cursor = getDb().query(DatabaseHelper.Livro.TABELA,
                DatabaseHelper.Livro.COLUNAS,
                null, null, null, null, null);
        List<Livro> livros = new ArrayList<Livro>();
        while (cursor.moveToNext()) {
            Livro livro = criarLivro(cursor);
            livros.add(livro);
        }
        cursor.close();
        return livros;
    }

    public Livro buscarLivroPorId(Long id) {
        Cursor cursor = getDb().query(DatabaseHelper.Livro.TABELA,
                DatabaseHelper.Livro.COLUNAS,
                DatabaseHelper.Livro._ID + " = ?",
                new String[]{id.toString()},
                null, null, null);
        if (cursor.moveToNext()) {
            Livro livro = criarLivro(cursor);
            cursor.close();
            return livro;
        }

        return null;
    }

    public long inserir(Livro livro) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.Livro.TITULO,
                livro.getTitulo());

        values.put(DatabaseHelper.Livro.AUTOR,
                livro.getAutor());

        values.put(DatabaseHelper.Livro.RESUMO,
                livro.getResumo());

        values.put(DatabaseHelper.Livro.CLASSIFICACAO,
                livro.getClassificacao());

        values.put(DatabaseHelper.Livro.CUTTER,
                livro.getCutter());

        values.put(DatabaseHelper.Livro.OBSERVACAO,
                livro.getObservacao());

        values.put(DatabaseHelper.Livro.PATH_IMAGE,
                livro.getPathImagem());

        values.put(DatabaseHelper.Livro.AVALIACAO,
                livro.getAvaliacao());

        return getDb().insert(DatabaseHelper.Livro.TABELA,
                null, values);
    }

    public long atualizar(Livro livro, Long id) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.Livro.TITULO,
                livro.getTitulo());

        values.put(DatabaseHelper.Livro.AUTOR,
                livro.getAutor());

        values.put(DatabaseHelper.Livro.RESUMO,
                livro.getResumo());

        values.put(DatabaseHelper.Livro.CLASSIFICACAO,
                livro.getClassificacao());

        values.put(DatabaseHelper.Livro.CUTTER,
                livro.getCutter());

        values.put(DatabaseHelper.Livro.OBSERVACAO,
                livro.getObservacao());

        values.put(DatabaseHelper.Livro.PATH_IMAGE,
                livro.getPathImagem());

        values.put(DatabaseHelper.Livro.AVALIACAO,
                livro.getAvaliacao());

        return getDb().update(DatabaseHelper.Livro.TABELA,
                values, DatabaseHelper.Livro._ID + " = " + id, null);
    }

    public boolean removerLivro(Long id) {
        String whereClause = DatabaseHelper.Livro._ID + " = ?";
        String[] whereArgs = new String[]{id.toString()};
        int removidos = getDb().delete(DatabaseHelper.Livro.TABELA,
                whereClause, whereArgs);
        return removidos > 0;
    }

    private Livro criarLivro(Cursor cursor) {
        Livro livro = new Livro(

                cursor.getLong(cursor.getColumnIndex(
                        DatabaseHelper.Livro._ID)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Livro.TITULO)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Livro.AUTOR)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Livro.RESUMO)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Livro.CLASSIFICACAO)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Livro.CUTTER)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Livro.OBSERVACAO)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.Livro.PATH_IMAGE)),

                cursor.getDouble(cursor.getColumnIndex(
                        DatabaseHelper.Livro.AVALIACAO))
        );
        return livro;
    }

}
