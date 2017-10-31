package com.example.choo827.graphgame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieAnimationView;

public class Trophy extends AppCompatActivity {
	private LottieAnimationView trophy;
	private ImageButton pass;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trophy);

		pass =findViewById(R.id.pass);
		trophy = findViewById(R.id.trophy);
		trophy.playAnimation();

		int infoRank = 1;
		SharedPreferences a = getSharedPreferences("r", MODE_PRIVATE);
		SharedPreferences.Editor editor = a.edit();
		editor.putInt("Rank", infoRank);
		editor.commit();

		pass.setOnClickListener(v-> finish());

	}
}
