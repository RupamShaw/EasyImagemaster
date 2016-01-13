package pl.aprilapps.easyphotopicker.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
<<<<<<< HEAD
 * Created by Dell on 8/01/2016.
=======
 * Created by Rupam on 8/01/2016.
 * updated 12/01/16
>>>>>>> 60b8daac562c53c85e5e7c9f5e2930bd3b07864e
 */
public class MyDBHandler extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION=3;
        private static final String DATABASE_NAME="slideshow.db";
    private static final String TABLE_USERDETAIL="userDetail";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_NAME="name1";
    private static final String COLUMN_IMAGE1="image1";
    private static final String COLUMN_IMAGE2="image2";
    private static final String COLUMN_MUSICNAME="musicName";
    private static final String COLUMN_MUSICPATH="musicPath";
    private static final String COLUMN_FLOATINGTEXT="floatingText";
    private static final String COLUMN_DURATION="duration";
    private static final String COLUMN_lOOPTIMES="loopTimes";
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_USERDETAIL+"("+
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME+" TEXT, "+
                COLUMN_IMAGE1+" TEXT, "+
                COLUMN_IMAGE2+" TEXT, "+
                COLUMN_MUSICNAME+" TEXT, "+
                COLUMN_MUSICPATH+" TEXT, "+
               COLUMN_FLOATINGTEXT+" TEXT ,"+
                COLUMN_DURATION+" INTEGER, "+
                COLUMN_lOOPTIMES+" INTEGER "+
                ");";
        System.out.println("in create query"+query);
        db.execSQL(query);
        System.out.println("afterexec in create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("on upg");
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USERDETAIL);
        System.out.println("on upg1");
        onCreate(db);
        System.out.println("on upg 2 after create db");
    }

    public void addUserDetail(UserDetail userDetail){
        System.out.println("1");
        ContentValues values=new ContentValues();
        System.out.println("2");
        if( userDetail.getNameReason()!=null)
        values.put(COLUMN_NAME, userDetail.getNameReason());
        System.out.println("3");
       if( userDetail.getImage1()!=null)
        values.put(COLUMN_IMAGE1, userDetail.getImage1().toString());
        System.out.println("4");
        if( userDetail.getImage2()!=null)
        values.put(COLUMN_IMAGE2, userDetail.getImage2().toString());
        System.out.println("5");
        if( userDetail.getMusicName()!=null)
        values.put(COLUMN_MUSICNAME, userDetail.getMusicName());
        System.out.println("6");
        if( userDetail.getMusic()!=null)
        values.put(COLUMN_MUSICPATH, userDetail.getMusic().toString());
        System.out.println("7");
        if( userDetail.getFloatingText()!=null)
        values.put(COLUMN_FLOATINGTEXT, userDetail.getFloatingText());
        System.out.println("8");
      //  if( userDetail.getDuration()!=0)
        values.put(COLUMN_DURATION, userDetail.getDuration());
        System.out.println("9");

        values.put(COLUMN_lOOPTIMES, userDetail.getLoopSlideShow());
        System.out.println("10");
        SQLiteDatabase db=this.getWritableDatabase();
        System.out.println("4");
        db.insert(TABLE_USERDETAIL, null, values);
        System.out.println("5 in add");
       /**checking db*/
        String query ="SELECT * FROM "+TABLE_USERDETAIL;
        Cursor c=db.rawQuery(query,null);
        System.out.println("in add user recors in table "+c.getCount());
        /**checking*/
       db.close();
        ArrayList<UserDetail>  userDetailList=new ArrayList<UserDetail>();
        userDetailList=   getUserDetail();
    }


    public void deleteUserDetail(String Names){
        System.out.println("in delete****");
     //   SQLiteDatabase db=new DatabseHelper(this).getWritableDatabase();
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USERDETAIL + " WHERE " + COLUMN_NAME + "=\"" + Names + "\";");
        db.close();
    }
   /* public void open(){
        db.open();
    }

    public void close(){
        db.close()
    }*/
    public ArrayList<UserDetail> getUserDetail(){
        ArrayList<UserDetail>  userDetailList=new ArrayList<UserDetail>();
   //    String dbString="";

        System.out.println("getUserDetail 1");


        System.out.println("    getCountRows() "+    getCountRows());
        SQLiteDatabase db=this.getReadableDatabase();
        String query ="SELECT * FROM "+TABLE_USERDETAIL;
        Cursor c=db.rawQuery(query,null);
        System.out.println("getUserDetail 2");
        if (c.moveToFirst()) {
            do {
                UserDetail userDetail = new UserDetail();
                if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null)
                    userDetail.setNameReason(c.getString(c.getColumnIndex(COLUMN_NAME)));
                if (c.getString(c.getColumnIndex(COLUMN_IMAGE1)) != null)
                    userDetail.setImage1(Uri.parse(c.getString(c.getColumnIndex(COLUMN_IMAGE1))));
                if (c.getString(c.getColumnIndex(COLUMN_IMAGE2)) != null)
                    userDetail.setImage2(Uri.parse(c.getString(c.getColumnIndex(COLUMN_IMAGE2))));

                if (c.getString(c.getColumnIndex(COLUMN_FLOATINGTEXT)) != null)
                    userDetail.setFloatingText(c.getString(c.getColumnIndex(COLUMN_FLOATINGTEXT)));

                if (c.getString(c.getColumnIndex(COLUMN_MUSICPATH)) != null)
                    userDetail.setMusic(Uri.parse(c.getString(c.getColumnIndex(COLUMN_MUSICPATH))));
                if (c.getString(c.getColumnIndex(COLUMN_MUSICNAME)) != null)
                    userDetail.setMusicName(c.getString(c.getColumnIndex(COLUMN_MUSICNAME)));

                userDetail.setDuration(c.getInt(c.getColumnIndex(COLUMN_DURATION)));
                userDetail.setLoopSlideShow(c.getInt(c.getColumnIndex(COLUMN_lOOPTIMES)));
                userDetailList.add(userDetail);
            }while(c.moveToNext());
        }//if
       System.out.println("after adding in db");
        c.close();
        db.close();
if(getCountRows()!=0)
    System.out.println("data received succefully");
    else
    System.out.println("NOOOOOOOOOOOOOO data received ");


        return userDetailList;

    }
   private int getCountRows(){
       int count=0;
       SQLiteDatabase db=this.getReadableDatabase();
       String query ="SELECT * FROM "+TABLE_USERDETAIL;
       Cursor c=db.rawQuery(query,null);

           count=c.getCount();
       db.close();
       return count;
   }
}
