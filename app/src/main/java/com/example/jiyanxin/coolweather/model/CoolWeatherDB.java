package com.example.jiyanxin.coolweather.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.jiyanxin.coolweather.db.CoolWeatherOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.CheckedInputStream;

/**
 * Created by JIYANXIN on 2017/4/1.
 */

public class CoolWeatherDB {
    /**
     * 数据库名
     */

    public static final String DB_NAME = "cool_weather";
    /**
     * 数据库版本
     */

    public static final int VERSION = 1;

    public static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;
    /**
     * 将构造方法私有化
     */

    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /**获取CoolWeatherDB的实例
     */

    public synchronized static CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }
    /**
     * 将Province实例存储到数据库
     */
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }
    /**
     * 从数据库读取全国的所有的省份信息。
     */

    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                Province province =new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("provice_name")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while(cursor.moveToNext());
        }
        return  list;
    }
    /**
     * 将city实例存储到数据库
     */
    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getcityName());
            values.put("city_code",city.getcityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }
    /**
     * 从数据库读取某省下所有的城市信息
     */

        public List<City> loadCities(int provinceId){
            List<City> list = new ArrayList<>();
            Cursor cursor = db.query("City",null,"province_id = ?",
                    new String[] {String.valueOf(provinceId)},null,null,null);
            if (cursor.moveToFirst()){
                do{
                    City city = new City();
                    city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    city.setcityName(cursor.getString(cursor.getColumnIndex("city_name")));
                    city.setcityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                    city.setProvinceId(provinceId);
                    list.add(city);
                }while(cursor.moveToNext());
            }
            return list;
        }
    /**
     * 将country实例存储到数据库。
     */

    public void saveCounty(County county){
        if (county != null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("County",county.getCityId());
            db.insert("County",null,values);

        }
    }
    /**
     * 从数据库读取某城市下所有县的信息。
     */

    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<>();
        Cursor cursor = db.query("County",null,"city_id = ?",
                new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("country_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("country_code")));
                county.setCityId(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }
        return list;
    }
    /**
     *
     */
}
