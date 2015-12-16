package br.com.gedai.bibliotecagedai.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import br.com.gedai.bibliotecagedai.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ColecaoFragment.OnColecaoSelectedListener,
        DetalheItemFragment.OnDetalheItemListener, LivroFragment.OnLivroListener {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = null;
        Class fragmentClass = null;
        fragment = new ColecaoFragment();
        fragmentClass = fragment.getClass();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        this.fab = (FloatingActionButton) findViewById(R.id.fab);

        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LivroFragment novoFragment = new LivroFragment();

                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, novoFragment);

                transaction.addToBackStack(null);

                transaction.commit();

                fab.hide();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        final int id = item.getItemId();

        Fragment fragment = null;

        Class fragmentClass = null;

        if (id == R.id.nav_colecao) {
            fragment = new ColecaoFragment();
            fragmentClass = fragment.getClass();
            this.fab.show();

        } else if (id == R.id.nav_sobre) {
            fragment = new SobreFragment();
            fragmentClass = fragment.getClass();

        } else if (id == R.id.nav_sair) {
            System.exit(0);
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void onLivroSelected(int position, long livro_id) {

        final DetalheItemFragment novoFragment = new DetalheItemFragment();

        final Bundle args = new Bundle();

        args.putInt(DetalheItemFragment.ARG_POSITION, position);
        args.putLong(DetalheItemFragment.ARG_LIVRO_ID, livro_id);

        novoFragment.setArguments(args);

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, novoFragment);

        transaction.addToBackStack(null);

        transaction.commit();

        this.fab.hide();
    }

    @Override
    public void onLivroEditarSelected(long livro_id) {

        final LivroFragment novoFragment = new LivroFragment();

        final Bundle args = new Bundle();

        args.putLong(LivroFragment.ARG_LIVRO_ID, livro_id);

        novoFragment.setArguments(args);

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, novoFragment);

        transaction.addToBackStack(null);

        transaction.commit();

        this.fab.hide();
    }

    @Override
    public void onLivroRemoverSelected() {

        final ColecaoFragment novoFragment = new ColecaoFragment();

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, novoFragment);

        transaction.addToBackStack(null);

        transaction.commit();

        this.fab.show();
    }

    @Override
    public void onLivroSalvarSelected() {

        final ColecaoFragment novoFragment = new ColecaoFragment();

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, novoFragment);

        transaction.addToBackStack(null);

        transaction.commit();

        fab.show();
    }
}
