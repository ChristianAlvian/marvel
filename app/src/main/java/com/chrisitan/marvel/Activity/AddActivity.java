package com.chrisitan.marvel.Activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddActivity extends AppCompatActivity {
    private EditText etNama, etDeskripsi, etNamaPemainAsli, etFilmYangDimainkan, etFoto;
    private Button btn_tambah;
    private String Nama, Deskripsi, NamaPemainAsli, FilmYangDimainkan,Foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etNama = findViewById(R.id.et_nama);
        etFoto = findViewById(R.id.et_foto);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etNamaPemainAsli = findViewById(R.id.et_nama_pemain_asli);
        etFilmYangDimainkan = findViewById(R.id.et_film_yang_dimainkan);
        btn_tambah = findViewById(R.id.btn_tambah);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNama.getText().toString();
                Foto = etFoto.getText().toString();
                Deskripsi = etDeskripsi.getText().toString();
                NamaPemainAsli = etNamaPemainAsli.getText().toString();
                FilmYangDimainkan = etFilmYangDimainkan.getText().toString();

                if (Nama.trim().isEmpty()){
                    etNama.setError("Nama harus di isi");
                }else if (Foto.trim().isEmpty()){
                    etFoto.setError("Link foto harus di isi");
                } else if (Deskripsi.trim().isEmpty()) {
                    etDeskripsi.setError("Deskripsi harus di isi");
                } else if (FilmYangDimainkan.trim().isEmpty()) {
                    etFilmYangDimainkan.setError("Film yang dimainkan harus di isi");
                } else if (NamaPemainAsli.trim().isEmpty()) {
                    etNamaPemainAsli.setError("Nama pemain asli harus di isi");
                } else {
                    tambahMarvel();
                }
            }
        });
    }
    private void tambahMarvel(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(Nama, Deskripsi, NamaPemainAsli, FilmYangDimainkan,Foto);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();
                Toast.makeText(AddActivity.this, "kode : " + kode + "Pesan" + pesan , Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(AddActivity.this, "Gagal simpan data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}