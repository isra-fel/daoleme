package cn.edu.fudan.daoleme.data.pojo;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.fudan.daoleme.data.DeliveryDBHelper;

/**
 * Created by rinnko on 2015/11/13.
 */
public class Delivery {

    private String expressCompanyName;

    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setIsReceived(boolean isReceived) {
        this.isReceived = isReceived;
    }

    public List<String> getState() {
        return state;
    }

    public void setState(List<String> state) {
        this.state = state;
    }

    public void addState(String state) { this.state.add(state); }

    private String id;
    private String tag;

    public Delivery(String expressCompanyName, String id, String tag, boolean isPinned, boolean isReceived, List<String> state) {
        this.expressCompanyName = expressCompanyName;
        this.id = id;
        this.tag = tag;
        this.isPinned = isPinned;
        this.isReceived = isReceived;
        this.state = state;
    }

    public Delivery() {
        this.expressCompanyName = "";
        this.id = "";
        this.tag = "";
        this.isPinned = false;
        this.isReceived = false;
        this.state = new ArrayList<String> ();
    }

    private boolean isPinned;
    private boolean isReceived;
    private List<String> state;

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DeliveryDBHelper.KEY_ID, id);
        contentValues.put(DeliveryDBHelper.KEY_EXPRESS, expressCompanyName);
        contentValues.put(DeliveryDBHelper.KEY_IS_PINNED, isPinned() ? 1 : 0);
        contentValues.put(DeliveryDBHelper.KEY_IS_RECEIVED, isReceived() ? 1 : 0);
        //state.join('\n')
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : state) {
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        contentValues.put(DeliveryDBHelper.KEY_STATE, stringBuilder.toString());
        return contentValues;
    }
}
