package cn.edu.fudan.daoleme.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by rinnko on 2015/10/20.
 */
public class DeliveryProvider extends ContentProvider {

    private static final String TAG = "DeliveryProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://cn.edu.fudan.daoleme.deliveryDB/" + DeliveryDBHelper.TABLE_NAME);

    private static final UriMatcher URI_MATCHER;
    private static final int TABLE_ID = 1;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI("cn.edu.fudan.daoleme", DeliveryDBHelper.TABLE_NAME, TABLE_ID);
    }

    private DeliveryDBHelper mDBHelper;

    @Override
    public boolean onCreate() {
        mDBHelper = new DeliveryDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // match uri

        queryBuilder.setTables(DeliveryDBHelper.TABLE_NAME);

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), CONTENT_URI);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        if (URI_MATCHER.match(uri) == TABLE_ID) {
            return "vnd.android.cursor.dir/vnd.daoleme.m";
        }
        return "vnd.android.cursor.item/vnd.daoleme.m";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        String nullColumnHack = null;

        // match uri

        long id = db.insert(DeliveryDBHelper.TABLE_NAME, nullColumnHack, values);

        if (id < 0) {
            return null; // insert failed
        }

        Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);

        getContext().getContentResolver().notifyChange(insertedId, null);

        return insertedId;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        // match uri

        if (selection == null) {
            selection = "1"; // always true
        }

        int deleteCount = db.delete(DeliveryDBHelper.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null); // notify changes to obsevers

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        // match uri

        int updateCount = db.update(DeliveryDBHelper.TABLE_NAME, values, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }

}
