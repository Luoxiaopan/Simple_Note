package com.luoxiaopan.note;

import java.util.Date;
import java.util.Locale;

public class Note {

	private String noteTime;
	private String noteContent;
	private String noteTitle;
	
	public Note(String time,String title, String content) {
		noteTime = time;
		//noteId = getTime();
		noteContent = content;
		if(title.equals(""))
		{
			StringBuffer sb = new StringBuffer(Config.MAX_TITLE_LENGTH);
			for(int i = 0; i < content.length();i++)
			{
				char temp = content.charAt(i);
				if(temp == ','||temp == '.'||temp == '£¬'||temp == '¡£')
				{
					break;
				}
				sb.append(temp);
			}
			noteTitle = sb.toString();
		}
		else noteTitle = title;
	}
	public static String currentTime()
	{
		Date date = new Date();
		String time = String.format(Locale.CHINA,"%tF"+ " " +"%tT",date,date);
		return time;
	}
	public String getContent() {
		return noteContent;
	}
	public String getTitle() {
		return noteTitle;
	}
	public String getHeadContent(){
		if(noteContent.length() <= 20) return noteContent;
		return (noteContent.substring(0, 19) + "¡­¡­");
	}
	public String getNoteTime()
	{
		return noteTime;
	}
}
