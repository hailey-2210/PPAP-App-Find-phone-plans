/*
package application.ppap_findphoneplans.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;

import application.ppap_findphoneplans.models.Product;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class ProductDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_PRODUCTS = "products";
    public static final String TABLE_RECENT = "recent";
    public static final String TABLE_FAVORITE = "favorite";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE ="type";
    public static final String COLUMN_PRODUCT_ID = "productId";
    public static final String COLUMN_NAME = "productName";
    public static final String COLUMN_PROVIDER = "provider";
    public static final String COLUMN_CALL = "call";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_INT_CALL = "intCall";
    public static final String COLUMN_INFO = "info";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_PHONE = "phoneName";
    public static final String COLUMN_PHONE_BRAND = "phoneBrand";
    public static final String COLUMN_UPFRONT = "upfront";

    private static final String DATABASE_NAME = "products";
    private static final int DATABASE_VERSION = 1;
    private String DATABASE_PATH = null;
    private SQLiteDatabase db;

    private static final String DATABASE_PRODUCT_CREATE = "CREATE TABLE "
            + TABLE_PRODUCTS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TYPE + " TEXT NOT NULL,"
            + COLUMN_PRODUCT_ID + "TEXT NOT NULL,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_PROVIDER + " TEXT NOT NULL,"
            + COLUMN_CALL + " TEXT,"
            + COLUMN_DATA + " DOUBLE,"
            + COLUMN_INT_CALL + " TEXT,"
            + COLUMN_INFO + " TEXT,"
            + COLUMN_PRICE + "DOUBLE NOT NULL,"
            + COLUMN_LINK + " TEXT,"
            + COLUMN_PHONE + " TEXT,"
            + COLUMN_PHONE_BRAND + " TEXT,"
            + COLUMN_UPFRONT + "DOUBLE"
            + ");";

    private static final String DATABASE_RECENT_CREATE = "CREATE TABLE "
            + TABLE_RECENT + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PRODUCT_ID + "TEXT NOT NULL,"
            + COLUMN_TYPE + "TEXT NOT NULL,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_PROVIDER + " TEXT NOT NULL,"
            + COLUMN_PRICE + "DOUBLE NOT NULL,"
            + COLUMN_PHONE + " TEXT"
            + ");";

    private static final String DATABASE_FAVORITE_CREATE = "CREATE TABLE "
            + TABLE_FAVORITE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TYPE + "TEXT NOT NULL,"
            + COLUMN_PRODUCT_ID + "TEXT NOT NULL,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_PROVIDER + " TEXT NOT NULL,"
            + COLUMN_PRICE + "DOUBLE NOT NULL,"
            + COLUMN_PHONE + " TEXT"
            + ");";

    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_PATH = getDatabaseFolder()+DATABASE_NAME+".sqlite";
        db = getWritableDatabase();
    }

    public static String getDatabaseFolder() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/"+PACKAGE_NAME+"/databases/";
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        try {
            if (db != null) {
                if (db.isOpen()) {
                    return db;
                }
            }
            return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public synchronized void close() {
        if (db != null) {
            db.close();
            db = null;
        }
        super.close();
    }


    // Database creation sql statement
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_PRODUCT_CREATE);
        db.execSQL(DATABASE_RECENT_CREATE);
        db.execSQL(DATABASE_FAVORITE_CREATE);
    }

    // Database update
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ProductDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }

}
*/
