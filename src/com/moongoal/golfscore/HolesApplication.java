package com.moongoal.golfscore;

import static com.moongoal.golfscore.HolesSQLiteOpenHelper.*;

import java.util.ArrayList;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.moongoal.golfscore.Holes;
import com.moongoal.golfscore.HolesSQLiteOpenHelper;

public class HolesApplication extends Application {

	private ArrayList<Holes> currentHoles;
	private SQLiteDatabase database;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		HolesSQLiteOpenHelper helper = new HolesSQLiteOpenHelper(this); 
		database = helper.getWritableDatabase();
		if (null == currentHoles) {
			loadHoles();
		}
	}

	private void loadHoles() {
		currentHoles = new ArrayList<Holes>();
		Cursor tasksCursor = database.query(ROUNDS_TABLE, //rounds table
				new String[] {ROUND_ID, HOLE_1, HOLE_2, HOLE_3,
							  HOLE_4, HOLE_5, HOLE_6, HOLE_7, HOLE_8, HOLE_9,
							  HOLE_10, HOLE_11, HOLE_12, HOLE_13, HOLE_14,
							  HOLE_15, HOLE_16, HOLE_17, HOLE_18, ROUND_NAME}, // fields on the table id, name and holes
				null, null, null, null, // advanced Select such as Where
				null); // order by
		tasksCursor.moveToFirst();
		Holes h;
		if (! tasksCursor.isAfterLast()) {
			do {
				long id = tasksCursor.getLong(0);
	     		int hole_1 = tasksCursor.getInt(1);
				int hole_2 = tasksCursor.getInt(2);
				int hole_3 = tasksCursor.getInt(3);
				int hole_4 = tasksCursor.getInt(4);
				int hole_5 = tasksCursor.getInt(5);
				int hole_6 = tasksCursor.getInt(6);
				int hole_7 = tasksCursor.getInt(7);
				int hole_8 = tasksCursor.getInt(8);
				int hole_9 = tasksCursor.getInt(9);
				int hole_10 = tasksCursor.getInt(10);
				int hole_11 = tasksCursor.getInt(11);
				int hole_12 = tasksCursor.getInt(12);
				int hole_13 = tasksCursor.getInt(13);
				int hole_14 = tasksCursor.getInt(14);
				int hole_15 = tasksCursor.getInt(15);
				int hole_16 = tasksCursor.getInt(16);
				int hole_17 = tasksCursor.getInt(17);
				int hole_18 = tasksCursor.getInt(18);
				String name  = tasksCursor.getString(19);
				h = new Holes(name);
				h.setId(id);
				h.setHole_1(hole_1);
				h.setHole_2(hole_2);
				h.setHole_3(hole_3);
				h.setHole_4(hole_4);
				h.setHole_5(hole_5);
				h.setHole_6(hole_6);
				h.setHole_7(hole_7);
				h.setHole_8(hole_8);
				h.setHole_9(hole_9);
				h.setHole_10(hole_10);
				h.setHole_11(hole_11);
				h.setHole_12(hole_12);
				h.setHole_13(hole_13);
				h.setHole_14(hole_14);
				h.setHole_15(hole_15);
				h.setHole_16(hole_16);
				h.setHole_17(hole_17);
				h.setHole_18(hole_18);
				h.setName(name);
				currentHoles.add(h);
			} while (tasksCursor.moveToNext());
		}
		
		tasksCursor.close();
		
	}
	
	public void setCurrentHoles(ArrayList<Holes> currentHoles) {
		this.currentHoles = currentHoles;
	}
	
	public ArrayList<Holes> getCurrentHoles() {
		return currentHoles;
	}
	
	public void addHoles(Holes h) {
		assert(null != h);
		
		ContentValues values = new ContentValues();
		values.put(ROUND_NAME, h.getName());
		values.put(HOLE_1, h.getHole_1());
		values.put(HOLE_2, h.getHole_2());
		values.put(HOLE_3, h.getHole_3());
		values.put(HOLE_4, h.getHole_4());
		values.put(HOLE_5, h.getHole_5());
		values.put(HOLE_6, h.getHole_6());
		values.put(HOLE_7, h.getHole_7());
		values.put(HOLE_8, h.getHole_8());
		values.put(HOLE_9, h.getHole_9());
		values.put(HOLE_10, h.getHole_10());
		values.put(HOLE_11, h.getHole_11());
		values.put(HOLE_12, h.getHole_12());
		values.put(HOLE_13, h.getHole_13());
		values.put(HOLE_14, h.getHole_14());
		values.put(HOLE_15, h.getHole_15());
		values.put(HOLE_16, h.getHole_16());
		values.put(HOLE_17, h.getHole_17());
		values.put(HOLE_18, h.getHole_18());

		long id = database.insert(ROUNDS_TABLE, null, values);
		h.setId(id);

		currentHoles.add(h);
	}
	
	public void saveHoles(Holes h) {
		assert(null != h);
		
		ContentValues values = new ContentValues();
		values.put(ROUND_NAME, h.getName());
		values.put(HOLE_1, h.getHole_1());
		values.put(HOLE_2, h.getHole_2());
		values.put(HOLE_3, h.getHole_3());
		values.put(HOLE_4, h.getHole_4());
		values.put(HOLE_5, h.getHole_5());
		values.put(HOLE_6, h.getHole_6());
		values.put(HOLE_7, h.getHole_7());
		values.put(HOLE_8, h.getHole_8());
		values.put(HOLE_9, h.getHole_9());
		values.put(HOLE_10, h.getHole_10());
		values.put(HOLE_11, h.getHole_11());
		values.put(HOLE_12, h.getHole_12());
		values.put(HOLE_13, h.getHole_13());
		values.put(HOLE_14, h.getHole_14());
		values.put(HOLE_15, h.getHole_15());
		values.put(HOLE_16, h.getHole_16());
		values.put(HOLE_17, h.getHole_17());
		values.put(HOLE_18, h.getHole_18());
		
		long id = h.getId();
		String where = String.format("%s = %d", ROUND_ID, id);

		database.update(ROUNDS_TABLE, values, where, null);
	}
	
	
	public void deleteHoles(Long[] ids) {
		StringBuffer idList = new StringBuffer();
		for (int i=0; i<ids.length; i++) {
			idList.append(ids[i]);
			if (i < ids.length - 1) {
				idList.append(",");
			}
		}
		// delete from tasks
		// where ids in (1,42,5)
		String where = String.format("%s in (%s)", ROUND_ID, idList);
		database.delete(ROUNDS_TABLE, where, null);
	}
}
