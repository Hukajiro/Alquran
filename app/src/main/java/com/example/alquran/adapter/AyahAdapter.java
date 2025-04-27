package com.example.alquran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alquran.R;
import com.example.alquran.model.Ayah;
import com.example.alquran.util.MediaPlayerManager;
import java.util.List;

public class AyahAdapter extends RecyclerView.Adapter<AyahAdapter.ViewHolder> {
    private List<Ayah> ayahList;
    private Context context;

    public AyahAdapter(Context context, List<Ayah> ayahList) {
        this.context = context;
        this.ayahList = ayahList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ayah, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ayah ayah = ayahList.get(position);
        if (ayah.getNumberInSurah() == 0) {
            holder.tvAyahNumber.setText("");
            holder.tvArabicText.setText(ayah.getText());
            holder.tvArabicText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.tvArabicText.setTextSize(24);
            holder.tvArabicText.setTypeface(null, android.graphics.Typeface.BOLD);
            holder.tvArabicText.setBackgroundResource(R.color.colorAccent);
            holder.tvTransliteration.setText("");
            holder.tvTranslation.setText("");
            holder.btnPlayAudio.setVisibility(View.GONE);
        } else {
            holder.tvAyahNumber.setText(String.valueOf(ayah.getNumberInSurah()));
            holder.tvArabicText.setText(ayah.getText());
            holder.tvArabicText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            holder.tvArabicText.setTextSize(20);
            holder.tvArabicText.setTypeface(null, android.graphics.Typeface.NORMAL);
            holder.tvArabicText.setBackgroundResource(0);
            holder.tvTransliteration.setText(ayah.getTransliteration());
            holder.tvTranslation.setText(ayah.getTranslation());
            holder.btnPlayAudio.setVisibility(View.VISIBLE);
        }

        holder.btnPlayAudio.setOnClickListener(v -> {
            String audioUrl = ayah.getAudio();
            if (audioUrl != null && !audioUrl.isEmpty()) {
                MediaPlayerManager.play(context, audioUrl);
            } else {
                Toast.makeText(context, "Audio tidak tersedia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ayahList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAyahNumber, tvArabicText, tvTransliteration, tvTranslation;
        ImageButton btnPlayAudio;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAyahNumber = itemView.findViewById(R.id.tv_ayah_number);
            tvArabicText = itemView.findViewById(R.id.tv_arabic_text);
            tvTransliteration = itemView.findViewById(R.id.tv_transliteration);
            tvTranslation = itemView.findViewById(R.id.tv_translation);
            btnPlayAudio = itemView.findViewById(R.id.btn_play_audio);
        }
    }
}