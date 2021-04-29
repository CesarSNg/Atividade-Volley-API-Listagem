package br.com.local.appjsonvolleyparserequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txtRec;
    private Button btnParse;
    private RequestQueue mQueue;

    //RecyclerView recyclerView;
    //RecyclerView.Adapter adapter;

    //List<ListarItens> itensList;

    //String url = "https://swapi.dev/api/films/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*recyclerView = findViewById(R.id.idRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itensList = new ArrayList<>();
        carregaDadosRecyclerView();*/

        btnParse = findViewById(R.id.btnParse);

        mQueue = Volley.newRequestQueue(this);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    /*private void carregaDadosRecyclerView() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando Dados...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                ListarItens itens = new ListarItens(
                                        object.getString("title"),
                                        object.getString("opening_crawl")
                                );
                                itensList.add(itens);
                            }

                            adapter = new MyAdapter(itensList, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/

    private void jsonParse() {
        ListView list = (ListView) findViewById(R.id.txtRec);
        ArrayList<String> listItems=new ArrayList<String>();

        ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        list.setAdapter(adapter);

        String url = "https://rest-api-csng-games.herokuapp.com/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listItems.add("Personagens" + "\n\n");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject personagens = response.getJSONObject(i);

                                int id = personagens.getInt("id");
                                String name = personagens.getString("nome");
                                String x = personagens.getString("X");
                                int y = personagens.getInt("Y");

                                listItems.add
                                        ("Nome: " + name + "\n\n"
                                                + "X: " + x + " \n\n "
                                                + "Y: " + String.valueOf(y)
                                                + " \n\n " +  "\n\n");
                                adapter.notifyDataSetChanged();

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