package br.com.softblue.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PlanetFragment extends Fragment {

    private Planet planet;

    public static PlanetFragment newInstance(Planet planet) {
        PlanetFragment fragment = new PlanetFragment();
        fragment.planet = planet;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planet, container, false);

        ImageView imageView = (view.findViewById(R.id.image));
        imageView.setImageResource(planet.getImageId());
        getActivity().setTitle(planet.getName());

        return view;
    }
}
