package com.dash.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dash.dietdash.R;
import com.dash.model.Calory;
import com.dash.model.Makanan;
import com.dash.model.Menu;
import com.dash.model.Pressure;
import com.dash.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String KEY_ID = "id";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DietDB2.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context ctx;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = getDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    // Getting User Contact
    public ArrayList<User> getUser() {

        ArrayList<User> listUser = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM User";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getDouble(5), cursor.getDouble(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9));

                // Adding contact to list
                listUser.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return listUser;
    }

    // Getting A User Contact
    public User getOneUser(String email) {

        User user = new User();
        String selectQuery = "SELECT * FROM User WHERE Email = '" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4),
                cursor.getDouble(5), cursor.getDouble(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9));
        cursor.close();
        db.close();
        return user;
    }

    // Getting Menu
    public ArrayList<Menu> getMenu(int category, int day, String type) {

        ArrayList<Menu> listMenu = new ArrayList<Menu>();
        String selectQuery = "SELECT * FROM Menu WHERE Category = '" + category + "'AND Day = '" + day + "'AND type = '" + type + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Menu menu = new Menu(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                        cursor.getInt(4), cursor.getString(5), cursor.getDouble(6), cursor.getDouble(7), cursor.getDouble(8),
                        cursor.getDouble(9), cursor.getDouble(10), cursor.getDouble(11), cursor.getDouble(12), cursor.getDouble(13));
                menu.setImage(getImageFood(menu.getFood()));
                listMenu.add(menu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listMenu;
    }

    // Getting Nutrition Calory
    public ArrayList<Calory> getCalory(int category, int day) {

        ArrayList<Calory> listCal = new ArrayList<Calory>();
        String selectQuery = "SELECT * FROM Calory WHERE Category = '" + category + "'AND Day = '" + day + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Calory cal = new Calory(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3));
                listCal.add(cal);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listCal;
    }

    // Adding new user
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", user.getEmail());
        values.put("Password", user.getPassword());

        db.insert("User", null, values);
        db.close();
    }

    // Adding new user
    public void addProfile(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", user.getName());
        values.put("Gender", user.getGender());
        values.put("Age", user.getAge());
        values.put("Weight", user.getWeight());
        values.put("Height", user.getHeight());
        values.put("Pressure", user.getPressure());
        values.put("Hg", user.getHg());
        values.put("Activity", user.getActivity());

        db.update("User", values, "Email=?", new String[]{user.getEmail()});
        db.close();
    }

    // Getting Diary Data
    public ArrayList<Pressure> getPressure(String email) {

        ArrayList<Pressure> listDiary = new ArrayList<Pressure>();
        String selectQuery = "SELECT * FROM Pressure WHERE Email='" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Pressure pressure = new Pressure(cursor.getString(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4));
                switch (pressure.getDay()) {
                    case 1:
                        pressure.setImage(R.drawable.day1);
                        pressure.setInfo("BELUM ADA");
                        break;
                    case 2:
                        pressure.setImage(R.drawable.day2);
                        break;
                    case 3:
                        pressure.setImage(R.drawable.day3);
                        break;
                    case 4:
                        pressure.setImage(R.drawable.day4);
                        break;
                    case 5:
                        pressure.setImage(R.drawable.day5);
                        break;
                    case 6:
                        pressure.setImage(R.drawable.day6);
                        break;
                    case 7:
                        pressure.setImage(R.drawable.day7);
                        break;
                }
                i++;
                // Adding contact to list
                listDiary.add(pressure);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return listDiary;
    }

    // Adding new Diary
    public void addPressure(Pressure pressure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", pressure.getEmail());
        values.put("Date", pressure.getDate());
        values.put("Day", pressure.getDay());
        values.put("Systolic", pressure.getSystolic());
        values.put("Diastolic", pressure.getDiastolic());

        db.insert("Pressure", null, values);
        db.close();
    }

    // Getting Makanan
    public ArrayList<Makanan> getMakanan(String search) {

        ArrayList<Makanan> listMakanan = new ArrayList<>();
        String selectQuery = "SELECT * FROM Makanan WHERE Food LIKE '%" + search + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Makanan makanan = new Makanan(cursor.getString(0), cursor.getInt(1), cursor.getString(2), cursor.getDouble(3),
                        cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6), cursor.getDouble(7), cursor.getDouble(8),
                        cursor.getDouble(9), cursor.getDouble(10));
                listMakanan.add(makanan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listMakanan;
    }

    // Adding new Makanan
    public void addNewMakanan(Makanan makanan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Food", makanan.getFood());
        values.put("Weight", makanan.getWeight());
        values.put("URT", makanan.getUrt());
        values.put("Carbs", makanan.getCarbs());
        values.put("Fat", makanan.getFat());
        values.put("Protein", makanan.getProtein());
        values.put("Cal", makanan.getCalory());
        values.put("Chol", makanan.getChol());
        values.put("Sodium", makanan.getSodium());
        values.put("Potassium", makanan.getPotassium());
        values.put("Calcium", makanan.getCalcium());

        db.insert("Makanan", null, values);
        db.close();
    }

    // Getting Input Makanan
    public ArrayList<Makanan> getInputMakanan(String email, int day) {

        ArrayList<Makanan> listMakanan = new ArrayList<>();
        String selectQuery = "SELECT * FROM Input_Makanan i, Makanan m WHERE i.email='" + email + "' AND i.day = " + day +
                " AND i.food = m.food";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Makanan makanan = new Makanan(cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getDouble(6),
                        cursor.getDouble(7), cursor.getDouble(8), cursor.getDouble(9), cursor.getDouble(10), cursor.getDouble(11),
                        cursor.getDouble(12), cursor.getDouble(13));
                listMakanan.add(makanan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listMakanan;
    }

    public ArrayList<Integer> getRowID(String email, int day){

        ArrayList<Integer> listRow = new ArrayList<>();
        String selectQuery = "SELECT rowid FROM Input_Makanan WHERE email='" + email + "' AND day = " + day;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                listRow.add(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listRow;
    }

    // Adding new user
    public void addInputMakanan(String userEmail, int day, String food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Email", userEmail);
        values.put("Day", day);
        values.put("Food", food);

        db.insert("Input_Makanan", null, values);
        db.close();
    }

    // Delete Input Makanan
    public void deleteInputMakanan(int rowId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Input_Makanan","rowid=?",new String[]{String.valueOf(rowId)});
    }

    // Getting Search Food
    public ArrayList<String> getSearchFood() {

        ArrayList<String> listSearch = new ArrayList<>();
        String selectQuery = "SELECT * FROM Search_Food";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String temp = new String (cursor.getString(0));
                listSearch.add(temp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listSearch;
    }

    // Adding Search Food
    public void addSearchFood(String food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Food", food);

        db.insert("Search_Food", null, values);
        db.close();
    }

    // Delete Search Food
    public void deleteSearchFood(String temp)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Search_Food","food=?",new String[]{temp});
    }


    private int getImageFood(String food) {
        int image = 0;
        switch (food) {
            case "Nasi Putih":
                image = R.drawable.nasi_putih;
                break;
            case "Buah Apel":
                image = R.drawable.apel;
                break;
            case "Buah Anggur":
                image = R.drawable.anggur;
                break;
            case "Buah Pisang":
                image = R.drawable.pisang;
                break;
            case "Buah Pepaya":
                image = R.drawable.pepaya;
                break;
            case "Capcay":
                image = R.drawable.capcay;
                break;
            case "Buncis Rebus":
                image = R.drawable.buncis;
                break;
            case "Ayam Panggang":
                image = R.drawable.ayam_panggang;
                break;
            case "Ayam Goreng Dada":
                image = R.drawable.ayam_goreng_dada;
                break;
            case "Ikan Bawal":
                image = R.drawable.ikan_bawal;
                break;
            case "Bubur Kacang Hijau":
                image = R.drawable.bubur_kacang_hijau;
                break;
            case "Ikan Kembung Goreng":
                image = R.drawable.ikan_kembung_goreng;
                break;
            case "Jeruk Manis":
                image = R.drawable.jeruk_manis;
                break;
            case "Kue Cubit":
                image = R.drawable.kue_cubit;
                break;
            case "Buah Mangga":
                image = R.drawable.mangga;
                break;
            case "Minyak Kelapa":
                image = R.drawable.minyak_kelapa;
                break;
            case "Puding Cokelat":
                image = R.drawable.puding_cokelat;
                break;
            case "Sayur Sawi":
                image = R.drawable.sawi;
                break;
            case "Sayur Bayam Bening":
                image = R.drawable.sayur_bayam;
                break;
            case "Sayur Lodeh":
                image = R.drawable.sayur_lodeh;
                break;
            case "Semangka":
                image = R.drawable.semangka;
                break;
            case "Sup Wortel":
                image = R.drawable.sup_wortel;
                break;
            case "Susu Skim":
                image = R.drawable.susu_skim;
                break;
            case "Tahu Goreng":
                image = R.drawable.tahu_goreng;
                break;
            case "Tempe Goreng":
                image = R.drawable.tempe_goreng;
                break;
            case "Telur Ayam Rebus":
                image = R.drawable.telur_rebus;
                break;
            case "Es Buah":
                image = R.drawable.es_buah;
                break;
            default:
                image = R.drawable.food;
                break;
        }
        return image;
    }
}
