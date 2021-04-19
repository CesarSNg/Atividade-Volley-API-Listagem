package br.com.local.appjsonvolleyparserequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.*;

public class MainActivity extends AppCompatActivity {
    private RecyclerView txtRec;
    private Button btnParse;
    private RequestQueue mQueue;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRec = findViewById(R.id.txtRec);
        btnParse = findViewById(R.id.btnParse);

        mQueue = Volley.newRequestQueue(this);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        String url = "http://192.168.0.105/heroapi/v1/Api.php?apicall=getheroes";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("heroes");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject heroe = jsonArray.getJSONObject(i);

                                int id = heroe.getInt("id");
                                String name = heroe.getString("name");
                                String realname = heroe.getString("realname");
                                int rating = heroe.getInt("rating");
                                String teamaffiliation = heroe.getString("teamaffiliation");

                                txtRec.addView(format("%s - %s - %s - %s\n\n", name, realname, valueOf(rating), teamaffiliation));
                                //txtRec.append(name + " - " + realname + " - " + String.valueOf(rating) + " - " + teamaffiliation + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}