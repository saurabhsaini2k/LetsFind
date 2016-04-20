package xcal.letsfind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GCM";
    private static final String TABLE_NAME = "newsTable";
    private static final String EMAIL = "EMAIL";
    private static final String TITLE = "TITLE";
    private static final String AUTHENTICATION = "AUTHENTICATION";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("CREATE TABLE " + TABLE_NAME + "( UID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL STRING, TITLE STRING, AUTHENTICATION STRING)");
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select UID as _id, EMAIL, TITLE,AUTHENTICATION  from "+TABLE_NAME,null);
        if(res!=null){
            res.moveToFirst();
        }
        return res;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public void insertMsg(String title,String email,String auth) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("TITLE", title);
        cv.put("EMAIL", email);
        cv.put("AUTHENTICATION", auth);


        db.insert(TABLE_NAME, null, cv);

        db.close();

    }

}