package com.fenixarts.nenektrivia.game.domain.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.game.domain.models.Answers;
import com.fenixarts.nenektrivia.game.domain.viewholder.QuestionViewHolder;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;

/**
 * NenekTrivia
 * Created by terry0022 on 17/01/18 - 18:21.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder>{

    private final QuestionListener listener;
    private ArrayList<Answers> list;
    private int correct;

    public interface QuestionListener{

        void onClickQuestionListener(Answers question, View view, int correct);

    }

    public QuestionAdapter(QuestionListener listener) {
        this.listener = listener;
        list = Lists.newArrayList();
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_answers, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        Answers item = list.get(position);
        holder.render(item, listener, correct);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addCollection(final Collection<Answers> collection){
        list.clear();
        list.addAll(collection);
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).isCorrect()){
                correct = i;
            }
        }
        notifyDataSetChanged();
    }

}
