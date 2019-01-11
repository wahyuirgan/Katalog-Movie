package top.technopedia.myapplication;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieList>> {
    EditText tmpcari;
    Button cari;
    ListView listfilm;
    TextView hasil;
    ProgressBar progressbar;
    MyMovieAdapter adapter;

    static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    String result = "Menampilkan ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MyMovieAdapter(this);
        adapter.notifyDataSetChanged();

        listfilm = findViewById(R.id.listfilm);
        listfilm.setAdapter(adapter);

        listfilm.setOnItemClickListener(new AdapterView.OnItemClickListener() {   //onclick pada listView
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieList item = (MovieList)parent.getItemAtPosition(position);

                Intent detailIntent = new Intent(MainActivity.this, DetailMovieActivity.class);
                detailIntent.putExtra("MOVIE",item);
                startActivity(detailIntent);


            }
        });

        tmpcari = findViewById(R.id.tmpcari);
        cari = findViewById(R.id.cari);

        cari.setOnClickListener(btnSearchListener);
        hasil = findViewById(R.id.hasil);
        progressbar = findViewById(R.id.progressbar);

        String movieTitle = tmpcari.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE, movieTitle);

        getLoaderManager().initLoader(0, bundle, this);



    }

    @SuppressLint("SetTextI18n")
    public Loader<ArrayList<MovieList>> onCreateLoader (int i, Bundle bundle){
        String MovieTitle = "";
        if (bundle != null){
            //noinspection deprecation
            MovieTitle = bundle.getString(EXTRA_MOVIE);
            hasil.setText(result +"Pencarian : " + MovieTitle);
            assert MovieTitle != null;
            if (MovieTitle.isEmpty()){
                hasil.setText(result +": Film Populer");
            }

        }

        progressbar.setVisibility(View.VISIBLE);
        if (progressbar.getVisibility() == View.VISIBLE){
            listfilm.setVisibility(View.GONE);
            hasil.setVisibility(View.GONE);
        }
        return new MyMovieAsyncTaskLoader(this, MovieTitle);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieList>> loader, ArrayList<MovieList> movieLists) {
        adapter.setData(movieLists);
        progressbar.setVisibility(View.GONE);
        if (progressbar.getVisibility() == View.GONE){
            listfilm.setVisibility(View.VISIBLE);
            hasil.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieList>> loader) {
        adapter.setData(null);

    }

    View.OnClickListener btnSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String movieTitle = tmpcari.getText().toString();
            if (TextUtils.isEmpty(movieTitle)){
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_MOVIE, movieTitle);
            getLoaderManager().restartLoader(0,bundle,MainActivity.this);
        }
    };
}
