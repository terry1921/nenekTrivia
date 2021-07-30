package com.fenixarts.nenektrivia.bestplayers.domain.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.utils.App;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * NenekTrivia
 * Created by terry0022 on 12/01/18 - 14:18.
 */

public class HonorViewHolder extends RecyclerView.ViewHolder{

    private ImageView image;
    private TextView username;
    private TextView place;
    private TextView points;

    public HonorViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        username = itemView.findViewById(R.id.username);
        place = itemView.findViewById(R.id.place);
        points = itemView.findViewById(R.id.points);
    }

    public void render(final BestPlayersItem item, int position) {
        Picasso.with(App.getContext()).load(item.getImage()).into(image);
        username.setText(item.getUsername());
        place.setText(String.format(Locale.US, "%d.º "+ App.getContext().getString(R.string.place), position + 1));
        points.setText(String.format(Locale.US, "%d pts", item.getPoints()));
    }

}
