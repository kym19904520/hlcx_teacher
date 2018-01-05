package com.up.study.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuyh.library.imgsel.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class CollectDao {
	private CollectSQLiteOpenHelper helper;

	public CollectDao(Context context){
		helper=new CollectSQLiteOpenHelper(context);
	}

	public boolean add(String paperName,String typeName,int paperId){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("paperId", paperId);
		values.put("paperName", paperName);
        values.put("typeName",typeName);
		long result=db.insert("records", "paperName", values);
		LogUtils.i("ddd" + paperId);
		db.close();

		if (result > 0) {
			return true;
		}else{
			return false;
		}
	}

	public boolean delete(String paperName){
		SQLiteDatabase db=helper.getWritableDatabase();
		int result=db.delete("records", "paperName=?", new String[]{paperName});
		db.close();

		if (result>0) {
			return true;
		}else{
			return false;
		}
	}

	public boolean delete1(){
        SQLiteDatabase db=helper.getWritableDatabase();
        int records = db.delete("records", null, null);
        if (records>0) {
            return true;
        }else{
            return false;
        }
    }
	
	public boolean update(String paperName,String newMode,String typeName,String newTypeName,int paperId,int newPaperId){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("paperName", newMode);
		values.put("paperId",newPaperId);
        values.put("typeName",newTypeName);
		int result=db.update("records", values, "paperName=? and typeName=? and paperId=?", new String[]{paperName,typeName, String.valueOf(paperId)});
		db.close();

		if (result>0) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isExist(String paperName){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor cursor=db.query("records", new String[]{"paperName"}, "paperName=?", new String[]{paperName}, null, null, null);
		if (cursor.moveToNext()) {
//			Log.i("db isExist操作结果：",cursor.getInt(0) + "-----" + cursor.getString(0) + "------" + cursor.getString(1));
			return true;
		}
		cursor.close();
		db.close();
		return false;
	}

	public List<ZJBean> findAll(){
		SQLiteDatabase db=helper.getReadableDatabase();
		List<ZJBean> infos=new ArrayList<>();
		Cursor cursor=db.query("records", new String[]{"paperName","typeName","paperId"}, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String paperName=cursor.getString(0);
            String typeName=cursor.getString(1);
			int paperId=cursor.getInt(2);
			ZJBean info=new ZJBean();
			info.setPaperName(paperName);
            info.setTypeName(typeName);
			info.setPaperId(paperId);
			infos.add(info);
		}
		cursor.close();
		db.close();
		return infos;
	}

	public boolean deleteDatabase(Context context){
		return context.deleteDatabase("records");
	}

}
