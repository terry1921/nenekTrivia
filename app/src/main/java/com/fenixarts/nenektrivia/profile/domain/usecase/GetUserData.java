package com.fenixarts.nenektrivia.profile.domain.usecase;

import com.fenixarts.nenektrivia.UseCase;
import com.fenixarts.nenektrivia.data.source.Repository;
import com.fenixarts.nenektrivia.profile.domain.models.ProfileItem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * NenekTrivia
 * Created by terry0022 on 01/02/18 - 12:58.
 */

public class GetUserData extends UseCase<GetUserData.RequestValues, GetUserData.ResponseValues>{

    private final Repository repository;

    public GetUserData(Repository repository) {
        this.repository = checkNotNull(repository);
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {}

    public static class RequestValues implements UseCase.RequestValues{}

    public static class ResponseValues implements UseCase.ResponseValues{

        private ProfileItem profile;
        private String error;

        public ResponseValues(ProfileItem profile) {
            this.profile = profile;
        }

        public ProfileItem getProfile() {
            return profile;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }

}
