package pl.steevit.electronicmusiceventspl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by steev on 24.11.2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {
    private final Context context;
    private final ArrayList<Event> values;


    public EventAdapter(Context context, ArrayList<Event> list) {

        super(context, R.layout.row_layout, list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        TextView tvMusicType = (TextView) rowView.findViewById(R.id.tvMusicType);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvDate = (TextView) rowView.findViewById(R.id.tvDate);
        TextView tvLocation = (TextView) rowView.findViewById(R.id.tvLocation);

        ImageView ivLogo = (ImageView) rowView.findViewById(R.id.ivLogo);

        tvMusicType.setText(values.get(position).getMusicType());
        tvName.setText(values.get(position).getName());
        tvDate.setText(values.get(position).getDate().toString());
        tvLocation.setText(values.get(position).getLocation());

        Glide.with(context)
                .load(values.get(position).getImage())
                .into(ivLogo);

        return rowView;
    }

}