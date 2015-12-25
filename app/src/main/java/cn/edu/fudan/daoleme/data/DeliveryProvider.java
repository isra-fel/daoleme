package cn.edu.fudan.daoleme.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.daoleme.data.pojo.Delivery;

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

    public static Delivery queryById(ContentResolver contentResolver, String deliveryId) {
        Delivery result = null;
        Cursor cursor = contentResolver.query(CONTENT_URI, new String[]{DeliveryDBHelper.KEY_ID, DeliveryDBHelper.KEY_EXPRESS, DeliveryDBHelper.KEY_IS_PINNED,
                        DeliveryDBHelper.KEY_IS_RECEIVED, DeliveryDBHelper.KEY_TAG, DeliveryDBHelper.KEY_STATE},
                "_id = ?", new String[]{deliveryId}, "");
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                int indexId = cursor.getColumnIndex(DeliveryDBHelper.KEY_ID);
                int indexExpress = cursor.getColumnIndex(DeliveryDBHelper.KEY_EXPRESS);
                int indexPinned = cursor.getColumnIndex(DeliveryDBHelper.KEY_IS_PINNED);
                int indexReceived = cursor.getColumnIndex(DeliveryDBHelper.KEY_IS_RECEIVED);
                int indexTag = cursor.getColumnIndex(DeliveryDBHelper.KEY_TAG);
                int indexState = cursor.getColumnIndex(DeliveryDBHelper.KEY_STATE);

                Delivery delivery = new Delivery();
                delivery.setId(cursor.getString(indexId));
                delivery.setExpressCompanyName(cursor.getString(indexExpress));
                delivery.setIsPinned(cursor.getInt(indexPinned) == 1);
                delivery.setIsReceived(cursor.getInt(indexReceived) == 1);
                delivery.setTag(cursor.getString(indexTag));
                delivery.setState(new ArrayList<String>());
                TextUtils.SimpleStringSplitter stringSplitter = new TextUtils.SimpleStringSplitter('\n');
                stringSplitter.setString(cursor.getString(indexState));
                for (String s : stringSplitter) {
                    delivery.addState(s);
                }

                result = delivery;
            } else {
                result = null;
            }
            cursor.close();
        }
        return result;
    }

    public static List<Delivery> queryAll(ContentResolver contentResolver) {
        List<Delivery> result = new ArrayList<>();
        Cursor cursor = contentResolver.query(CONTENT_URI, new String[]{DeliveryDBHelper.KEY_ID, DeliveryDBHelper.KEY_EXPRESS, DeliveryDBHelper.KEY_IS_PINNED,
                        DeliveryDBHelper.KEY_IS_RECEIVED, DeliveryDBHelper.KEY_TAG, DeliveryDBHelper.KEY_STATE},
                "", new String[]{}, "");
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                int indexId = cursor.getColumnIndex(DeliveryDBHelper.KEY_ID);
                int indexExpress = cursor.getColumnIndex(DeliveryDBHelper.KEY_EXPRESS);
                int indexPinned = cursor.getColumnIndex(DeliveryDBHelper.KEY_IS_PINNED);
                int indexReceived = cursor.getColumnIndex(DeliveryDBHelper.KEY_IS_RECEIVED);
                int indexTag = cursor.getColumnIndex(DeliveryDBHelper.KEY_TAG);
                int indexState = cursor.getColumnIndex(DeliveryDBHelper.KEY_STATE);
                while (cursor.moveToNext()) {
                    Delivery delivery = new Delivery();
                    delivery.setId(cursor.getString(indexId));
                    delivery.setExpressCompanyName(cursor.getString(indexExpress));
                    delivery.setIsPinned(cursor.getInt(indexPinned) == 1);
                    delivery.setIsReceived(cursor.getInt(indexReceived) == 1);
                    delivery.setTag(cursor.getString(indexTag));
                    delivery.setState(new ArrayList<String>());
                    TextUtils.SimpleStringSplitter stringSplitter = new TextUtils.SimpleStringSplitter('\n');
                    stringSplitter.setString(cursor.getString(indexState));
                    for (String s : stringSplitter) {
                        delivery.addState(s);
                    }
                    result.add(delivery);
                }
            }
            cursor.close();
        }
        return result;
    }

    public static List<Delivery> queryAllPinned(ContentResolver contentResolver) {
        List<Delivery> result = new ArrayList<>();
        Cursor cursor = contentResolver.query(CONTENT_URI, new String[]{DeliveryDBHelper.KEY_ID, DeliveryDBHelper.KEY_EXPRESS, DeliveryDBHelper.KEY_IS_PINNED,
                        DeliveryDBHelper.KEY_IS_RECEIVED, DeliveryDBHelper.KEY_TAG, DeliveryDBHelper.KEY_STATE},
                "isPinned = ?", new String[]{}, "1");
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                int indexId = cursor.getColumnIndex(DeliveryDBHelper.KEY_ID);
                int indexExpress = cursor.getColumnIndex(DeliveryDBHelper.KEY_EXPRESS);
                int indexPinned = cursor.getColumnIndex(DeliveryDBHelper.KEY_IS_PINNED);
                int indexReceived = cursor.getColumnIndex(DeliveryDBHelper.KEY_IS_RECEIVED);
                int indexTag = cursor.getColumnIndex(DeliveryDBHelper.KEY_TAG);
                int indexState = cursor.getColumnIndex(DeliveryDBHelper.KEY_STATE);
                while (cursor.moveToNext()) {
                    Delivery delivery = new Delivery();
                    delivery.setId(cursor.getString(indexId));
                    delivery.setExpressCompanyName(cursor.getString(indexExpress));
                    delivery.setIsPinned(cursor.getInt(indexPinned) == 1);
                    delivery.setIsReceived(cursor.getInt(indexReceived) == 1);
                    delivery.setTag(cursor.getString(indexTag));
                    delivery.setState(new ArrayList<String>());
                    TextUtils.SimpleStringSplitter stringSplitter = new TextUtils.SimpleStringSplitter('\n');
                    stringSplitter.setString(cursor.getString(indexState));
                    for (String s : stringSplitter) {
                        delivery.addState(s);
                    }
                    result.add(delivery);
                }
            }
            cursor.close();
        }
        return result;
    }
}
