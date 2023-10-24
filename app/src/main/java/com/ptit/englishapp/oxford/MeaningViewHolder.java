package com.ptit.englishapp.oxford;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.englishapp.R;

public class MeaningViewHolder extends RecyclerView.ViewHolder {
    public TextView textView_partsOfSpeech;
    public RecyclerView recycler_definitions;

    public TextView syn, an;
    public MeaningViewHolder(@NonNull View itemView) {
        super(itemView);
        syn = itemView.findViewById(R.id.textView_synonyms);
        an = itemView.findViewById(R.id.textView_antonyms);
        textView_partsOfSpeech = itemView.findViewById(R.id.textView_partsOfSpeech);
        recycler_definitions = itemView.findViewById(R.id.recycler_definitions);
    }

}
