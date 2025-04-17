package com.example.alquran.api;

import com.example.alquran.model.SurahDetailResponse;
import com.example.alquran.model.SurahResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("v1/surah")
    Call<SurahResponse> getSurahList();

    @GET("v1/surah/{surah_number}/editions/quran-uthmani,id.indonesian")
    Call<SurahDetailResponse> getSurahDetail(@Path("surah_number") int surahNumber);
}