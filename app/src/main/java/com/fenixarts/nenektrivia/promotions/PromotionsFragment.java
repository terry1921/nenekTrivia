package com.fenixarts.nenektrivia.promotions;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.base.BaseFragment;
import com.fenixarts.nenektrivia.promotions.domain.adapters.PromotionAdapter;
import com.fenixarts.nenektrivia.promotions.domain.models.PromotionRow;
import com.fenixarts.nenektrivia.utils.Injection;
import com.fenixarts.nenektrivia.utils.Notify;
import com.fenixarts.nenektrivia.utils.Utils;

import java.util.Collection;

/**
 * NenekTrivia
 * Created by terry0022 on 08/02/18 - 17:39.
 */

public class PromotionsFragment extends BaseFragment implements Contract.View{

    private PromotionAdapter adapter;
    private Contract.Presenter presenter;

    public static Fragment getInstance() {
        return new PromotionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = onCreateNenekView(inflater, container, R.layout.activity_promotions);
        initView(mainView);
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Utils.isOnline(getContext())){
            presenter.onStart();
        }else{
            setNoConnection(true);
        }
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.promotion_list);
        recyclerView.setHasFixedSize(true);

        adapter = new PromotionAdapter();
        adapter.onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        presenter = new PromotionsPresenter(this,
                Injection.provideUseCaseHandler(),
                Injection.provideGetPromotionsUseCase(getContext()));
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean show) {
        super.setLoadingIndicator(show);
    }

    @Override
    public void onError(int resource) {
        new Notify(getContext(), Notify.LENGTH_SHORT, getString(resource),Notify.NOTIFY_ALERT).showNotify();
    }

    @Override
    public void onError(String error) {
        new Notify(getContext(), Notify.LENGTH_SHORT, error,Notify.NOTIFY_ALERT).showNotify();
    }

    @Override
    public void showPromotions(Collection<PromotionRow> collection) {
        if (collection.isEmpty()){
            showNoAvailableContent(true);
        }else{
            showNoAvailableContent(false);
            adapter.addAll(collection);
        }
    }

}
