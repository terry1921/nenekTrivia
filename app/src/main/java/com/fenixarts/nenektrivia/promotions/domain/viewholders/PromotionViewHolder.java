package com.fenixarts.nenektrivia.promotions.domain.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.promotions.domain.models.PromotionRow;
import com.fenixarts.nenektrivia.utils.App;
import com.squareup.picasso.Picasso;

/**
 * NenekTrivia
 * Created by terry0022 on 19/02/18 - 17:38.
 */

public class PromotionViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitleView;
    private final ImageView mImageView;
    private final TextView mContentView;

    public PromotionViewHolder(View view) {
        super(view);
        mTitleView = view.findViewById(R.id.title_promotion);
        mImageView = view.findViewById(R.id.image_promotion);
        mContentView = view.findViewById(R.id.content_promotion);
    }

    public void render(PromotionRow row){
        mTitleView.setVisibility(View.GONE);
        mTitleView.setText("");
        mContentView.setVisibility(View.GONE);
        mContentView.setText("");
        mImageView.setVisibility(View.GONE);
        mImageView.setImageResource(R.drawable.background_banner);

        if (!row.getTitle().isEmpty()){
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(row.getTitle());
        }

        if (!row.getContent().isEmpty()){
            mContentView.setVisibility(View.VISIBLE);
            mContentView.setText(row.getContent());
        }

        if (!row.getImage().isEmpty()){
            mImageView.setVisibility(View.VISIBLE);
            Picasso.with(App.getContext()).load(row.getImage()).into(mImageView);
        }
    }

}
