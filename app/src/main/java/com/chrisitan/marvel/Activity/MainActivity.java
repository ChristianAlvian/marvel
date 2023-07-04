package com.chrisitan.marvel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chrisitan.marvel.API.APIRequestData;
import com.chrisitan.marvel.API.RetroServer;
import com.chrisitan.marvel.Adapter.AdapterMarvel;
import com.chrisitan.marvel.Model.ModelMarvel;
import com.chrisitan.marvel.Model.ModelResponse;
import com.chrisitan.marvel.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvMarvel;
    private FloatingActionButton fabTambah;
    private ProgressBar pbMarvel;
    private RecyclerView.Adapter adMarvel;
    private RecyclerView.LayoutManager lmMarvel;
    private List<ModelMarvel> listMasjid = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMarvel = findViewById(R.id.rv_marvel);
        fabTambah = findViewById(R.id.fab_tambah);
        pbMarvel = findViewById(R.id.pb_marvel);

        lmMarvel = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvMarvel.setLayoutManager(lmMarvel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retriveMarvel();
    }

    public void retriveMarvel(){
        pbMarvel.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrive();
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listMasjid = response.body().getData();
                adMarvel = new AdapterMarvel(MainActivity.this, listMasjid);
                rvMarvel.setAdapter(adMarvel);
                adMarvel.notifyDataSetChanged();
                pbMarvel.setVisibility(View.GONE);

                fabTambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, AddActivity.class));
                    }
                });
            }


            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbMarvel.setVisibility(View.GONE);
            }

        });
    }
}