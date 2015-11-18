package br.com.gedai.bibliotecagedai.ui;

/**
 * Created by GTI-366739 on 16/11/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MinhaBibliotecaActivity extends Activity {

    private ListView listaOpcoesMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(br.com.gedai.bibliotecagedai.R.layout.activity_minha_biblioteca);

        listaOpcoesMenu = (ListView) findViewById(br.com.gedai.bibliotecagedai.R.id.lista_menu);
        String[] values = new String[] { "Minha coleção", "Adicionar" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listaOpcoesMenu.setAdapter(adapter);

        listaOpcoesMenu.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition = position;

                if (itemPosition == 0) {
                    Intent intent = new Intent(MinhaBibliotecaActivity.this, ColecaoActivity.class);
                    startActivity(intent);
                }

                if (itemPosition == 1) {
                    Intent intent = new Intent(MinhaBibliotecaActivity.this, NovoItemActivity.class);
                    startActivity(intent);
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(br.com.gedai.bibliotecagedai.R.menu.minha_biblioteca, menu);
        return true;
    }

}

