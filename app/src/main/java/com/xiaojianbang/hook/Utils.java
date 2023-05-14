package com.xiaojianbang.hook;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Utils {

	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			"android.permission.READ_EXTERNAL_STORAGE",
			"android.permission.WRITE_EXTERNAL_STORAGE" };

	public static void verifyStoragePermissions(Activity activity) {
		try {
			//检测是否有写的权限
			int permission = ActivityCompat.checkSelfPermission(activity,
					"android.permission.WRITE_EXTERNAL_STORAGE");
			if (permission != PackageManager.PERMISSION_GRANTED) {
				// 没有写的权限，去申请写的权限，会弹出对话框
				ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getCalc(int a, int b) {
		return a + b;
	}

	public static int getCalc(int a, int b, int c) {
		return a + b + c;
	}

	public static int getCalc(int a, int b, int c, int d) {
		return a + b + c + d;
	}

	public static String shufferMap(HashMap<String, String> map){
		Set key = map.keySet();
		Iterator it = key.iterator();
		StringBuilder result = new StringBuilder();
		while(it.hasNext()){
			String keystr = (String) it.next();
			String valuestr = (String) map.get(keystr);
			result.append(valuestr);
		}
		return result.toString();
	}

	public static String shufferMap2(Map<String, String> map){
		Set key = map.keySet();
		Iterator it = key.iterator();
		StringBuilder result = new StringBuilder();
		while(it.hasNext()){
			String keystr = (String) it.next();
			String valuestr = (String) map.get(keystr);
			result.append(valuestr);
		}
		return result.toString();
	}

	public static String myPrint(String[] strArr){
		StringBuilder sb = new StringBuilder();
		for(String str : strArr){
			sb.append(str).append("|");
		}
		return sb.toString();
	}

	public static String myPrint(Object... objArr){
		StringBuilder sb = new StringBuilder();
		for(Object obj : objArr){
			sb.append(obj).append("|");
		}
		return sb.toString();
	}

	public static String myPrint(ArrayList<Object> arrayList){
		StringBuilder sb = new StringBuilder();
		for(Object obj : arrayList){
			sb.append(obj).append("|");
		}
		return sb.toString();
	}

}
