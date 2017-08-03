package com.luoxiaopan.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Bundle bundle = new Bundle();
		bundle.putInt("noteNum", 0);
		Intent intent = new Intent(this,com.luoxiaopan.noteDispaly.DisplayAllNote.class);
		intent.putExtras(bundle);
		startActivity(intent);
		//finish();
	}
}
