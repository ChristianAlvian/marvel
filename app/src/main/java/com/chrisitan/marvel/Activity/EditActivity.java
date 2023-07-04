package com.chrisitan.marvel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chrisitan.marvel.API.APIRequestData;
import com.chrisitan.marvel.API.RetroServer;
import com.chrisitan.marvel.Model.ModelResponse;
import com.chrisitan.marvel.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {
    private EditText etNama, etDeskripsi, etNamaAsliPemain, etFilmYangDimainkan, etFoto;
    private Button btn_ubah;
    private String Nama, Deskripsi, NamaAsliPemain, FilmYangDimainkan, Foto;
    private String yId,yNama, yDeskripsi, yNamaAsliPemain, yFilmYangDimainkan, yFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etNama = findViewById(R.id.et_nama);
        etFoto = findViewById(R.id.et_foto);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etNamaAsliPemain = findViewById(R.id.et_nama_pemain_asli);
        etFilmYangDimainkan = findViewById(R.id.et_film_yang_dimainkan);
        btn_ubah = findViewById(R.id.btn_ubah);

        Intent tangkap = getIntent();
        yId = tangkap.getStringExtra("xId");
        yNama = tangkap.getStringExtra("xNama");
        yDeskripsi = tangkap.getStringExtra("xDeskripsi");
        yNamaAsliPemain = tangkap.getStringExtra("xNamaAsliPemain");
        yFilmYangDimainkan = tangkap.getStringExtra("xFilmYangDimainkan");
        yFoto = tangkap.getStringExtra("xFoto");

        etNama.setText(yNama);
        etFoto.setText(yFoto);
        etDeskripsi.setText(yDeskripsi);
        etNamaAsliPemain.setText(yNamaAsliPemain);
        etFilmYangDimainkan.setText(yFilmYangDimainkan);

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNama.getText().toString();
                Foto = etFoto.getText().toString();
                Deskripsi = etDeskripsi.getText().toString();
                NamaAsliPemain = etNamaAsliPemain.getText().toString();
                FilmYangDimainkan = etFilmYangDimainkan.getText().toString();

                if (Nama.trim().isEmpty()){
                    etNama.setError("Nama harus di isi");
                }else if (Foto.trim().isEmpty()){
                    etFoto.setError("Link foto harus di isi");
                } else if (Deskripsi.trim().isEmpty()) {
                    etDeskripsi.setError("Deskripsi harus di isi");
                } else if (FilmYangDimainkan.trim().isEmpty()) {
                    etFilmYangDimainkan.setError("Film yang dimainkan harus di isi");
                } else if (NamaAsliPemain.trim().isEmpty()) {
                    etNamaAsliPemain.setError("Nama pemain asli harus di isi");
                } else {
                    ubahMarvel();
                }
            }
        });
    }

    private void ubahMarvel(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, Nama, Deskripsi, NamaAsliPemain, FilmYangDimainkan,Foto);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();

                Toast.makeText(EditActivity.this, "kode : " + kode +" Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Error: Gagal ketika ubah data! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}