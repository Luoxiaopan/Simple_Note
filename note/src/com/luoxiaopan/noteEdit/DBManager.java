package com.luoxiaopan.noteEdit;

import java.util.ArrayList;

import com.luoxiaopan.note.Note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private NoteDatabaseHelper dataHelper;
	private SQLiteDatabase db;
	public DBManager(Context context) {
		dataHelper = new NoteDatabaseHelper(context,"NoteData.db", null, 1);
		db = dataHelper.getWritableDatabase();
	}
	private Note getNoteFromCursor(Cursor cursor)
	{
		String title, content, time;
		time = cursor.getString(cursor.getColumnIndex("noteTime"));
		title = cursor.getString(cursor.getColumnIndex("noteTitle"));
		content = cursor.getString(cursor.getColumnIndex("noteContent"));
		return (new Note(time, title, content));
	}
	public void saveNote(Note savedNote)
	{
		ContentValues noteValues = new ContentValues();
		noteValues.put("noteTime", savedNote.getNoteTime());
		noteValues.put("noteTitle", savedNote.getTitle());
		noteValues.put("noteContent", savedNote.getContent());
		db.insert("Note", null, noteValues);
	}
	public Note findNote(int num)//µ¹Ðò²é¿´
	{
		Note note = null;
		Cursor cursor = db.query("Note", null,null,null,null,null,null);
        if(!cursor.moveToLast()) return note;
		for(int i = 1 ;i < num; i ++)
		{
			cursor.moveToPrevious();
		}
		note = getNoteFromCursor(cursor);
        cursor.close();
		return note;
	}
	public ArrayList<Note> getAllNote()
	{
		ArrayList<Note> noteList = new ArrayList<Note>();
		Cursor cursor = db.query("Note", null,null,null,null,null,null);
        if(!cursor.moveToLast()) return noteList;
        noteList.add(getNoteFromCursor(cursor));
        while(cursor.moveToPrevious())
        {
            noteList.add(getNoteFromCursor(cursor));
        }
        cursor.close();
		return noteList;
	}

	public void deleteNote(String time)
	{
		db.execSQL("delete from Note where noteTime = '" + time +"'");
	}
	public void close() {
		db.close();
		dataHelper.close();
	}
}
