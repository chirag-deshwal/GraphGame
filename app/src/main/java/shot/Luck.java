package shot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.choo827.graphgame.R;

public class Luck extends Fragment {
	public static LottieAnimationView luck;


	public Luck() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view=inflater.inflate(R.layout.fragment_luck, container, false);
		luck=(LottieAnimationView)view.findViewById(R.id.luck);
		luck.setImageAssetsFolder("images/");

		return view;
	}

}
