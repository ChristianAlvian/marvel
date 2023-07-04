package com.chrisitan.marvel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chrisitan.marvel.API.APIRequestData;
import com.chrisitan.marvel.API.RetroServer;
import com.chrisitan.marvel.Activity.DetailActivity;
import com.chrisitan.marvel.Activity.EditActivity;
import com.chrisitan.marvel.Activity.MainActivity;
import com.chrisitan.marvel.Model.ModelMarvel;
import com.chrisitan.marvel.Model.ModelResponse;
import com.chrisitan.marvel.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterMarvel extends RecyclerView.Adapter<AdapterMarvel.VHMarvel>{
    private Context ctx;
    private List<ModelMarvel> listMarvel;

    public AdapterMarvel(Context ctx, List<ModelMarvel> listMarvel) {
        this.ctx = ctx;
        this.listMarvel = listMarvel;
    }

    @NonNull
    @Override
    public VHMarvel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_marvel, parent, false);
        return new VHMarvel(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHMarvel holder, int position) {
        ModelMarvel MM = listMarvel.get(position);
        holder.tvId.setText(MM.getId());
        holder.tvNama.setText(MM.getNama());
        holder.tvFoto.setText(MM.getFoto());
        holder.tvDeskripsi.setText(MM.getDeskripsi());
        holder.tvNamaAsliPemain.setText(MM.getNama_asli_pemain());
        holder.tvFilmYangDimainkan.setText(MM.getFilm_yang_dimainkan());
        Glide
                .with(ctx)
                .load(MM.getFoto())
                .into(holder.ivFoto);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xNama, xFoto,xDeskripsi, xNamaAsliPemain, xFilmYangDimainkan;
                xNama = MM.getNama();
                xFoto = MM.getFoto();
                xDeskripsi = MM.getDeskripsi();
                xNamaAsliPemain = MM.getNama_asli_pemain();
                xFilmYangDimainkan = MM.getFilm_yang_dimainkan();

                Intent kirim = new Intent(ctx, DetailActivity.class);
                kirim.putExtra("xNama",xNama);
                kirim.putExtra("xFoto",xFoto);
                kirim.putExtra("xDeskripsi",xDeskripsi);
                kirim.putExtra("xNamaAsliPemain",xNamaAsliPemain);
                kirim.putExtra("xFilmYangDimainkan",xFilmYangDimainkan);
                ctx.startActivity(kirim);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listMarvel.size();
    }

    public class VHMarvel extends RecyclerView.ViewHolder{
        private TextView tvId,tvNama, tvDeskripsi,tvNamaAsliPemain, tvFilmYangDimainkan, tvFoto;
        private Button btnHapus, btnUbah, btnDetail;
        private ImageView ivFoto;
        public VHMarvel(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvFoto = itemView.findViewById(R.id.tv_foto);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            tvFilmYangDimainkan = itemView.findViewById(R.id.tv_film_yang_dimainkan);
            tvNamaAsliPemain = itemView.findViewById(R.id.tv_nama_asli_pemain);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            btnUbah = itemView.findViewById(R.id.btn_ubah);
            btnDetail = itemView.findViewById(R.id.btn_detail);

            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMarvel(tvId.getText().toString());

                }
            });

            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, EditActivity.class);
                    kirim.putExtra("xId", tvId.getText().toString());
                    kirim.putExtra("xNama", tvNama.getText().toString());
                    kirim.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                    kirim.putExtra("xNamaAsliPemain", tvNamaAsliPemain.getText().toString());
                    kirim.putExtra("xFilmYangDimainkan", tvFilmYangDimainkan.getText().toString());
                    kirim.putExtra("xFoto", tvFoto.getText().toString());
                    ctx.startActivity(kirim);
                }
            });

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        void deleteMarvel(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode:"+kode+"Pesan : "+ pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retriveMarvel();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
