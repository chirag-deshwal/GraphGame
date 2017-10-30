package com.example.choo827.graphgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appinvite.AppInviteInvitation;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private WebView wbMain;
	private ActionBarDrawerToggle toggle;
	private ProgressBar pgb;
	private MenuItem item;
	public static int REQUEST_INVITE=10;
	private AdView mAdView;

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

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
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
		wbMain.loadUrl("http://112.148.160.163:3000");

		wbMain.setWebViewClient(new WebClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				switch (errorCode) {
//					case ERROR_CONNECT:{
//						Toast.makeText(MainActivity.this,"connet error",Toast.LENGTH_SHORT).show();
//						break;
//					}
//
//					case ERROR_TIMEOUT:{
//						Toast.makeText(MainActivity.this,"time out",Toast.LENGTH_SHORT).show();
//						break;
//					}

					case ERROR_HOST_LOOKUP: {
//						Toast.makeText(MainActivity.this,"ㅈㅅ",Toast.LENGTH_SHORT).show();
//						wbMain.loadUrl("");
						break;
					}
				}
			}
		});

		wbMain.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress < 100) {
					pgb.setVisibility(ProgressBar.VISIBLE);
				} else if (progress == 100) {
					pgb.setVisibility(ProgressBar.GONE);
					try {
						completeRefresh();
					} catch (NullPointerException e) {
						e.getStackTrace();
					}
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
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		item = (MenuItem) menu.findItem(R.id.action_refresh);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (toggle.onOptionsItemSelected(item))
			return true;
		int id = item.getItemId();
		if (id == R.id.action_refresh) {
			refresh();
			wbMain.reload();
		}

		return super.onOptionsItemSelected(item);
	}

	public void refresh() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ImageView iv = (ImageView) inflater.inflate(R.layout.iv_refresh, null);

		Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
		rotation.setRepeatCount(Animation.INFINITE);
		iv.startAnimation(rotation);
		item.setActionView(iv);
	}

	public void completeRefresh() {
		item.getActionView().clearAnimation();
		item.setActionView(null);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		// Handle navigation view item clicks here.
		switch (item.getItemId()) {
			case R.id.home: {
				wbMain.loadUrl("http://112.148.160.163:3000");
				setTitle(R.string.app_name);
				break;
			}

			case R.id.game: {
				wbMain.loadUrl("http://112.148.160.163:3000/start");
				setTitle("게임하기");
				break;
			}

			case R.id.ranking: {
				wbMain.loadUrl("http://112.148.160.163:3000/rank");
				setTitle("랭킹");
				break;
			}

			case R.id.donation: {
				wbMain.loadUrl("http://112.148.160.163:3000/donation");
				setTitle("기부하기");
				break;
			}

			case R.id.more: {
				wbMain.loadUrl("http://112.148.160.163:3000/more");
				setTitle("더보기");
				break;
			}

			case R.id.nav_share: {
				onInviteClicked();
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

	private void onInviteClicked() {
		Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
				.setMessage(getString(R.string.invitation_message))
				.setCallToActionText(getString(R.string.invitation_cta))
				.build();
		startActivityForResult(intent, REQUEST_INVITE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_INVITE) {
			if (resultCode == RESULT_OK) {
				// Get the invitation IDs of all sent messages
				String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
				for (String id : ids) {
					Log.d("invite", "onActivityResult: sent invitation " + id);
				}
			} else {
				// Sending failed or it was canceled, show failure message to the user
				// ...
				Snackbar.make(findViewById(android.R.id.content), "초대취소", Snackbar.LENGTH_LONG).show();
			}
		}
	}
}