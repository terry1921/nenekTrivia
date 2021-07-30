package com.fenixarts.nenektrivia.profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.help.HelpFragment;
import com.fenixarts.nenektrivia.profile.domain.models.ProfileItem;
import com.fenixarts.nenektrivia.utils.Injection;
import com.fenixarts.nenektrivia.utils.Notify;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * NenekTrivia
 * Created by terry0022 on 08/02/18 - 17:42.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View{

    private ImageView profileImage;
    private TextView profileUsername;
    private TextView profileMail;
    private TextView profilePoints;
    private TextView profilePlace;
    private ProfileContract.Presenter presenter;

    /**
     * Crea una instancia prefabricada de {@link HelpFragment}
     *
     * @return Instancia dle fragmento
     */
    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        profileImage = view.findViewById(R.id.profile_image);
        profileUsername = view.findViewById(R.id.profile_username);
        profileMail = view.findViewById(R.id.profile_mail);
        profilePoints = view.findViewById(R.id.profile_points);
        profilePlace = view.findViewById(R.id.profile_place);

        presenter = new ProfilePresenter(this,
                Injection.provideUseCaseHandler(),
                Injection.provideGetUserData(getContext()));
    }

    private void setUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (null != user){
            profileUsername.setText(user.getDisplayName());
            profileMail.setText(user.getEmail());
            profilePlace.setText(getString(R.string.dummy));
            profilePoints.setText(getString(R.string.dummy));
            Picasso.with(getContext()).load(user.getPhotoUrl()).into(profileImage);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
        setUserData();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setUserData(ProfileItem profile) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Picasso.with(getContext()).load(user.getPhotoUrl()).into(profileImage);
        }
        profileUsername.setText(profile.getUsername());
        profileMail.setText(profile.getMail());
        profilePlace.setText(profile.getPlace());
        profilePoints.setText(String.format(Locale.US, "%d", profile.getPoints()));
    }

    @Override
    public void onError() {

    }

    @Override
    public void onError(String error) {
        new Notify(getContext(), Notify.LENGTH_SHORT, error, Notify.NOTIFY_ALERT).showNotify();
    }
}
