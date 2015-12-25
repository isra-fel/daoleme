package cn.edu.fudan.daoleme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DeliveryDBHelper extends SQLiteOpenHelper{

	public static final String TABLE_NAME = "delivery";
	public static final String KEY_ID = "_id";
	public static final String KEY_EXPRESS = "express";
	public static final String KEY_IS_PINNED = "pinned";
	public static final String KEY_IS_RECEIVED = "received";
	public static final String KEY_STATE = "state";
	private static final String CREATE_SQL = "create table " + 
		TABLE_NAME + "(" +
		KEY_ID + " integer primary key, " +
		KEY_EXPRESS + " text, " +
		KEY_IS_PINNED + " integer, " +
		KEY_IS_RECEIVED + " integer, " +
		KEY_STATE + " text)";

	public DeliveryDBHelper(Context context){
		super(context, "localDb.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL(CREATE_SQL);
	}

}