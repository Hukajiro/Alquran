package com.example.alquran;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alquran.adapter.AyahAdapter;
import com.example.alquran.api.ApiClient;
import com.example.alquran.api.ApiService;
import com.example.alquran.model.Ayah;
import com.example.alquran.model.SurahDetailResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class SurahDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AyahAdapter adapter;
    private List<Ayah> ayahList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_detail);

        recyclerView = findViewById(R.id.rv_ayah);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int surahNumber = getIntent().getIntExtra("SURAH_NUMBER", 1);
        fetchSurahDetail(surahNumber);
    }

    private void fetchSurahDetail(int surahNumber) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SurahDetailResponse> call = apiService.getSurahDetail(surahNumber);
        call.enqueue(new Callback<SurahDetailResponse>() {
            @Override
            public void onResponse(Call<SurahDetailResponse> call, Response<SurahDetailResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    ayahList.clear();
                    List<Ayah> arabicAyahs = response.body().getData().get(0).getAyahs();
                    List<Ayah> indoAyahs = response.body().getData().get(1).getAyahs();

                    if (arabicAyahs != null && indoAyahs != null && arabicAyahs.size() == indoAyahs.size()) {
                        for (int i = 0; i < arabicAyahs.size(); i++) {
                            Ayah ayah = new Ayah();
                            ayah.setNumberInSurah(arabicAyahs.get(i).getNumberInSurah());
                            ayah.setText(arabicAyahs.get(i).getText());
                            ayah.setTransliteration(arabicAyahs.get(i).getTransliteration());
                            ayah.setTranslation(indoAyahs.get(i).getText());
                            ayahList.add(ayah);
                        }

                        adapter = new AyahAdapter(SurahDetailActivity.this, ayahList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(SurahDetailActivity.this, "Data ayat tidak valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SurahDetailActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SurahDetailResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SurahDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}