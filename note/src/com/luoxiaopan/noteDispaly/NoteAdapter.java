package com.luoxiaopan.noteDispaly;

import java.util.ArrayList;

import android.R.string;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.luoxiaopan.note.Note;
import com.luoxiaopan.note.R;

public class NoteAdapter extends ArrayAdapter<Note>{
	private int resourcesId;
	public NoteAdapter(Context context,int textViewResourceId, ArrayList<Note> objects) {
		super(context, textViewResourceId, objects);
		resourcesId = textViewResourceId;
	}
	public View getView(int position, View convertView,ViewGroup parents)
	{
		Note note = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourcesId, null);
		TextView tilteTextView = (TextView)view.findViewById(R.id.list_item_title);
		tilteTextView.setText(note.getTitle());
		TextView timeTextView = (TextView)view.findViewById(R.id.list_item_time);
		timeTextView.setText(getNoteShortTime(note.getNoteTime()));
		TextView contentTextView = (TextView)view.findViewById(R.id.list_item_content);
		contentTextView.setText(note.getHeadContent());
		return view;
	}
	static public String getNoteShortTime(String noteTime){
		String currentTime = Note.currentTime();
		String yearMonthDay = noteTime.substring(0, 10);//XXXX-XX-XX
		String hourMinuteSecond = noteTime.substring(11, 19);//XX:XX:XX
		if(currentTime.substring(0,10).equals(yearMonthDay)) return hourMinuteSecond;
		return yearMonthDay;
	}
}
