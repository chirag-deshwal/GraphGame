package com.example.choo827.graphgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private WebView wbMain;
	private ActionBarDrawerToggle toggle;
	private ProgressBar pgb;
	private AdView bannerAd;
	private InterstitialAd refreshAd;
	private DrawerLayout drawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences preference = getSharedPreferences("a", MODE_PRIVATE);
		int firstviewshow = preference.getInt("First", 0);

		if (firstviewshow != 1) {
			Intent intent = new Intent(MainActivity.this, FirstView.class);
			startActivity(intent);
		}

		drawer = findViewById(R.id.drawer_layout);
		toggle = new ActionBarDrawerToggle(
				this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
		};
		drawer.setDrawerListener(toggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		setUpUI();

		bannerAd = findViewById(R.id.bannerAd);
		AdRequest adRequest = new AdRequest.Builder().build();
		bannerAd.loadAd(adRequest);

		refreshAd = new InterstitialAd(MainActivity.this);
		refreshAd.setAdUnitId("ca-app-pub-9205620612549464/1802718919");
		refreshAd.loadAd(new AdRequest.Builder().build());
	}

	private class WebClient extends WebViewClient {

		// URL 호출하기 전...
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		// webpage를 모두 읽었을 때,
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	}

	private void setUpUI() {
		wbMain = (WebView) findViewById(R.id.wbMain);
		pgb = (ProgressBar) findViewById(R.id.pgb);

		// 무조건해야 한다. 웹페이지 진행상황을 관리하는 클래스
		wbMain.setWebViewClient(new WebClient());

		// 세팅을 가져오고 설정한다.
		WebSettings set = wbMain.getSettings();

		// 자바스크립트를 사용가능하게 하고 zoom을 false한다.
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(false);

		// 이동한다.
		wbMain.loadUrl("https://graph-game-site.herokuapp.com/");

		wbMain.setWebViewClient(new WebClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//				super.onReceivedError(view, errorCode, description, failingUrl);
				wbMain.loadUrl("file:///android_asset/errorPage/ERROR.html");
			}
		});

		wbMain.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress < 100) {
					pgb.setVisibility(ProgressBar.VISIBLE);
				} else if (progress == 100) {
					pgb.setVisibility(ProgressBar.GONE);
				}
				pgb.setProgress(progress);
			}
		});
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		toggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		toggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (toggle.onOptionsItemSelected(item))
			return true;
		int id = item.getItemId();
		if (id == R.id.action_refresh) {
			wbMain.reload();

			refreshAd.loadAd(new AdRequest.Builder().build());
			refreshAd.setAdListener(new AdListener() {
				@Override
				public void onAdClosed() {
					// Load the next interstitial.
					refreshAd.loadAd(new AdRequest.Builder().build());
				}

			});
			if (refreshAd.isLoaded()) {
				refreshAd.show();
			} else {
				Log.d("TAG", "The interstitial wasn't loaded yet.");
			}

		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		// Handle navigation view item clicks here.
		switch (item.getItemId()) {
			case R.id.home: {
				setTitle(R.string.app_name);
				if (wbMain.getUrl().equals("https://graph-game-site.herokuapp.com/")) {
					drawer.closeDrawer(GravityCompat.START);
				} else {
					wbMain.loadUrl("https://graph-game-site.herokuapp.com/");
				}
				break;
			}

			case R.id.game: {
//				Trace myTrace = FirebasePerformance.getInstance().newTrace("test_trace");
//				myTrace.start();
				setTitle("게임하기");
				if (wbMain.getUrl().equals("https://graph-game-site.herokuapp.com/start")) {
					drawer.closeDrawer(GravityCompat.START);
				} else {
					wbMain.loadUrl("https://graph-game-site.herokuapp.com/start");
				}
				break;
			}

			case R.id.ranking: {
				SharedPreferences preference = getSharedPreferences("r", MODE_PRIVATE);
				int Rankshow = preference.getInt("Rank", 0);
				if (Rankshow != 1) {
					Intent rank = new Intent(MainActivity.this, Trophy.class);
					startActivity(rank);
				}

				setTitle("랭킹");
				if (wbMain.getUrl().equals("https://graph-game-site.herokuapp.com/rank")) {
					drawer.closeDrawer(GravityCompat.START);
				} else {
					wbMain.loadUrl("https://graph-game-site.herokuapp.com/rank");
				}
				break;
			}

			case R.id.donation: {
				setTitle("기부하기");
				if (wbMain.getUrl().equals("https://graph-game-site.herokuapp.com/donation")) {
					drawer.closeDrawer(GravityCompat.START);
				} else {
					wbMain.loadUrl("https://graph-game-site.herokuapp.com/donation");
				}
				break;
			}

			case R.id.more: {
				setTitle("더보기");
				if (wbMain.getUrl().equals("https://graph-game-site.herokuapp.com/more")) {
					drawer.closeDrawer(GravityCompat.START);
				} else {
					wbMain.loadUrl("https://graph-game-site.herokuapp.com/more");
				}
				break;
			}

			case R.id.nav_send: {
				Intent it = new Intent(Intent.ACTION_SEND);
				it.setType("plain/text");

				String[] tos = {"ljh86029926@gmail.com"};
				it.putExtra(Intent.EXTRA_EMAIL, tos);
				it.putExtra(Intent.EXTRA_SUBJECT, "문의메일입니다.");
				startActivity(it);
				break;
			}
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
