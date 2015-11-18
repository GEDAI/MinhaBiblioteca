package br.com.gedai.bibliotecagedai.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import br.com.gedai.bibliotecagedai.model.Livro;

/**
 * Created by GTI-366739 on 16/11/2015.
 */
public class BancoService {
    public List<Livro> getAllLivros(Context context) {

        Banco banco = new Banco(context, "banco", null, 1);

        Cursor cursor = banco.getWritableDatabase().query(
                "livro",
                new String[] { "id_livro", "titulo", "autor", "resumo",
                        "path_imagem", "avaliacao" }, null, null, null, null,
                null);

        List<Livro> livros = new ArrayList<Livro>();

        while (cursor.moveToNext()) {

            Livro livro = new Livro();
            livro.setTitulo(cursor.getString(1));
            livro.setAutor(cursor.getString(2));
            livro.setResumo(cursor.getString(3));
            livro.setPathImagem(cursor.getString(4));
            livro.setAvaliacao(cursor.getDouble(5));

            livros.add(livro);
        }

        cursor.close();
        banco.close();

        return livros;
    }

    public void salvarLivro(Livro livro, Context context) {

        try {

            Banco banco = new Banco(context, "banco", null, 1);

            ContentValues contentValues = new ContentValues();
            contentValues.put("titulo", livro.getTitulo());
            contentValues.put("autor", livro.getAutor());
            contentValues.put("resumo", livro.getResumo());
            contentValues.put("avaliacao", livro.getAvaliacao());
            contentValues.put("path_imagem", livro.getPathImagem());
            banco.getWritableDatabase().insert("livro", null, contentValues);

            Toast toast = Toast.makeText(context,
                    "Livro cadastrado com sucesso!!", 5);
            toast.show();

            banco.close();

        } catch (Exception e) {
            Toast toast = Toast.makeText(context,
                    "Erro ao salvar informações no banco!", 5);
            toast.show();
        }
    }
}
