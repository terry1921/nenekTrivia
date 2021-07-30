package com.fenixarts.nenektrivia.bestplayers.domain.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.bestplayers.domain.viewholder.HonorViewHolder;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 12/01/18 - 14:17.
 */

public class HonorAdapter extends RecyclerView.Adapter<HonorViewHolder>{

    private List<BestPlayersItem> list;

    public HonorAdapter() {
        list = Lists.newArrayList();
    }

    @NonNull
    @Override
    public HonorViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_best_player, parent, false);
        return new HonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HonorViewHolder holder, int position) {
        BestPlayersItem item = list.get(position);
        holder.render(item, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateCollection(final Collection<BestPlayersItem> collection) {
        list.clear();
        list.addAll(checkNotNull(collection));
        notifyDataSetChanged();
    }
}
