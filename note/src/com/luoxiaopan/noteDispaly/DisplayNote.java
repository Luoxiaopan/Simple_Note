package com.luoxiaopan.noteDispaly;

import com.luoxiaopan.note.Config;
import com.luoxiaopan.note.Note;
import com.luoxiaopan.note.R;
import com.luoxiaopan.noteEdit.DBManager;
import com.luoxiaopan.noteEdit.EditNote;
import com.luoxiaopan.noteWidget.NoteWidget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class DisplayNote extends Activity {

	private TextView noteTitle;
	private TextSwitcher noteContent;
	private TextView noteTime;
	private TextView headTitleTextView;
	private Button editNote;
	private Button deleteNote;
	private DBManager dbManager;
	private int inputPosition;
	private String inputNoteTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_display_note);
		Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
	
		inputPosition = getIntent().getIntExtra("position", 1); 
		//如果“position”对应的没有值，则直接使用后面“”1
		inputNoteTime = getIntent().getStringExtra("noteTime");
		
		noteTitle = (TextView)findViewById(R.id.displayNoteTitle);
		noteContent = (TextSwitcher)findViewById(R.id.displayNoteContent);
		noteContent.setFactory(new ViewFactory(){
			
			@Override
			public View makeView() {
				TextView textView = new TextView(DisplayNote.this);
				textView.setGravity(Gravity.CENTER);
				
				return textView;
			}
		});
		noteContent.setInAnimation(in);
		noteContent.setOutAnimation(out);
		
		
		noteTime = (TextView)findViewById(R.id.displayNoteTime);
		headTitleTextView = (TextView)findViewById(R.id.headTitleTextView);
		headTitleTextView.setText("NOTE CONTENT");
		editNote = (Button)findViewById(R.id.editNoteButton);
		deleteNote = (Button)findViewById(R.id.deleteNoteButton);

		dbManager = new DBManager(this);
		Note note = dbManager.findNote(inputPosition);
		if(note != null)
		{
			noteTitle.setText(note.getTitle());
			noteContent.setText(note.getContent());
			noteTime.setText(inputNoteTime);
		}
		//dbManager.close();
		editNote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent outIntent = new Intent(DisplayNote.this,EditNote.class);
				outIntent.putExtra("noteTime", inputNoteTime);
				outIntent.putExtra("position", inputPosition);
				startActivity(outIntent);
				finish();
			}
		});
		deleteNote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(DisplayNote.this);
				builder.setMessage("Do you want to delete the note or not?");
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dbManager.deleteNote(inputNoteTime);
						Config.FLAG_OF_CHANGE = true;
						Intent broadcastIntent = new Intent(NoteWidget.UPDATE_ACTION);
						sendBroadcast(broadcastIntent);
						dbManager.close();
						finish();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				builder.show();
			}
		});
		
	}
}
