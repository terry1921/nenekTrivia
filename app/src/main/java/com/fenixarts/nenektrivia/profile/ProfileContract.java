package com.fenixarts.nenektrivia.profile;

import com.fenixarts.nenektrivia.base.BasePresenter;
import com.fenixarts.nenektrivia.base.BaseView;
import com.fenixarts.nenektrivia.profile.domain.models.ProfileItem;

/**
 * NenekTrivia
 * Created by terry0022 on 15/01/18 - 13:47.
 */

public interface ProfileContract {

    interface View extends BaseView<Presenter>{

        void setUserData(final ProfileItem profile);

        void onError();

        void onError(String error);

    }

    interface Presenter extends BasePresenter{

    }

}
