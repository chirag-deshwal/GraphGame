package shot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.choo827.graphgame.R;

public class Atm extends Fragment {
	public static LottieAnimationView atm;

	public Atm() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_atm, container, false);
		atm = view.findViewById(R.id.atm);
//		atm.playAnimation();

		return view;
	}

}
