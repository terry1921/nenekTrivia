package com.fenixarts.nenektrivia.bestplayers;

import com.fenixarts.nenektrivia.base.BasePresenter;
import com.fenixarts.nenektrivia.base.BaseView;
import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;

import java.util.Collection;

/**
 * NenekTrivia
 * Created by terry0022 on 12/01/18 - 11:57.
 */

public interface BestPlayersContract {

    interface View extends BaseView<Presenter>{

        void showBestPlayers(final Collection<BestPlayersItem> collection);

        void onError(final String message);

        void onError(final int message);

        void setLoadingIndicator(boolean active);

        boolean isActive();

    }

    interface Presenter extends BasePresenter{

    }

}
