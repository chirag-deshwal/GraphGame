package shot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.choo827.graphgame.R;

public class Send extends Fragment {
	public static LottieAnimationView send;


	public Send() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view= inflater.inflate(R.layout.fragment_send, container, false);
		send=(LottieAnimationView)view.findViewById(R.id.send);


		return view;
	}

}
