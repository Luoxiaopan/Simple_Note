package com.luoxiaopan.noteEdit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class NoteDatabaseHelper extends SQLiteOpenHelper{

	private Context mContext;
	public static final String CREATE_NOTEDATA_STRING = "create table Note ("
			+"noteNum integer primary key autoincrement, "
			+"noteTime text, "
			+"noteTitle text, "
			+"noteContent text)"
			;
	
	public NoteDatabaseHelper(Context context,String name,CursorFactory factory,int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_NOTEDATA_STRING);
		Toast.makeText(mContext, "Create successfully", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
