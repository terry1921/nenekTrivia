package com.fenixarts.nenektrivia.help;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenixarts.nenektrivia.R;

/**
 * NenekTrivia
 * Created by terry0022 on 08/02/18 - 17:32.
 */

public class HelpFragment extends Fragment{

    /**
     * Crea una instancia prefabricada de {@link HelpFragment}
     *
     * @return Instancia dle fragmento
     */
    public static HelpFragment getInstance() {
        return new HelpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_help, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }

}
