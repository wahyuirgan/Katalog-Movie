package top.technopedia.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static top.technopedia.myapplication.BuildConfig.url_image;

public class MyMovieAdapter extends BaseAdapter {
    private ArrayList<MovieList> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mLInflater;
    private String description;

    public MyMovieAdapter (Context context){
        this.context = context;
        mLInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    public void setData (ArrayList<MovieList> items){
        mData = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
         if (mData == null) return 0;
         return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mLInflater.inflate(R.layout.movie_models, null);
          //  holder.lv_movie = (ListView)convertView.findViewById(R.id.lv_movie);
            holder.judul = convertView.findViewById(R.id.judul);
            holder.deskrip = convertView.findViewById(R.id.deskrip);
            holder.rilis = convertView.findViewById(R.id.rilis);
            holder.poster = convertView.findViewById(R.id.poster);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.judul.setText(mData.get(position).getTitle());
        String overview = mData.get(position).getOverview();

        if (TextUtils.isEmpty(overview)){
            description = "Tidak Ditemukan";
        }
        else {
            description = overview;
        }
        holder.deskrip.setText(description);

        String foundDate = mData.get(position).getRelease_date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatOfDate = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = formatOfDate.parse(foundDate);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormatDate =  new SimpleDateFormat("EEEE, dd MMM yyyy");
            String releaseDate = newFormatDate.format(date);
            holder.rilis.setText(releaseDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.with(context).load(url_image + mData.get(position).getPoster_path()).placeholder(context.getResources().getDrawable(R.drawable.ic_photo_black_24dp)).error(context.getResources().getDrawable(R.drawable.ic_photo_black_24dp)).into(holder.poster);
        return convertView;
    }

    private  static class ViewHolder {
        //ListView lv_movie;
        TextView judul,deskrip,rilis;
        ImageView poster;
    }
}
