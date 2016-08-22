package com.moongoal.golfscore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HolesSQLiteOpenHelper extends SQLiteOpenHelper {

	public static final int VERSION = 1;
	public static final String DB_NAME  = "holes.sqlite";
	public static final String ROUNDS_TABLE  = "rounds";
	public static final String ROUND_ID = "id";
	public static final String ROUND_NAME = "name";
	public static final String HOLE_1  = "hole_1";
	public static final String HOLE_2  = "hole_2";
	public static final String HOLE_3  = "hole_3";
	public static final String HOLE_4  = "hole_4";
	public static final String HOLE_5  = "hole_5";
	public static final String HOLE_6  = "hole_6";
	public static final String HOLE_7  = "hole_7";
	public static final String HOLE_8  = "hole_8";
	public static final String HOLE_9  = "hole_9";
	public static final String HOLE_10  = "hole_10";
	public static final String HOLE_11  = "hole_11";
	public static final String HOLE_12  = "hole_12";
	public static final String HOLE_13  = "hole_13";
	public static final String HOLE_14  = "hole_14";
	public static final String HOLE_15  = "hole_15";
	public static final String HOLE_16  = "hole_16";
	public static final String HOLE_17  = "hole_17";
	public static final String HOLE_18  = "hole_18";
	
	public HolesSQLiteOpenHelper(Context context) {
		//super(context, name, factory, version);
		super(context, DB_NAME, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);

	}

	private void createTable(SQLiteDatabase db) {
		db.execSQL(
				"create table " + ROUNDS_TABLE +" (" +
				ROUND_ID + " integer primary key autoincrement not null," +
				ROUND_NAME + " text," +
				HOLE_1 + " text," +
				HOLE_2 + " text," +
				HOLE_3 + " text," +
				HOLE_4 + " text," +
				HOLE_5 + " text," +
				HOLE_6 + " text," +
				HOLE_7 + " text," +
				HOLE_8 + " text," +
				HOLE_9 + " text," +
				HOLE_10 + " text," +
				HOLE_11 + " text," +
				HOLE_12 + " text," +
				HOLE_13 + " text," +
				HOLE_14 + " text," +
				HOLE_15 + " text," +
				HOLE_16 + " text," +
				HOLE_17 + " text," +
				HOLE_18 + " text" +
				");"
			);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
