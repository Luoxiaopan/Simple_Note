package com.luoxiaopan.noteDispaly;

import java.util.ArrayList;

import com.luoxiaopan.note.Config;
import com.luoxiaopan.note.Note;
import com.luoxiaopan.note.R;
import com.luoxiaopan.noteEdit.DBManager;
import com.luoxiaopan.noteEdit.EditNote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class DisplayAllNote extends Activity {
	private ListView noteListView;
	private NoteAdapter noteAdapter;
	private Button addNoteButton;
	private TextView headTitleTextView;
	private ArrayList<Note> noteList;
	private DBManager dbManager;
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		if(Config.FLAG_OF_CHANGE) {
			noteList = dbManager.getAllNote();
			noteAdapter = new NoteAdapter(this, R.layout.all_note_list_item, noteList);
			noteListView.setAdapter(noteAdapter);
			Config.FLAG_OF_CHANGE = false;
		}
		super.onStart();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_display_all_note);
		noteListView = (ListView)findViewById(R.id.displayNoteList);
		addNoteButton = (Button)findViewById(R.id.addNoteButton);
		headTitleTextView = (TextView)findViewById(R.id.headTitleTextView);
		headTitleTextView.setText("NOTE LIST");
		dbManager = new DBManager(this);
		noteList = dbManager.getAllNote();
		
		addNoteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent outIntent = new Intent(DisplayAllNote.this,EditNote.class);
				startActivity(outIntent);	
			}
		});
		noteAdapter = new NoteAdapter(this, R.layout.all_note_list_item, noteList);
		noteListView.setAdapter(noteAdapter);
		Config.FLAG_OF_CHANGE = false;
		noteListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent,View view,int position,long id){

				Intent outIntent = new Intent(DisplayAllNote.this,DisplayNote.class);
				outIntent.putExtra("noteTime", noteList.get(position).getNoteTime());
				outIntent.putExtra("position", position + 1);
				startActivity(outIntent);
				//finish();
			}
		});
	}

}

