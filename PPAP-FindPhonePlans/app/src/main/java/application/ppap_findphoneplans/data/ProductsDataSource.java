/*
package application.ppap_findphoneplans.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import application.ppap_findphoneplans.models.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ProductsDataSource {

    private SQLiteDatabase database;
    private ProductDBHelper dbHelper;
    private String[] allColumnsProducts = {ProductDBHelper.COLUMN_ID,
            ProductDBHelper.COLUMN_PRODUCT_ID, ProductDBHelper.COLUMN_TYPE, ProductDBHelper.COLUMN_NAME,
            ProductDBHelper.COLUMN_PROVIDER, ProductDBHelper.COLUMN_CALL, ProductDBHelper.COLUMN_DATA,
            ProductDBHelper.COLUMN_INT_CALL, ProductDBHelper.COLUMN_INFO, ProductDBHelper.COLUMN_PRICE,
            ProductDBHelper.COLUMN_LINK, ProductDBHelper.COLUMN_PHONE, ProductDBHelper.COLUMN_PHONE_BRAND,
            ProductDBHelper.COLUMN_UPFRONT};

    private String[] allColumnsRecent = {ProductDBHelper.COLUMN_ID,
            ProductDBHelper.COLUMN_PRODUCT_ID, ProductDBHelper.COLUMN_TYPE, ProductDBHelper.COLUMN_NAME,
            ProductDBHelper.COLUMN_PROVIDER, ProductDBHelper.COLUMN_PRICE, ProductDBHelper.COLUMN_PHONE};

    private String[] allColumnsFavorite = {ProductDBHelper.COLUMN_ID,
            ProductDBHelper.COLUMN_PRODUCT_ID, ProductDBHelper.COLUMN_TYPE, ProductDBHelper.COLUMN_NAME,
            ProductDBHelper.COLUMN_PROVIDER, ProductDBHelper.COLUMN_PRICE, ProductDBHelper.COLUMN_PHONE};

    // private String[] allColumnsEbay = {ProductDBHelper.COLUMN_ID ,ProductDBHelper.COLUMN_PRODUCT};
    public ProductsDataSource(Context context) {
        dbHelper = new ProductDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Product createProduct(String productId, String productType, String productName,
                                 String provider, String call, String data, String intCall, String price, String link,
                                 String info, String phoneName, String phoneBrand, String upfront) {

        ContentValues values = new ContentValues();
        values.put(ProductDBHelper.COLUMN_PRODUCT_ID, productId);
        values.put(ProductDBHelper.COLUMN_TYPE, productType);
        values.put(ProductDBHelper.COLUMN_NAME, productName);
        values.put(ProductDBHelper.COLUMN_PROVIDER, provider);
        values.put(ProductDBHelper.COLUMN_CALL, call);
        values.put(ProductDBHelper.COLUMN_DATA, data);
        values.put(ProductDBHelper.COLUMN_INT_CALL, intCall);
        values.put(ProductDBHelper.COLUMN_PRICE, price);
        values.put(ProductDBHelper.COLUMN_LINK, link);
        values.put(ProductDBHelper.COLUMN_INFO, info);
        values.put(ProductDBHelper.COLUMN_PHONE, phoneName);
        values.put(ProductDBHelper.COLUMN_PHONE_BRAND, phoneBrand);
        values.put(ProductDBHelper.COLUMN_UPFRONT, upfront);

        long insertId = database.insert(ProductDBHelper.TABLE_PRODUCTS, null,
                values);
        Cursor cursor = database.query(ProductDBHelper.TABLE_PRODUCTS,
                allColumnsProducts, ProductDBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Product newProduct = cursorToProduct(cursor);
        cursor.close();
        return newProduct;
    }

    public Recent createRecent(String productId, String productType, String productName, String provider, String price, String phone) {

        ContentValues values = new ContentValues();

        Cursor checkingCursor = database.rawQuery("SELECT * FROM " + ProductDBHelper.TABLE_RECENT + " WHERE " + ProductDBHelper.COLUMN_NAME + "= " + "'" + productName + "';", null);
        if (checkingCursor.moveToFirst()) {
            Recent alreadyInRecent = cursorToRecent(checkingCursor);
            checkingCursor.close();
            return alreadyInRecent;
        }
        values.put(ProductDBHelper.COLUMN_PRODUCT_ID, productId);
        values.put(ProductDBHelper.COLUMN_TYPE, productType);
        values.put(ProductDBHelper.COLUMN_NAME, productName);
        values.put(ProductDBHelper.COLUMN_PROVIDER, provider);
        values.put(ProductDBHelper.COLUMN_PRICE, price);
        values.put(ProductDBHelper.COLUMN_PHONE, phone);
        long insertId = database.insert(ProductDBHelper.TABLE_RECENT, null,
                values);

        Cursor cursor = database.query(ProductDBHelper.TABLE_RECENT,
                allColumnsRecent, ProductDBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Recent newRecent = cursorToRecent(cursor);
        cursor.close();

        return newRecent;
    }

    public Favorite createFavoriteList(String productId, String productType, String productName, String provider, String price, String phone) {

        ContentValues values = new ContentValues();

        values.put(ProductDBHelper.COLUMN_PRODUCT_ID, productId);
        values.put(ProductDBHelper.COLUMN_TYPE, productType);
        values.put(ProductDBHelper.COLUMN_NAME, productName);
        values.put(ProductDBHelper.COLUMN_PROVIDER, provider);
        values.put(ProductDBHelper.COLUMN_PRICE, price);
        values.put(ProductDBHelper.COLUMN_PHONE, phone);

        long insertId = database.insert(ProductDBHelper.TABLE_FAVORITE, null,
                values);

        Cursor cursor = database.query(ProductDBHelper.TABLE_FAVORITE,
                allColumnsFavorite, ProductDBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Favorite newFavoriteList = cursorToFavoriteList(cursor);
        cursor.close();
        return newFavoriteList;
    }

    public void deleteProduct(Product product) {
        long id = product.getId();
        System.out.println("Product deleted with id: " + id);
        database.delete(ProductDBHelper.TABLE_PRODUCTS, ProductDBHelper.COLUMN_ID
                + " = " + id, null);
    }

    public Recent getRecent(long id) {

        Cursor cursor = database.query(ProductDBHelper.TABLE_RECENT,
                allColumnsRecent, null, null, null, null, null);
        long counter = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (counter == id) {
                break;

            }
            counter++;
            cursor.moveToNext();

        }
        Recent recent = cursorToRecent(cursor);
        cursor.close();
        return recent;
    }

    public void deleteRecent(long id) {
        //long id = recent.getId();
        //System.out.println("Product "+recent.getName()+"from the recent");

        Cursor cursor = database.query(ProductDBHelper.TABLE_RECENT,
                allColumnsRecent, null, null, null, null, null);
        long counter = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (counter == id) {

                String productNameForDeletion = cursor.getString(1);
                database.delete(ProductDBHelper.TABLE_RECENT, ProductDBHelper.COLUMN_NAME
                        + " = '" + productNameForDeletion + "';", null);
                //cursor.close();

                break;

            }
            counter++;
            cursor.moveToNext();

        }
        cursor.close();
        //database.delete(ProductDBHelper.TABLE_RECENT, ProductDBHelper.COLUMN_ID
        //        + " = " + id, null);
    }

    public void deleteFavoriteList(long id) {


        Cursor cursor = database.query(ProductDBHelper.TABLE_FAVORITE,
                allColumnsFavorite, null, null, null, null, null);
        long counter = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (counter == id) {

                String productNameForDeletion = cursor.getString(1);
                database.delete(ProductDBHelper.TABLE_FAVORITE, ProductDBHelper.COLUMN_NAME
                        + " = '" + productNameForDeletion + "';", null);
                //cursor.close();

                break;

            }
            counter++;
            cursor.moveToNext();

        }
        cursor.close();
        //database.delete(ProductDBHelper.TABLE_RECENT, ProductDBHelper.COLUMN_ID
        //        + " = " + id, null);
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<Product>();

        Cursor cursor = database.query(ProductDBHelper.TABLE_PRODUCTS,
                allColumnsProducts, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            products.add(product);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return products;
    }

    public ArrayList<Recent> getAllRecent() {
        ArrayList<Recent> histories = new ArrayList<Recent>();
        Cursor cursor = database.query(ProductDBHelper.TABLE_RECENT, allColumnsRecent, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recent recent = cursorToRecent(cursor);
            histories.add(recent);
            cursor.moveToNext();
        }
        cursor.close();
        return histories;

    }

    public ArrayList<Favorite> getAllFavoriteList() {
        ArrayList<Favorite> favoriteLists = new ArrayList<Favorite>();
        Cursor cursor = database.query(ProductDBHelper.TABLE_FAVORITE, allColumnsFavorite, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Favorite favorite = cursorToFavoriteList(cursor);
            favoriteLists.add(favorite);
            cursor.moveToNext();
        }

        cursor.close();
        return favoriteLists;

    }


    public void deleteAllProducts() {
        ArrayList<Product> products = getAllProducts();
        if (products.size() != 0) {
            database.delete(ProductDBHelper.TABLE_PRODUCTS, null, null);
        }
    }

    public void deleteAllRecent() {
        ArrayList<Recent> histories = getAllRecent();
        if (histories.size() != 0) {
            database.delete(ProductDBHelper.TABLE_RECENT, null, null);
        }
    }

    public void deleteAllFavoriteList() {
        ArrayList<Favorite> favoriteLists = getAllFavoriteList();
        if (favoriteLists.size() != 0) {
            database.delete(ProductDBHelper.TABLE_FAVORITE, null, null);
        }
    }

    public Product cursorToProduct(Cursor cursor) {

        Product product = new Product();
        product.setId(cursor.getInt(0));
        product.setProductType(cursor.getString(1));
        product.setProductName(cursor.getString(2));
        product.setProvider(cursor.getString(3));
        product.setCall(cursor.getString(4));
        product.setData(cursor.getDouble(5));
        product.setIntCall(cursor.getString(6));
        product.setPrice(cursor.getDouble(7));
        product.setLink(cursor.getString(8));
        product.setInfo(cursor.getString(9));
        product.setPhoneModel(cursor.getString(10));
        product.setPhoneBrand(cursor.getString(11));
        product.setUpfront(cursor.getDouble(12));
        return product;
    }

    public Recent cursorToRecent(Cursor cursor) {
        Recent recent = new Recent(cursor.getInt(0), cursor.getString(1).charAt(0), cursor.getString(2),
                cursor.getString(3), cursor.getDouble(7), cursor.getString(10));
        return recent;
    }


    public Favorite cursorToFavoriteList(Cursor cursor) {
        Favorite favoriteList = new Favorite(cursor.getInt(0), cursor.getString(1).charAt(0), cursor.getString(2),
                cursor.getString(3), cursor.getDouble(7), cursor.getString(10));
        return favoriteList;
    }

    // TODO: List all brand
    public String[] getAllBrands() {
        Set<String> brandSet = new HashSet<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + ProductDBHelper.TABLE_PRODUCTS, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            brandSet.add(cursor.getString(cursor.getColumnIndex(ProductDBHelper.COLUMN_PHONE_BRAND)));
            cursor.moveToNext();
        }
        cursor.close();
        String[] brands = Arrays.copyOf(brandSet.toArray(), brandSet.size(), String[].class);
        return brands;
    }


// TODO: List all model by brand
    public Set<String> getAllModels(String brand) {
        Map<String, String> brandModel = new HashMap<>();
        Set<String> models = new HashSet<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + ProductDBHelper.TABLE_PRODUCTS, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            brandModel.put(cursor.getString(cursor.getColumnIndex(ProductDBHelper.COLUMN_PHONE_BRAND)),
                    cursor.getString(cursor.getColumnIndex(ProductDBHelper.COLUMN_PHONE)));
            cursor.moveToNext();
        }
        cursor.close();
        for (int i = 0; i<= brandModel.size(); i++) {
            if (brandModel.containsKey(brand)==false){
                brandModel.remove(brandModel.get(i));
            }
        }
        models.addAll(brandModel.values());
        return models;
    }


    // List all packages by model
    public List<Product> getProductByModel(String model) {
        List<Product> products = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + ProductDBHelper.TABLE_PRODUCTS
                + "WHERE " + ProductDBHelper.COLUMN_PRICE + "== " + model, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            products.add(product);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return products;
    }

    // List SIM package by price

    public List<Product> getProductByPrice(int maxPrice) {
        List<Product> products = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + ProductDBHelper.TABLE_PRODUCTS
                + "WHERE " + ProductDBHelper.COLUMN_PRICE + "< = " + maxPrice, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            products.add(product);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return products;
    }

//  List SIM package by selected usage

    public List<Product> getProductByUsage(int minData, boolean ntnCallRequest, boolean intCallRequest) {
        List<Product> products = new ArrayList<>();
        Cursor cursor;
        if(ntnCallRequest ==true && intCallRequest ==true) {
            cursor = database.rawQuery("SELECT * FROM " + ProductDBHelper.TABLE_PRODUCTS
                    + "WHERE" + ProductDBHelper.COLUMN_DATA + "> " + minData
                    + "AND (" + ProductDBHelper.COLUMN_CALL + " LIKE '%nlimited%')"
                    + "AND (" + ProductDBHelper.COLUMN_INT_CALL + " IS NOT NULL)", null);
        }

        else if(ntnCallRequest ==true && intCallRequest == false) {
            cursor = database.rawQuery("SELECT * FROM " + ProductDBHelper.TABLE_PRODUCTS
                    + "WHERE (" + ProductDBHelper.COLUMN_DATA + "> " + minData + ")"
                    + "AND (" + ProductDBHelper.COLUMN_CALL + " LIKE '%nlimited%')", null);
        }
        else if (ntnCallRequest ==false && intCallRequest == true) {
            cursor = database.rawQuery("SELECT * FROM " + ProductDBHelper.TABLE_PRODUCTS
                    + "WHERE (" + ProductDBHelper.COLUMN_DATA + "> " + minData + ")"
                    + "AND (" + ProductDBHelper.COLUMN_INT_CALL + " IS NOT NULL)", null);
        }
        else {
            cursor = database.rawQuery("SELECT * FROM " + ProductDBHelper.TABLE_PRODUCTS
                    + "WHERE" + ProductDBHelper.COLUMN_DATA + "> " + minData, null);
        }

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Product product = cursorToProduct(cursor);
                products.add(product);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return products;
        }


}*/
