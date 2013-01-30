package com.richmond.riddler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	  public static final String TABLE_RIDDLES = "riddles";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_RIDDLE_ONE = "riddleone";
	  public static final String COLUMN_RIDDLE_TWO = "riddletwo";
	  public static final String COLUMN_RIDDLE_THREE = "riddlethree";	  
	  public static final String COLUMN_RIDDLE_ONE_HINT = "riddleonehint";
	  public static final String COLUMN_RIDDLE_TWO_HINT = "riddletwohint";
	  public static final String COLUMN_RIDDLE_THREE_HINT = "riddlethreehint";  
	  public static final String COLUMN_RIDDLE_ONE_LOCATION = "riddleonelocation";
	  public static final String COLUMN_RIDDLE_TWO_LOCATION = "riddletwolocation";
	  public static final String COLUMN_RIDDLE_THREE_LOCATION = "riddlethreelocation";
	  public static final String COLUMN_DISTANCE = "distance"; 
	  
	  private static final String DATABASE_NAME = "riddles.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_RIDDLES + "("
	      + COLUMN_ID + " integer primary key autoincrement, " 
	      + COLUMN_RIDDLE_ONE + " text not null, " 
	      + COLUMN_RIDDLE_TWO + " text not null, " 
	      + COLUMN_RIDDLE_THREE + " text not null, " 
	      + COLUMN_RIDDLE_ONE_HINT + " text not null, " 
	      + COLUMN_RIDDLE_TWO_HINT + " text not null, " 
	      + COLUMN_RIDDLE_THREE_HINT + " text not null, " 
	      + COLUMN_RIDDLE_ONE_LOCATION + " text not null, " 
	      + COLUMN_RIDDLE_TWO_LOCATION + " text not null, " 
	      + COLUMN_RIDDLE_THREE_LOCATION + " text not null, " 
	      + COLUMN_DISTANCE+ " text not null);";

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIDDLES);
	    onCreate(db);
	  }
	  

}
