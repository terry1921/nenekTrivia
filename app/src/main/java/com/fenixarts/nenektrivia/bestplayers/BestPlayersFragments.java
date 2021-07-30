package com.fenixarts.nenektrivia.bestplayers;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.base.BaseFragment;
import com.fenixarts.nenektrivia.bestplayers.domain.adapter.HonorAdapter;
import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.help.HelpFragment;
import com.fenixarts.nenektrivia.utils.Injection;
import com.fenixarts.nenektrivia.utils.Notify;
import com.fenixarts.nenektrivia.utils.Utils;

import java.util.Collection;

/**
 * NenekTrivia
 * Created by terry0022 on 08/02/18 - 17:55.
 */

public class BestPlayersFragments extends BaseFragment implements BestPlayersContract.View {

    private BestPlayersContract.Presenter presenter;
    private HonorAdapter adapter;

    /**
     * Crea una instancia prefabricada de {@link HelpFragment}
     *
     * @return Instancia dle fragmento
     */
    public static BestPlayersFragments getInstance() {
        return new BestPlayersFragments();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = onCreateNenekView(inflater, container, R.layout.activity_honor);
        initView(mainView);
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Utils.isOnline(getContext())){
            presenter.onStart();
            setNoConnection(false);
        }else{
            setNoConnection(true);
        }
    }

    private void initView(View view) {
        /* initialize presenter */
        presenter = new BestPlayersPresenter(this,
                Injection.provideUseCaseHandler(),
                Injection.provideGetBestPlayerUseCase(getContext()));

        /* find views */
        RecyclerView recyclerView = view.findViewById(R.id.honor_list);
        recyclerView.setHasFixedSize(true);

        /* set actions */
        adapter = new HonorAdapter();
        adapter.onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(BestPlayersContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showBestPlayers(Collection<BestPlayersItem> collection) {
        if (collection.isEmpty()){
            showNoAvailableContent(true);
        }else {
            showNoAvailableContent(false);
            adapter.updateCollection(collection);
        }
    }

    @Override
    public void onError(String message) {
        new Notify(getContext(), Notify.LENGTH_SHORT,message,Notify.NOTIFY_ALERT).showNotify();
    }

    @Override
    public void onError(int message) {
        new Notify(getContext(), Notify.LENGTH_SHORT,getString(message),Notify.NOTIFY_ALERT).showNotify();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        super.setLoadingIndicator(active);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
