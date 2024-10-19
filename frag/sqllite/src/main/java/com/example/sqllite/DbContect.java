package com.example.sqllite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbContect extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db";
    private static final String TABLE_NAME = "user_info";
    private static final int DB_VERSION = 1;
    private static DbContect mHelper = null;
    private SQLiteDatabase mRDB;
    private SQLiteDatabase mWDB;






    DbContect(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //利用单例模式获取数据库帮助器的唯一实例
    public static DbContect getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new DbContect(context);
        }
        return (mHelper);
    }









    //打开数据库的读链接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    //打开数据库的写链接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    //关闭数据库链接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
        }

        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }









    //创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTs " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " age INTEGER NOT NULL," +
                " height LONG NOT NULL," +
                " Weight FLOAT NOT NULL," +
                " married INTEGER NOT NULL);";
        db.execSQL(sql);

    }
    //版本更新时候会执行（DB_VERSION=1,2,3......）增加两列属性
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="ALTER TABLE "+TABLE_NAME+" ADD COLUMN phone VARCHAR;";
        db.execSQL(sql);
        sql="ALTER TABLE "+TABLE_NAME+" ADD COLUMN password VARCHAR;";
        db.execSQL(sql);
    }










    //添加
    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.name);
        values.put("age", user.age);
        values.put("height", user.height);
        values.put("weight", user.weight);
        values.put("married", user.married);
/*      执行插入记录动作，该语句返回插入记录的行号
        如果第三个参数values为Nu1l或者元素个数为0， 由于insert()方法要求必须添加一条除了主键之外其它字段为Null值的记录，
        为了满足SQL语法的需要，insert语句必须给定一个字段名，如:insert into person(name)values(Null),
        倘若不给定字段名 ， insert语句就成了这样: insert into person()values()，显然这不满足标准SQL的语法。
        如果第三个参数values 不为Null并且元素的个数大于0 ，可以把第二个参数设置为null 。*/
        return mWDB.insert(TABLE_NAME, null, values);
    }

    //删除
    public long deleteByName(String name) {
        //mWDB.delete(TABLE_NAME,"1=1",null);    删除所有
        return mWDB.delete(TABLE_NAME, "name=?", new String[]{name});
    }

    //更改
    public long update(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.name);
        values.put("age", user.age);
        values.put("height", user.height);
        values.put("weight", user.weight);
        values.put("married", user.married);
        return mWDB.update(TABLE_NAME, values, "name=?", new String[]{user.name});
    }

    //查询所有
    public List<User> queryAll() {
        List<User> list = new ArrayList<>();
        //执行记录查询动作该语句返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, null, null, null, null, null);
        //循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            User user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.height = cursor.getLong(3);
            user.weight = cursor.getFloat(4);
            //SQLite没有布尔型，用0表示false，用1表示true
            user.married = (cursor.getInt(5) == 0 ? false : true);
            list.add(user);

        }
        return list;
    }

    //查询 按名称
    public List<User> queryBYName(String name) {
        List<User> list = new ArrayList<>();
        //执行记录查询动作该语句返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, "name=?", new String[]{name}, null, null, null);
        //循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            User user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.height = cursor.getLong(3);
            user.weight = cursor.getFloat(4);
            //SOLite没有布尔型，用0表示false，用1表示true
            user.married = (cursor.getInt(5) == 0 ? false : true);
            list.add(user);

        }
        return list;
    }
}





