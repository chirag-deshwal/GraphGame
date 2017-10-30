package shot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.choo827.graphgame.R;

public class Daynight extends Fragment {
	public static LottieAnimationView daynight;


	public Daynight() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view= inflater.inflate(R.layout.fragment_daynight, container, false);
		daynight=(LottieAnimationView)view.findViewById(R.id.daynight);
		return view;
	}

}
