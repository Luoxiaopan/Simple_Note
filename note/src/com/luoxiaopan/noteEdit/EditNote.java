package com.luoxiaopan.noteEdit;

import com.luoxiaopan.note.Config;
import com.luoxiaopan.note.Note;
import com.luoxiaopan.note.R;
import com.luoxiaopan.noteWidget.NoteWidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditNote extends Activity {
	private EditText editNoteTitle;
	private EditText editNoteContent;
	private TextView headTitleTextView;
	private Button saveNote;
	private int inputPosition;
	private String inputNoteTime;
	private Note oldNote;
	private DBManager dbManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_edit_note);

		inputPosition = getIntent().getIntExtra("position", 0); 
		//如果“position”对应的没有值，则直接使用后面“”1
		inputNoteTime = getIntent().getStringExtra("noteTime");
		dbManager = new DBManager(this);

		headTitleTextView = (TextView)findViewById(R.id.headTitleTextView);
		headTitleTextView.setText("NOTE EDITING");		editNoteTitle = (EditText)findViewById(R.id.editNoteTitle);	
		editNoteContent = (EditText)findViewById(R.id.editNoteContent);	
		oldNote =  dbManager.findNote(inputPosition);
		if(inputPosition != 0){
			editNoteTitle.setText(oldNote.getTitle());
			editNoteContent.setText(oldNote.getContent());
		}
		saveNote = (Button)findViewById(R.id.saveNoteButton);
		saveNote.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				save(inputPosition);
				finish();
			}
		});
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)  {  
	    if (keyCode == KeyEvent.KEYCODE_BACK )  
	    {  
	    	if(editNoteTitle.getText().toString().equals("") && editNoteContent.getText().toString().equals("") ){
	    		dbManager.close();
	    	}
	    	else {
	    		save(inputPosition);
	    	}
    		finish();
	    }  
	     return false;  
	} 
	private void save(int position){
		Note note = new Note(Note.currentTime(),editNoteTitle.getText().toString(),editNoteContent.getText().toString());
		dbManager.saveNote(note);
		if(position != 0)
		{
			dbManager.deleteNote(inputNoteTime);
		}
		dbManager.close();
		Config.FLAG_OF_CHANGE = true;
		Intent broadcastIntent = new Intent(NoteWidget.UPDATE_ACTION);
		sendBroadcast(broadcastIntent);
	}
}
