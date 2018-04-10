package pl.steevit.electronicmusiceventspl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MusicStoppedListener {

    private static final String URL_EVENTS = "http://steevit.pl/android/events/api.php";
    ImageView ivPlayStop;
    String audioLink = "https://dl.dropbox.com/s/08pcby7d4madjec/Blinders%20-%20Snakecharmer.mp3?dl=0";
    boolean musicPlaying = false;
    Intent serviceIntent;
    ListView lvEvents;
    ArrayList<Event> list;
    Event event1, event2, event3, event4, event5, event6, event7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPlayStop = (ImageView) findViewById(R.id.ivPlayStop);
        ivPlayStop.setBackgroundResource(R.mipmap.play);
        serviceIntent = new Intent(this, MyPlayService.class);

        ApplicationClass.context = (Context) MainActivity.this;

        ivPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!musicPlaying)
                {
                    playAudio();
                    ivPlayStop.setImageResource(R.mipmap.stop);
                    musicPlaying = true;
                }
                else
                {
                    stopPlayService();
                    ivPlayStop.setImageResource(R.mipmap.play);
                    musicPlaying = false;
                }

            }
        });

        lvEvents = (ListView) findViewById(R.id.lvEvents);

        list = new ArrayList<Event>();

        loadEvents();



        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Event ev = list.get(i);

                Gson gson = new Gson();
                String eventAsString = gson.toJson(ev);

                Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                intent.putExtra("event", eventAsString);
                startActivity(intent);

            }
        });

    }

    private void stopPlayService()
    {
        try
        {
            stopService(serviceIntent);
        }
        catch (SecurityException e)
        {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void playAudio()
    {
        serviceIntent.putExtra("AudioLink", audioLink);

        try
        {
            startService(serviceIntent);
        }
        catch (SecurityException e)
        {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMusicStopped()
    {
        ivPlayStop.setImageResource(R.mipmap.play);
        musicPlaying = false;
    }

    private void loadEvents() {

        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                            DateFormat format2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject event = array.getJSONObject(i);
                                if(event.getString("date2") != "null") {
                                    Event eventObj = new Event(
                                            event.getInt("id"),
                                            event.getString("name"),
                                            event.getString("location"),
                                            format2.format(format.parse(event.getString("date"))),
                                            format2.format(format.parse(event.getString("date2"))),
                                            event.getString("musicType"),
                                            event.getString("description"),
                                            event.getString("www"),
                                            event.getDouble("lat"),
                                            event.getDouble("lng"),
                                            event.getString("image"));
                                    list.add(eventObj);
                                }
                                else
                                {
                                    Event eventObj = new Event(
                                            event.getInt("id"),
                                            event.getString("name"),
                                            event.getString("location"),
                                            format2.format(format.parse(event.getString("date"))),
                                            event.getString("date2"),
                                            event.getString("musicType"),
                                            event.getString("description"),
                                            event.getString("www"),
                                            event.getDouble("lat"),
                                            event.getDouble("lng"),
                                            event.getString("image"));
                                    list.add(eventObj);
                                }

                            }

                            //creating adapter object and setting it to recyclerview
                            EventAdapter adapter = new EventAdapter(MainActivity.this, list);
                            lvEvents.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}