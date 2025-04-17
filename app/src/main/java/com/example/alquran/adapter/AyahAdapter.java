package com.example.alquran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alquran.R;
import com.example.alquran.model.Ayah;
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
        holder.tvAyahNumber.setText(String.valueOf(ayah.getNumberInSurah()));
        holder.tvArabicText.setText(ayah.getText());
        holder.tvTransliteration.setText(ayah.getTransliteration());
        holder.tvTranslation.setText(ayah.getTranslation());
    }

    @Override
    public int getItemCount() {
        return ayahList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAyahNumber, tvArabicText, tvTransliteration, tvTranslation;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAyahNumber = itemView.findViewById(R.id.tv_ayah_number);
            tvArabicText = itemView.findViewById(R.id.tv_arabic_text);
            tvTransliteration = itemView.findViewById(R.id.tv_transliteration);
            tvTranslation = itemView.findViewById(R.id.tv_translation);
        }
    }
}