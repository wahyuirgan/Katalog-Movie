package top.technopedia.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static top.technopedia.myapplication.BuildConfig.url_image;

public class DetailMovieActivity extends AppCompatActivity {
    ImageView posterutm,poster;
    TextView judul,rilis,tglrilis, rating,Sinopsis, detail,jdlats, bhsasl;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        MovieList content = getIntent().getParcelableExtra("MOVIE");

        assert getSupportActionBar()!=null;
        getSupportActionBar().setTitle(content.getTitle());

        posterutm = findViewById(R.id.posterutm);
        poster = findViewById(R.id.poster);
        judul =  findViewById(R.id.judul);
        rilis = findViewById(R.id.rilis);
        tglrilis = findViewById(R.id.tglrilis);
        rating = findViewById(R.id.rating);
        Sinopsis = findViewById(R.id.sinopsis);
        detail = findViewById(R.id.detail);
        jdlats = findViewById(R.id.jdlats);
        bhsasl =  findViewById(R.id.bhsasl);

        Picasso.with(this).load(url_image + content.getBackdrop_path()).placeholder(this.getResources().getDrawable(R.drawable.ic_photo_black_24dp)).error(this.getResources().getDrawable(R.drawable.ic_photo_black_24dp)).into(posterutm);
        Picasso.with(this).load(url_image + content.getPoster_path()).into(poster);
        judul.setText(content.getTitle());
        jdlats.setText(content.getTitle());


        String foundDate = content.getRelease_date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatOfDate = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = formatOfDate.parse(foundDate);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormatDate =  new SimpleDateFormat("EEEE, dd MMM yyyy");
            String releaseDate = newFormatDate.format(date);
            tglrilis.setText(releaseDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        rating.setText(content.getVote_average() + "/10");
        detail.setText(content.getOverview());
        bhsasl.setText(content.getOriginal_language());


    }
}
