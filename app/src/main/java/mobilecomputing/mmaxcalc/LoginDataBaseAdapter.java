package mobilecomputing.mmaxcalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LoginDataBaseAdapter extends SQLiteOpenHelper {

    // declare the DB name here
    public static final String DATABASE_NAME = "userlogin.db";

    // now declare the TABLE name that will be part of the DB
    public static final String TABLE_NAME = "user_table";

    // declare the COLUMNS of the TABLE
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "WEIGHT";
    public static final String COL_5 = "AREA";
    public static final String COL_6 = "PSI";


    // this is referencing the java class that will manage the SQL DB
    public LoginDataBaseAdapter(Context context) {

        // whenever the constructor below is called, our DB will now be created
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // this is the execute sql query method that takes a string sql query and executes this query
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, WEIGHT TEXT, AREA TEXT, PSI TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // upgrade the table if version number is increased and call onCreate to create a new DB
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String username, String password, String weight, String area, String psi) {

        // Open the database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        // This class is used to store a set of values that a ContentResolver can process.
        ContentValues contentValues = new ContentValues();

        // you need to specify the column and the data for that column
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,password);
        contentValues.put(COL_4,weight);
        contentValues.put(COL_5, area);
        contentValues.put(COL_6, psi);

        // need to give this the table name and the content values
        long result = db.insert(TABLE_NAME,null,contentValues);

        // method will return -1 if the insert did not work
        if (result == -1)
            return false;
        else
            return true;
    }

    public String getPassword(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String password = "";
        try{

            cursor = db.rawQuery("SELECT PASSWORD FROM user_table WHERE USERNAME=?", new String[] {username + ""});

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
            }

            return password;
        }finally {

            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getWeight(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String weight = "";
        try{

            cursor = db.rawQuery("SELECT WEIGHT FROM user_table WHERE USERNAME=?", new String[] {username + ""});

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                weight = cursor.getString(cursor.getColumnIndex("WEIGHT"));
            }

            return weight;
        }finally {

            cursor.close();
        }
    }
    public String getID(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String id = "";
        try{

            cursor = db.rawQuery("SELECT ID FROM user_table WHERE USERNAME=?", new String[] {username + ""});

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                id = cursor.getString(cursor.getColumnIndex("ID"));
            }

            return id;
        }finally {

            cursor.close();
        }
    }
    public String getArea(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String area = "";
        try{

            cursor = db.rawQuery("SELECT AREA FROM user_table WHERE USERNAME=?", new String[] {username + ""});

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                area = cursor.getString(cursor.getColumnIndex("AREA"));
            }

            return area;
        }finally {

            cursor.close();
        }
    }

    public String getPSI(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String psi = "";
        try{

            cursor = db.rawQuery("SELECT PSI FROM user_table WHERE USERNAME=?", new String[] {username + ""});

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                psi = cursor.getString(cursor.getColumnIndex("PSI"));
            }

            return psi;
        }finally {

            cursor.close();
        }
    }






    public boolean updateData(String Id, String username, String password, String weight, String area, String psi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Id);
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,password);
        contentValues.put(COL_4, weight);
        contentValues.put(COL_5, area);
        contentValues.put(COL_6, psi);
        db.update(TABLE_NAME, contentValues, "id = ?", new String[] {Id});
        return true;
    }

    public Integer deleteData(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {Id});
    }
}