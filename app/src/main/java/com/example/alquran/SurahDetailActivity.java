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
import com.example.alquran.util.MediaPlayerManager;
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_ayah);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int surahNumber = getIntent().getIntExtra("SURAH_NUMBER", 1);
        fetchSurahDetail(surahNumber);
    }

    private void fetchSurahDetail(int surahNumber) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<SurahDetailResponse> callText = apiService.getSurahDetail(surahNumber);
        Call<SurahDetailResponse> callAudio = apiService.getSurahDetailWithAudio(surahNumber);

        callText.enqueue(new Callback<SurahDetailResponse>() {
            @Override
            public void onResponse(Call<SurahDetailResponse> call, Response<SurahDetailResponse> responseText) {
                if (responseText.isSuccessful() && responseText.body() != null && responseText.body().getData() != null) {
                    List<Ayah> arabicAyahs = responseText.body().getData().get(0).getAyahs();
                    List<Ayah> indoAyahs = responseText.body().getData().get(1).getAyahs();

                    callAudio.enqueue(new Callback<SurahDetailResponse>() {
                        @Override
                        public void onResponse(Call<SurahDetailResponse> call, Response<SurahDetailResponse> responseAudio) {
                            progressBar.setVisibility(View.GONE);
                            if (responseAudio.isSuccessful() && responseAudio.body() != null && responseAudio.body().getData() != null) {
                                List<Ayah> audioAyahs = responseAudio.body().getData().get(1).getAyahs();

                                ayahList.clear();
                                final String BISMILLAH = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ";
                                final String BISMILLAH_INDO = "Dengan nama Allah Yang Maha Pengasih, Maha Penyayang.";
                                boolean bismillahAdded = false;

                                if (surahNumber != 1 && surahNumber != 9 && !arabicAyahs.isEmpty()) {
                                    Ayah firstArabic = arabicAyahs.get(0);
                                    Ayah firstIndo = indoAyahs.get(0);

                                    // Hapus Bismillah dengan regex agar lebih fleksibel
                                    String arab1 = firstArabic.getText().replaceAll("\\s+", "");
                                    String bismillahClean = BISMILLAH.replaceAll("\\s+", "");
                                    if (arab1.startsWith(bismillahClean)) {
                                        // Tambahkan Bismillah sebagai ayat 0
                                        Ayah bismillahAyah = new Ayah();
                                        bismillahAyah.setNumber(0);
                                        bismillahAyah.setNumberInSurah(0);
                                        bismillahAyah.setText(BISMILLAH);
                                        bismillahAyah.setTransliteration("");
                                        bismillahAyah.setTranslation(""); // Atau BISMILLAH_INDO jika ingin tampil
                                        bismillahAyah.setAudio("");
                                        ayahList.add(bismillahAyah);

                                        // Hapus Bismillah dari ayat 1 Arab
                                        firstArabic.setText(firstArabic.getText().replaceFirst(BISMILLAH, "").trim());
                                        // Hapus Bismillah dari ayat 1 Indo
                                        if (firstIndo.getText().contains(BISMILLAH_INDO)) {
                                            firstIndo.setText(firstIndo.getText().replaceFirst(BISMILLAH_INDO, "").trim());
                                        }
                                        bismillahAdded = true;
                                    }
                                }

                                for (int i = 0; i < arabicAyahs.size(); i++) {
                                    Ayah ayah = new Ayah();
                                    ayah.setNumber(bismillahAdded ? i + 1 : arabicAyahs.get(i).getNumber());
                                    ayah.setNumberInSurah(bismillahAdded ? i + 1 : arabicAyahs.get(i).getNumberInSurah());
                                    ayah.setText(arabicAyahs.get(i).getText());
                                    ayah.setTransliteration(arabicAyahs.get(i).getTransliteration());
                                    ayah.setTranslation(indoAyahs.get(i).getText());
                                    ayah.setAudio(audioAyahs.get(i).getAudio());
                                    ayahList.add(ayah);
                                }

                                adapter = new AyahAdapter(SurahDetailActivity.this, ayahList);
                                recyclerView.setAdapter(adapter);
                            } else {
                                Toast.makeText(SurahDetailActivity.this, "Failed to load audio", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SurahDetailResponse> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SurahDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SurahDetailActivity.this, "Failed to load text data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SurahDetailResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SurahDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerManager.stop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}