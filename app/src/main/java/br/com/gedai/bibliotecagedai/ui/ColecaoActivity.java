package br.com.gedai.bibliotecagedai.ui;

/**
 * Created by GTI-366739 on 16/11/2015.
 */
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import br.com.gedai.bibliotecagedai.service.BancoService;
import br.com.gedai.bibliotecagedai.model.Livro;
import br.com.gedai.bibliotecagedai.adapter.LivroAdapter;

public class ColecaoActivity extends ListActivity {

    private List<Livro> livros;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BancoService bancoService = new BancoService();
        livros = bancoService.getAllLivros(getApplicationContext());
        setListAdapter(new LivroAdapter(this, livros));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Livro livro = livros.get(position);

        Intent intent = new Intent(this, DetalheItemActivity.class);
        intent.putExtra("livro", livro);
        startActivity(intent);
    }

}