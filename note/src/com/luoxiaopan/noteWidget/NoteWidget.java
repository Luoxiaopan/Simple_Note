package com.luoxiaopan.noteWidget;

import com.luoxiaopan.note.R;
import com.luoxiaopan.noteDispaly.DisplayAllNote;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class NoteWidget extends AppWidgetProvider {
	public static final String TOAST_ACTION = "com.luoxiaopan.note.TOAST_ACTION";
	public static final String EXTRA_ACTION = "com.luoxiaopan.note.EXTRA_ACTION";
	public static final String UPDATE_ACTION = "com.luoxiaopan.note.UPDATE_ACTION";
	
	public NoteWidget() {
		
	}
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

		Intent intent = new Intent(context,WidgetNoteListService.class);
		remoteView.setRemoteAdapter(R.id.widgetDisplayNoteList, intent);
		Intent openAppIntent = new Intent(context,DisplayAllNote.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, 0);
		remoteView.setOnClickPendingIntent(R.id.openNoteApp, pendingIntent);
		 //remoteView.setEmptyView(viewId, emptyViewId);
		 //Intent toastIntent = new Intent(context,WidgetNoteListService.class);
		 // toastIntent.setAction(TOAST_ACTION);
		 //PendingIntent toastpPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		 //remoteView.setPendingIntentTemplate(R.id.widgetDisplayNoteList, toastpPendingIntent);
		 //appWidgetManager.updateAppWidget(new ComponentName(context, WidgetNoteListService.class), remoteView);
		 //
		ComponentName componentName = new ComponentName(context,NoteWidget.class);
		appWidgetManager.updateAppWidget(componentName, remoteView);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	@Override
	public void onDeleted(Context context, int[] appWidgetIds){
		super.onDeleted(context, appWidgetIds);
	}
	@Override
	public void onEnabled(Context context){
		super.onEnabled(context);
	}
	@Override
	public void onReceive(Context context, Intent intent){
		super.onReceive(context, intent);

		if(intent.getAction().equals(TOAST_ACTION))
		{
			//onUpdate(context, this.appWidgetManager, appWidgetIds);
		}
		if(intent.getAction().equals(UPDATE_ACTION))
		{
			Log.d("UPDATE_ACTION", UPDATE_ACTION);
			//RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			
			//remoteView.setTextViewText(R.id.widgetTitle, "Update successfully!");
			//Intent adapterIntent= new Intent(context,WidgetNoteListService.class);
			//remoteView.setRemoteAdapter(R.id.widgetDisplayNoteList, adapterIntent);
			//这里的intent（adpterIntent）与形参的intent不同
			AppWidgetManager appWidgetManger = AppWidgetManager.getInstance(context);
			ComponentName componentName = new ComponentName( context, NoteWidget.class);
			int[] appIds = appWidgetManger.getAppWidgetIds(componentName);
			appWidgetManger.notifyAppWidgetViewDataChanged (appIds, R.id.widgetDisplayNoteList);
			//this.onUpdate(context, appWidgetManger, appIds);
			//appWidgetManger.updateAppWidget(componentName, remoteView); 
			//super.onUpdate(context, appWidgetManager, appIds);
		}
	}
	
}
