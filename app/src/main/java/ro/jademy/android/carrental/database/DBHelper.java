package ro.jademy.android.carrental.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ro.jademy.android.carrental.model.Car;
import ro.jademy.android.carrental.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper INSTANCE;

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "CarRental.db";

    // table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CARS = "cars";

    // common column names
    private static final String COL_COMMON_ID = "id";

    // cars table column names
    private static final String COL_CARS_MAKE = "make";
    private static final String COL_CARS_MODEL = "model";
    private static final String COL_CARS_IMAGE = "image";

    // users table column names
    private static final String COL_USERS_NAME = "name";
    private static final String COL_USERS_EMAIL = "email";
    private static final String COL_USERS_PASSWORD = "password";

    // create table statements

    // cars create table statement
    private static final String CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS + " (" +
            COL_COMMON_ID + " integer PRIMARY KEY, " +
            COL_CARS_MAKE + " text, " +
            COL_CARS_MODEL + " text," +
            COL_CARS_IMAGE + " text" + ")";

    // users create table statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COL_COMMON_ID + " integer PRIMARY KEY, " +
            COL_USERS_NAME + " text, " +
            COL_USERS_EMAIL + " text," +
            COL_USERS_PASSWORD + " text" + ")";


    // drop table statements

    // cars drop table statement
    private static final String DROP_TABLE_CARS = "DROP TABLE IF EXISTS " + TABLE_CARS;

    // users drop table statement
    private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;

    public static synchronized DBHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DBHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CARS);

        // add some entries
        insertCar(db, "Dacia", "Duster", "duster_01");
        insertCar(db, "Dacia", "Logan", "logan_01");
        insertCar(db, "Dacia", "Sandero", "sandero_01");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CARS);
        db.execSQL(DROP_TABLE_USERS);

        onCreate(db);
    }

    public long insertCar(SQLiteDatabase db, String make, String model, String image) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CARS_MAKE, make);
        contentValues.put(COL_CARS_MODEL, model);
        contentValues.put(COL_CARS_IMAGE, image);

        return db.insert(TABLE_CARS, null, contentValues);
    }

    // cars table methods

    public long insertCar(String make, String model, String image) {

        SQLiteDatabase db = this.getWritableDatabase();

        return insertCar(db, make, model, image);
    }

    public int updateCar(Integer id, String make, String model, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CARS_MAKE, make);
        contentValues.put(COL_CARS_MODEL, model);
        contentValues.put(COL_CARS_IMAGE, image);

        return db.update(TABLE_CARS, contentValues, "id = ? ", new String[]{Integer.toString(id)});
    }

    public Integer deleteCar(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_CARS, "id = ? ", new String[]{Integer.toString(id)});
    }

    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_CARS, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String make = res.getString(res.getColumnIndex(COL_CARS_MAKE));
            String model = res.getString(res.getColumnIndex(COL_CARS_MODEL));
            String image = res.getString(res.getColumnIndex(COL_CARS_IMAGE));

            Car car = new Car(make, model, image);
            carList.add(car);

            res.moveToNext();
        }
        return carList;
    }

    // users table methods

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_USERS_NAME, user.getName());
        values.put(COL_USERS_EMAIL, user.getEmail());
        values.put(COL_USERS_PASSWORD, user.getPassword());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }


    public List<User> getAllUsers() {
        // array of columns to fetch
        String[] columns = {
                COL_COMMON_ID,
                COL_USERS_EMAIL,
                COL_USERS_NAME,
                COL_USERS_PASSWORD
        };

        String sortOrder = COL_USERS_NAME + " ASC";
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, columns, null, null, null, null, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_COMMON_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COL_USERS_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COL_USERS_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COL_USERS_PASSWORD)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_USERS_NAME, user.getName());
        values.put(COL_USERS_EMAIL, user.getEmail());
        values.put(COL_USERS_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USERS, values, COL_COMMON_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USERS, COL_COMMON_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String email) {

        String[] columns = {
                COL_COMMON_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_USERS_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

    public boolean checkUser(String email, String password) {

        String[] columns = {
                COL_COMMON_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USERS_EMAIL + " = ?" + " AND " + COL_USERS_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        return cursorCount > 0;
    }
}