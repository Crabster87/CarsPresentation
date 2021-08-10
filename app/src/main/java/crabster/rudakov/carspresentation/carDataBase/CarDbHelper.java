package crabster.rudakov.carspresentation.carDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "carBase.db";
    private static final int VERSION = 1;

    public CarDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Создаём базу данных(если таковая отсутствует) с заданными именами, колонками и типами данных
     * */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + CarDbSchema.CarTable.NAME +
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CarDbSchema.Columns.IMAGE_ID + " INTEGER, " +
                CarDbSchema.Columns.MODEL + ", " +
                CarDbSchema.Columns.PRICE + " INTEGER, " +
                CarDbSchema.Columns.POWER + " INTEGER)");
    }

    /**
     * Метод не используется
     * */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

}
