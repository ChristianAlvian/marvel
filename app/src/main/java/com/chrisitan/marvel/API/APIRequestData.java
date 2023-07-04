package com.chrisitan.marvel.API;

import com.chrisitan.marvel.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrive.php")
    Call<ModelResponse> ardRetrive();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("deskripsi") String deskripsi,
            @Field("nama_asli_pemain") String nama_asli_pemain,
            @Field("film_yang_dimainkan") String film_yang_dimainkan,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("update.php")
    Call <ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("deskripsi") String deskripsi,
            @Field("nama_asli_pemain") String nama_asli_pemain,
            @Field("film_yang_dimainkan") String film_yang_dimainkan,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call <ModelResponse> ardDelete(
            @Field("id") String id
    );
}
