package com.chrisitan.marvel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chrisitan.marvel.R;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNama, tvDeskripsi, tvNamaAsliPemain, tvFilmYangDimainkan;
    private ImageView ivFoto;
    String yFoto ,yNama, yDeskripsi, yNamaAsliPemain, yFilmYangDimainkan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNama = findViewById(R.id.tv_nama);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        tvNamaAsliPemain = findViewById(R.id.tv_nama_asli_pemain);
        tvFilmYangDimainkan = findViewById(R.id.tv_film_yang_dimainkan);
        ivFoto = findViewById(R.id.iv_foto);

        Intent tangkap = getIntent();
        yNama = tangkap.getStringExtra("xNama");
        yFoto = tangkap.getStringExtra("xFoto");
        yDeskripsi = tangkap.getStringExtra("xDeskripsi");
        yNamaAsliPemain = tangkap.getStringExtra("xNamaAsliPemain");
        yFilmYangDimainkan = tangkap.getStringExtra("xFilmYangDimainkan");

        tvNama.setText(yNama);
        tvDeskripsi.setText(yDeskripsi);
        tvNamaAsliPemain.setText(yNamaAsliPemain);
        tvFilmYangDimainkan.setText(yFilmYangDimainkan);
        Glide
                .with(DetailActivity.this)
                .load(yFoto)
                .into(ivFoto);
    }
}