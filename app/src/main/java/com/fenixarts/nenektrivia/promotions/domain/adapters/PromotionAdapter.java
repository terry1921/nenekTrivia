package com.fenixarts.nenektrivia.promotions.domain.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.promotions.domain.models.PromotionRow;
import com.fenixarts.nenektrivia.promotions.domain.viewholders.PromotionViewHolder;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * NenekTrivia
 * Created by terry0022 on 19/02/18 - 17:44.
 */

public class PromotionAdapter extends RecyclerView.Adapter<PromotionViewHolder> {

    private List<PromotionRow> list;

    public PromotionAdapter() {
        list = Lists.newArrayList();
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_promotion, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PromotionViewHolder holder, int position) {
        PromotionRow row = list.get(position);
        holder.render(row);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<PromotionRow> collection){
        list.clear();
        list.addAll(collection);
        notifyDataSetChanged();
    }

}
