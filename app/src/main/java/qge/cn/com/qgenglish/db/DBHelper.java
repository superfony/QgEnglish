package qge.cn.com.qgenglish.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import qge.cn.com.qgenglish.iciba.WordBean;

/**
 * 数据库
 */
@SuppressWarnings("all")
class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "app_data.db";

    private static final int DB_VERSION = 1;

    public static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement," + "author text,"
            + "price real," + "pages integer," + "name text)";

    DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    DBHelper(Context context, String name) {
        super(context, name, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 在此位置创建数据库表
        // create(Behavior.class);
        // db.execSQL(CREATE_BOOK);// 执行建表语句
        //Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
        create(Behavior.class, db);
        create(User.class, db);
        create(WordBean.class, db);
        Log.i("", "");
    }

    private String getTableName(Class<?> cla) {
        Annotation[] annotations = cla.getDeclaredAnnotations();
        if (annotations == null || annotations.length == 0) {
            throw new IllegalStateException("you must use Table annotation for bean");
        }
        String tableName = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof Table)
                tableName = ((Table) annotation).tableName();
        }
        if (TextUtils.isEmpty(tableName)) {
            throw new IllegalStateException("you must use Table annotation for bean");
        }
        return tableName;
    }

    public void create(Class<?> cls, SQLiteDatabase db) {
        String tableName = getTableName(cls);
        create(tableName, cls, db);
    }

    public void create(String tableName, Class<?> cls, SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ";
        String table = "";
        String primary = "";
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                boolean isAutoincrement = primaryKey.autoincrement();
                String name = primaryKey.column();
                primary = String.format(name + " %s primary key " +
                        (isAutoincrement ? "autoincrement," : ","), getTypeString(field));
            } else if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                String name = column.column();
                table = table + String.format(name + " %s,", getTypeString(field));
            }
        }
        if (TextUtils.isEmpty(table))
            return;
        sql = sql + "(" + primary + table.substring(0, table.length() - 1) + ")";
        db.execSQL(sql);
    }

    public boolean update(Object obj, String tableName, String where, String[] args) {
        if (!isExist(tableName)) {
            return false;
        }
        Class<?> cls = obj.getClass();
        SQLiteDatabase db;
        ContentValues values = new ContentValues();
        Field[] fields = cls.getDeclaredFields();
        try {
            db = getWritableDatabase();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);

                    String name = column.column();
                    Object object = field.get(obj);
                    values.put(name, object == null ? "" : object.toString());
                } else if (field.isAnnotationPresent(PrimaryKey.class)) {
                    PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                    boolean isAutoincrement = primaryKey.autoincrement();
                    String name = primaryKey.column();
                    if (!isAutoincrement) {
                        Object object = field.get(obj);
                        values.put(name, object == null ? "" : object.toString());
                    }
                }
            }
            db.update(tableName, values, where, args);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Object obj, String where, String... args) {
        Class<?> cls = obj.getClass();
        String tableName = getTableName(cls);
        return update(obj, tableName, where, args);
    }

    public boolean insert(Object obj, String tableName) {
        if (!isExist(tableName)) {
            return false;
        }
        Class<?> cls = obj.getClass();
        SQLiteDatabase db;
        ContentValues values = new ContentValues();
        //通过class取出当前class的所有字段
        Field[] fields = cls.getDeclaredFields();
        try {
            db = getWritableDatabase();
            for (Field field : fields) {
                field.setAccessible(true);
                //判断该程序元素上是否包含指定类型的注解，存在则返回true，否则返回false.
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    String name = column.column();
                    Object object = field.get(obj);
                    values.put(name, object == null ? "" : object.toString());
                } else if (field.isAnnotationPresent(PrimaryKey.class)) {
                    PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                    boolean isAutoincrement = primaryKey.autoincrement();
                    String name = primaryKey.column();
                    if (!isAutoincrement) {
                        Object object = field.get(obj);
                        values.put(name, object == null ? "" : object.toString());
                    }
                }
            }
            return db.insert(tableName, null, values) != 0;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }


    /**
     * 事务插入
     *
     * @param lists     数据
     * @param tableName tableName
     * @return <T>
     */
    private <T> boolean insertList(List<T> lists, String tableName) {
        if (!isExist(tableName)) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (Object obj : lists) {
                Class<?> cls = obj.getClass();
                ContentValues values = new ContentValues();
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Column.class)) {
                        Column column = field.getAnnotation(Column.class);
                        String name = column.column();
                        Object object = field.get(obj);
                        values.put(name, object == null ? "" : object.toString());
                    } else if (field.isAnnotationPresent(PrimaryKey.class)) {
                        PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                        boolean isAutoincrement = primaryKey.autoincrement();
                        String name = primaryKey.column();
                        if (!isAutoincrement) {
                            Object object = field.get(obj);
                            values.put(name, object == null ? "" : object.toString());
                        }
                    }
                }
                db.insert(tableName, "", values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db.isOpen()) {
                db.endTransaction();
            }
        }
        return true;
    }


    public boolean insert(Object obj) {
        Class<?> cls = obj.getClass();
        String tableName = getTableName(cls);
        return isExist(tableName) && insert(obj, tableName);
    }


    public <T> boolean insertTransaction(List<T> list, String tableName) {
        return isExist(tableName) && insertList(list, tableName);
    }

    public long getCount(Class<?> cls) {
        String tableName = getTableName(cls);
        if (!isExist(tableName))
            return -1;
        SQLiteDatabase db;
        Cursor cursor = null;
        try {
            db = getReadableDatabase();
            cursor = db.rawQuery(String.format("select count(*) from %s", tableName), null);
            cursor.moveToFirst();
            long count = cursor.getLong(0);
            cursor.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return 0;
    }

    /**
     * 判断数据是否存在
     * 第二个参数填写具体的 where条件(where 字段名="");
     *
     * @return isDataExist
     */
    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public boolean isDataExist(String tableName, String where) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        try {
            String sql = String.format("SELECT * FROM %s %s", tableName, where);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                return true;// //有城市在数据库已存在，返回true
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return false;
    }

    // 删除数据表
    public boolean clear(String tableName) {
        if (!isExist(tableName)) {
            return false;
        }
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
            db.execSQL(String.format("DELETE from '%s'", tableName));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return false;
    }

    /**
     * 更新字段
     *
     * @param table  表
     * @param column 字段
     * @param value  要更新的值
     * @return 成功或者失败
     */
    public boolean update(String table, String column, Object value, String where) {
        if (!isExist(table)) {
            return false;
        }
        SQLiteDatabase db;
        try {
            db = getReadableDatabase();
            String sql = String.format("UPDATE %s SET %s='%s' %s", table, column, value.toString(), where);
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 新增字段
     *
     * @param tableName  tableName
     * @param columnName columnName
     * @param type       type
     * @return true or false
     */
    private boolean alter(String tableName, String columnName, String type) {
        if (!isExist(tableName)) return false;
        SQLiteDatabase db;
        try {
            db = getWritableDatabase();
            db.execSQL(String.format("ALTER TABLE %s ADD %s %s", tableName, columnName, type));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 新增字段
     *
     * @param cls cls
     * @return true or false
     */
    public boolean alter(Class<?> cls) {
        String tableName = getTableName(cls);
        if (!isExist(tableName))
            return false;
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                String name = column.column();
                if (!isColumnExist(tableName, name)) {
                    alter(tableName, name, getTypeString(field));
                }
            }
        }
        return false;
    }

    private boolean isColumnExist(String tableName, String columnName) {
        boolean result = false;
        Cursor cursor = null;
        SQLiteDatabase db;
        try {
            db = getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0"
                    , null);
            result = cursor != null && cursor.getColumnIndex(columnName) != -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return result;
    }

    /**
     * 判断数据表是否存在
     *
     * @param tableName tableName
     * @return 判断数据表是否存在
     */
    public boolean isExist(String tableName) {
        if (TextUtils.isEmpty(tableName)) {
            return false;
        }
        boolean exits = false;
        SQLiteDatabase db;
        Cursor cursor = null;
        String sql = "select * from sqlite_master where name=" + "'" + tableName + "'";
        try {
            db = getReadableDatabase();
            cursor = db.rawQuery(sql, null);
            if (cursor.getCount() != 0) {
                exits = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return exits;
    }

    private String getTypeString(Field field) {
        Class<?> type = field.getType();
        if (type.equals(int.class)) {
            return "integer";
        } else if (type.equals(String.class)) {
            return "text";
        } else if (type.equals(long.class)) {
            return "long";
        } else if (type.equals(float.class)) {
            return "feal";
        } else if (type.equals(double.class)) {
            return "feal";
        }
        return "varchar";
    }

    public <T> List<T> get(Class<T> cls) {
        return get(cls, null, null, null, 0, 0, 0, false);
    }

    public <T> List<T> get(Class<T> cls, String where) {
        return get(cls, where, null, null, 0, 0, 0, false);
    }

    public <T> List<T> get(Class<T> cls, int limit, int limit1) {
        return get(cls, null, null, null, limit, limit1, 0, true);
    }

    public <T> List<T> getWithOffset(Class<T> cls, int limit, int offset) {
        return get(cls, null, null, null, limit, 0, offset, false);
    }

    public <T> List<T> get(Class<T> cls, int limit, int limit1, int offset) {
        return get(cls, null, null, null, limit, limit1, offset, true);
    }

    //limit (curPage-1)*pageSize,pageSize
    public <T> List<T> get(Class<T> cls, String where, String orderColumn, String orderType, int limit, int limit1, int offset, boolean isPage) {
        String tableName = getTableName(cls);
        if (!isExist(tableName))
            return null;
        List<T> list = new ArrayList<>();
        SQLiteDatabase db;
        Cursor cursor = null;
        try {
            db = getReadableDatabase();
            String sql = String.format("SELECT * from %s", tableName);
            String whereAre = TextUtils.isEmpty(where) ? null : " " + where;
            String orderBy = TextUtils.isEmpty(orderColumn) ? null : String.format(" ORDER BY %s %s", orderColumn, orderType);
            // String limitStr = limit == 0 ? null : String.format(" limit %s offset %s", String.valueOf(limit), String.valueOf(offset));
            String limitStr = null;
            String limitStr1 = null;
            if (isPage) {
                limitStr = limit == 0 ? " limit 0" : String.format(" limit %s ", String.valueOf(limit));
                limitStr1 = limit1 == 0 ? null : String.format(" , %s", String.valueOf(limit1));
            }
            String offsetStr = offset == 0 ? null : String.format(" offset %s", String.valueOf(offset));
            StringBuilder sb = new StringBuilder();
            sb.append(sql);
            sb.append(TextUtils.isEmpty(limitStr) ? "" : limitStr);
            sb.append(TextUtils.isEmpty(limitStr1) ? "" : limitStr1);
            sb.append(TextUtils.isEmpty(offsetStr) ? "" : offsetStr);
            sb.append(TextUtils.isEmpty(whereAre) ? "" : whereAre);
            sb.append(TextUtils.isEmpty(orderBy) ? "" : orderBy);
            sql = sb.toString();
            cursor = db.rawQuery(sql, null);
            Field[] fields = cls.getDeclaredFields();
            while (cursor.moveToNext()) {
                T t = cls.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String name = "";
                    if (field.isAnnotationPresent(Column.class))
                        name = field.getAnnotation(Column.class).column();
                    else if (field.isAnnotationPresent(PrimaryKey.class))
                        name = field.getAnnotation(PrimaryKey.class).column();
                    if (!TextUtils.isEmpty(name)) {
                        Class<?> type = field.getType();
                        if (type.equals(int.class)) {
                            field.set(t, cursor.getInt(cursor.getColumnIndex(name)));
                        } else if (type.equals(String.class)) {
                            field.set(t, cursor.getString(cursor.getColumnIndex(name)));
                        } else if (type.equals(long.class)) {
                            field.set(t, cursor.getLong(cursor.getColumnIndex(name)));
                        } else if (type.equals(float.class)) {
                            field.set(t, cursor.getFloat(cursor.getColumnIndex(name)));
                        } else if (type.equals(double.class)) {
                            field.set(t, cursor.getDouble(cursor.getColumnIndex(name)));
                        }
                    }
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 删除数据
     *
     * @param cls cls
     * @return 成功或者失败
     */
    public boolean delete(Class<?> cls, String where, String... args) {
        String tableName = getTableName(cls);
        if (!isExist(tableName))
            return false;
        SQLiteDatabase db;
        try {
            db = getWritableDatabase();
            int i = db.delete(tableName, where, args);
            return i > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // 对 WordBean 的操作


    //从单词库中获得某个单词的信息，如果词库中没有改单词，那么返回null
    public WordBean getWordFromDict(String searchedWord) {
        WordBean w = null;
        String[] columns = new String[]{"key",
                "psE", "pronE", "psA", "pronA", "acceptation"};
        String[] strArray = new String[6];
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("word", columns, "key=?", new String[]{searchedWord}, null, null, null);
        while (cursor.moveToNext()) {
            for (int i = 0; i < strArray.length; i++) {
                strArray[i] = cursor.getString(cursor.getColumnIndex(columns[i]));
            }
            w = new WordBean(strArray[0], strArray[1], strArray[2], strArray[3], strArray[4], strArray[5]);
        }
        cursor.close();
        return w;
    }

    /**
     * 根据单词获取某一个具体的字段值
     * backValue 是表字段值
     */
    public String getCursorValue(String searchedWord, String backValue) {

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("word", new String[]{backValue}, "key=?", new String[]{searchedWord}, null, null, null);
        if (cursor.moveToNext() == false) {
            cursor.close();
            return null;
        }
        String str = cursor.getString(cursor.getColumnIndex(backValue));
        cursor.close();
        return str;

    }

    /**
     *
     */

    //判断数据库中是否存在某个单词
    public boolean isWordExist(String word) {
        Cursor cursor = null;
        SQLiteDatabase db = getWritableDatabase();
        try {
            cursor = db.query("word", new String[]{"key"}, "key=?", new String[]{word}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    /**********************
     * 对word的操作
     **********************/

    public long getWordCount() {

        SQLiteDatabase db;
        Cursor cursor = null;
        try {
            db = getReadableDatabase();
            cursor = db.rawQuery(String.format("select count(*) from %s", "device_id"), null);
            cursor.moveToFirst();
            long count = cursor.getLong(0);
            cursor.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return 0;
    }

}


//    在Android中查询数据是通过Cursor类来实现的，当我们使用SQLiteDatabase.query()方法时，会得到一个Cursor对象，Cursor指向的就是每一条数据。它提供了很多有关查询的方法，具体方法如下：
//
//    public Cursor query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit);
//    各个参数的意义说明：
//    ①table:表名称
//    ②columns:列名称数组
//    ③selection:条件字句，相当于where
//    ④selectionArgs:条件字句，参数数组
//    ⑤groupBy:分组列
//    ⑥having:分组条件
//    ⑦orderBy:排序列
//    ⑧limit:分页查询限制
//    ⑨Cursor:返回值，相当于结果集ResultSet


// 对 word_db数据库的操作


