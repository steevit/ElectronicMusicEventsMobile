package pl.steevit.electronicmusiceventspl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class DescriptionActivity extends AppCompatActivity {

    TextView tvMusicType, tvName, tvDate, tvLocation, tvDescription;
    ImageView ivLogo;
    Button btnWwww, btnMap;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Gson gson = new Gson();
        final String eventAsString = getIntent().getStringExtra("event");
        final Event event = gson.fromJson(eventAsString, Event.class);

        tvMusicType = (TextView) findViewById(R.id.tvMusicTypeBig);
        tvName = (TextView) findViewById(R.id.tvNameBig);
        tvDate = (TextView) findViewById(R.id.tvDateBig);
        tvLocation = (TextView) findViewById(R.id.tvLocationBig);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        ivLogo = (ImageView) findViewById(R.id.ivLogoBig);
        btnWwww = (Button) findViewById(R.id.btnWww);
        btnMap = (Button) findViewById(R.id.btnMap);

        tvMusicType.setText(event.getMusicType());
        tvName.setText(event.getName());

        if(!event.getDate2().equals("null")) {
            tvDate.setText(event.getDate() + " - " + event.getDate2());
        }
        else {
            tvDate.setText(event.getDate());
        }

        tvLocation.setText(event.getLocation());
        tvDescription.setText(event.getDescription());

        Glide.with(DescriptionActivity.this)
                .load(event.getImage())
                .into(ivLogo);

        btnWwww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getWww()));
                startActivity(intent);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DescriptionActivity.this, MapsActivity.class);
                intent.putExtra("event", eventAsString);
                startActivity(intent);

            }
        });

    }



}
