package com.example.choo827.graphgame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import shot.Atm;
import shot.Daynight;
import shot.Luck;
import shot.Send;
import shot.Welcome;

import static shot.Atm.atm;
import static shot.Daynight.daynight;
import static shot.Luck.luck;
import static shot.Send.send;

public class FirstView extends AppCompatActivity {
	private ViewPager vp = null;
	private FloatingActionButton fab = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_view);

		vp = (ViewPager) findViewById(R.id.vp);
		vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
		vp.setCurrentItem(0);
		vp.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

		fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vp.setCurrentItem(getItem(+1));
				switch (vp.getCurrentItem()) {
					case 1:
						atm.playAnimation();
						break;
					case 2:
						daynight.playAnimation();
						daynight.loop(true);
						break;
					case 3:
						send.playAnimation();
						daynight.loop(false);
						break;
					case 4: {
						sec4();
						break;
					}
				}
			}
		});
	}

	private void sec4() {
		luck.playAnimation();
		luck.loop(true);
		fab.setImageResource(R.drawable.check);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int infoFirst = 1;
				SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
				SharedPreferences.Editor editor = a.edit();
				editor.putInt("First", infoFirst);
				editor.commit();
				finish();
			}
		});
	}

	private int getItem(int i) {
		return vp.getCurrentItem() + i;
	}

	private class pagerAdapter extends FragmentPagerAdapter {
		pagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			switch (position) {
				case 0:
					return new Welcome();
				case 1:
					return new Atm();
				case 2:
					return new Daynight();
				case 3:
					return new Send();
				case 4:
					return new Luck();
				default:
					return null;
			}
		}

		@Override
		public int getCount() {
			return 5;
		}
	}

	@Override
	public void onBackPressed() {
//		super.onBackPressed();
		Snackbar.make(findViewById(android.R.id.content), "뛰어 넘기시겠습니까?", Snackbar.LENGTH_LONG)
				.setAction("예", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						vp.setCurrentItem(4);
						sec4();
					}
				}).show();
	}
}
