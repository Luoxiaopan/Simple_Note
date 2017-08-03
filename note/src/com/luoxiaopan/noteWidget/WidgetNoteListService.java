package com.luoxiaopan.noteWidget;

import java.util.ArrayList;

import com.luoxiaopan.note.Note;
import com.luoxiaopan.note.R;
import com.luoxiaopan.noteEdit.DBManager;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class WidgetNoteListService extends RemoteViewsService {

	public WidgetNoteListService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		// TODO Auto-generated method stub
		return new StackRemoteViewsFactory(this.getApplicationContext(),intent);
	}
	class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
		private Context mContext;
		private ArrayList<Note> noteList;
		private DBManager dbManager;
		public StackRemoteViewsFactory(Context context,Intent intent){
			mContext = context;
		}
		
		@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			dbManager = new DBManager(mContext);
			noteList = dbManager.getAllNote();
		}

		@Override
		public void onDataSetChanged() {
			// TODO Auto-generated method stub
			dbManager = new DBManager(mContext);
			noteList = dbManager.getAllNote();
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			dbManager.close();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return noteList.size();
		}

		@Override
		public RemoteViews getViewAt(int position) {
			// TODO Auto-generated method stub
			RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),R.layout.all_note_list_item);
			remoteViews.setTextViewText(R.id.list_item_title, noteList.get(position).getTitle());
			remoteViews.setTextViewText(R.id.list_item_time,getNoteShortTime( noteList.get(position).getNoteTime()));
			remoteViews.setTextViewText(R.id.list_item_content, noteList.get(position).getHeadContent());
			Intent fillIntent = new Intent();
			fillIntent.putExtra("", position);//
			return remoteViews;
		}

		private String getNoteShortTime(String noteTime){
			String currentTime = Note.currentTime();
			String yearMonthDay = noteTime.substring(0, 10);//XXXX-XX-XX
			String hourMinuteSecond = noteTime.substring(11, 19);//XX:XX:XX
			if(currentTime.substring(0,10).equals(yearMonthDay)) return hourMinuteSecond;
			return yearMonthDay;
		}
		@Override
		public RemoteViews getLoadingView() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	
}
