package com.fenixarts.nenektrivia.game.domain.viewholder;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.game.domain.adapters.QuestionAdapter;
import com.fenixarts.nenektrivia.game.domain.models.Answers;
import com.fenixarts.nenektrivia.utils.App;

/**
 * NenekTrivia
 * Created by terry0022 on 17/01/18 - 18:21.
 */

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    private final View container;
    private View itemView;
    private TextView answer;
    private TextView idQuestion;

    public QuestionViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        answer = itemView.findViewById(R.id.answer);
        idQuestion = itemView.findViewById(R.id.id_question);
        container = itemView.findViewById(R.id.container);
    }

    public void render(final Answers item, final QuestionAdapter.QuestionListener listener,final int correct) {
        container.setBackgroundResource(R.drawable.button_answer_game);
        answer.setTextColor(ColorStateList.valueOf(App.getContext().getResources().getColor(R.color.textColorSecondary)));
        answer.setText(item.getAnswer());
        idQuestion.setText(item.getId());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickQuestionListener(item, view, correct);
            }
        });
    }

}
