package com.richmond.riddler;


public class RiddlesDataSource {

//
//	
//	// Database fields
//    private SQLiteDatabase database;
//    private MySQLiteHelper dbHelper;
//    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
//    	MySQLiteHelper.COLUMN_RIDDLE_TITLE,
//        MySQLiteHelper.COLUMN_RIDDLE_ONE, 
//        MySQLiteHelper.COLUMN_RIDDLE_TWO, 
//        MySQLiteHelper.COLUMN_RIDDLE_THREE, 
//        MySQLiteHelper.COLUMN_RIDDLE_ONE_HINT, 
//        MySQLiteHelper.COLUMN_RIDDLE_TWO_HINT, 
//        MySQLiteHelper.COLUMN_RIDDLE_THREE_HINT, 
//        MySQLiteHelper.COLUMN_RIDDLE_ONE_LOCATION, 
//        MySQLiteHelper.COLUMN_RIDDLE_TWO_LOCATION, 
//        MySQLiteHelper.COLUMN_RIDDLE_THREE_LOCATION, 
//        MySQLiteHelper.COLUMN_DISTANCE };
//
//    public RiddlesDataSource(Context context) {
//	    dbHelper = new MySQLiteHelper(context);
//    }
//
//    public void open() throws SQLException {
//        database = dbHelper.getWritableDatabase();
//    }
//
//    public void close() {
//        dbHelper.close();
//    }
//
//    public RiddleSequence createRiddleSequence(String[] riddles, double[] locations, double distance) {
//        ContentValues values = new ContentValues();
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_ONE,   riddles[0]);
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_TWO,   riddles[1]);
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_THREE, riddles[2]);
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_ONE_HINT,   riddles[3]);
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_TWO_HINT,   riddles[4]);
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_THREE_HINT, riddles[5]);
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_TITLE, riddles[6]);
//	    
//	    
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_ONE_LOCATION, locations[1]   + "," +locations[0]);
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_TWO_LOCATION, locations[3]   + "," +locations[2]);
//	    values.put(MySQLiteHelper.COLUMN_RIDDLE_THREE_LOCATION, locations[5] + "," +locations[4]);
//	    
//	    values.put(MySQLiteHelper.COLUMN_DISTANCE, distance);
//	    
//	    long insertId = database.insert(MySQLiteHelper.TABLE_RIDDLES, null,
//	        values);
//	    Cursor cursor = database.query(MySQLiteHelper.TABLE_RIDDLES,
//	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//	        null, null, null);
//	    cursor.moveToFirst();
//	    RiddleSequence newComment = cursorToRiddleSequence(cursor);
//	    cursor.close();
//	    return newComment;
//  }
//
////  public void deleteRiddleSequence(RiddleSequence riddleSequence) {
////    //long id = riddleSequence.getId();
////    System.out.println("Riddle Sequence deleted with id: " + id);
////    database.delete(MySQLiteHelper.TABLE_RIDDLES, MySQLiteHelper.COLUMN_ID
////        + " = " + id, null);
////  }
//
//  public List<RiddleSequence> getAllRiddles() {
//    List<RiddleSequence> riddleSequences = new ArrayList<RiddleSequence>();
//
//    Cursor cursor = database.query(MySQLiteHelper.TABLE_RIDDLES,
//        allColumns, null, null, null, null, null);
//
//    cursor.moveToFirst();
//    while (!cursor.isAfterLast()) {
//      RiddleSequence riddleSequence = cursorToRiddleSequence(cursor);
//      riddleSequences.add(riddleSequence);
//      cursor.moveToNext();
//    }
//    // Make sure to close the cursor
//    cursor.close();
//    return riddleSequences;
//  }
//    
//  
//  public RiddleSequence getRiddles(long ID) {
//
//	    Cursor cursor = database.query(MySQLiteHelper.TABLE_RIDDLES,
//	        allColumns, MySQLiteHelper.COLUMN_ID
//	        + " = " + ID , null, null, null, null);
//	    
//	    RiddleSequence r = null;
//	    if (cursor.moveToFirst()){
//	    	r = cursorToRiddleSequence(cursor);
//	    }
//	    else{
//	    	Log.i("Find Riddle", "Can't Find Riddle");
//	    }
//	    // Make sure to close the cursor
//	    cursor.close();
//	    
//	    return r;
// }
  

//  private RiddleSequence cursorToRiddleSequence(Cursor cursor) {
//	RiddleSequence riddleSequence = new RiddleSequence();
//	riddleSequence.setId(cursor.getLong(0));
//	riddleSequence.setRiddletitle(cursor.getString(1));
//	riddleSequence.setRiddleone(cursor.getString(2));
//	riddleSequence.setRiddletwo(cursor.getString(3));
//	riddleSequence.setRiddlethree(cursor.getString(4));
//	riddleSequence.setRiddleonehint(cursor.getString(5));
//	riddleSequence.setRiddletwohint(cursor.getString(6));
//	riddleSequence.setRiddlethreehint(cursor.getString(7));
//	riddleSequence.setRiddleonelocation(cursor.getString(8));
//	riddleSequence.setRiddletwolocation(cursor.getString(9));
//	riddleSequence.setRiddlethreelocation(cursor.getString(10));
//	riddleSequence.setDistance(cursor.getString(11));
//    return riddleSequence;
//  }
  

  
} 