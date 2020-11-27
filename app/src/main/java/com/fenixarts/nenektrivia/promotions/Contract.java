package com.fenixarts.nenektrivia.promotions;

import com.fenixarts.nenektrivia.base.BasePresenter;
import com.fenixarts.nenektrivia.base.BaseView;
import com.fenixarts.nenektrivia.promotions.domain.models.PromotionRow;

import java.util.Collection;

/**
 * NenekTrivia
 * Created by terry0022 on 19/02/18 - 18:30.
 */

public interface Contract {

    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(final boolean show);

        void onError(final int resource);

        void onError(final String error);

        void showPromotions(Collection<PromotionRow> collection);

    }

    interface Presenter extends BasePresenter{

    }

}
