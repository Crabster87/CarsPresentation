package crabster.rudakov.carspresentation.carDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import crabster.rudakov.carspresentation.recyclerViewHelpers.Car;

public class CarDbManager {

    private List<Car> cars = new ArrayList<>();
    private Context context;
    private SQLiteDatabase db;

    public CarDbManager(Context context) {
        this.context = context.getApplicationContext();
        db = new CarDbHelper(context).getWritableDatabase();
    }

    /**
     * Вставляем данные из хранилища в имеющуюся таблицу в БД
     * */
    public void addCar(Car car) {
        ContentValues values = getContentValues(car);
        db.insert(CarDbSchema.CarTable.NAME, null, values);
    }

    /**
     * Записываем данные в специальное хранилище, ассоциируя при этом
     * названия колонок таблицы БД и параметров авто
     * */
    private static ContentValues getContentValues(Car car) {
        ContentValues values = new ContentValues();
        values.put(CarDbSchema.Columns.IMAGE_ID, car.getImageId());
        values.put(CarDbSchema.Columns.MODEL, car.getModel());
        values.put(CarDbSchema.Columns.PRICE, car.getPrice());
        values.put(CarDbSchema.Columns.POWER, car.getPower());
        return values;
    }

    /**
     * С помощью специального объекта, проходя по каждой строке таблицы БД, получаем список
     * имеющихся авто, отсортированный по нужному параметру
     * */
    public List<Car> getCarList(String orderBy) {
        try (CarCursorWrapper cursorWrapper = queryCars(orderBy)) {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                Car car = cursorWrapper.getCar();
                cars.add(car);
                cursorWrapper.moveToNext();
            }
        }
        return cars;
    }

    /**
     * Специальным запросом к БД получаем объект для дальнейшей обработки данных, в параметре метода
     * указываем наименование колонки, по которой хотим отсортировать полученный список
     * */
    private CarCursorWrapper queryCars(String orderBy) {
        Cursor cursor = db.query(CarDbSchema.CarTable.NAME, null
                                                          , null
                                                          , null
                                                          , null
                                                          , null
                                                          , orderBy);
        return new CarCursorWrapper(cursor);
    }

    /**
     * Получаем авто из таблицы БД с требующимся идентификатором ресурса соответствующей ему картинки
     * */
    public Car getCarFromDB(int imageId) {
        Cursor cursor = db.query(CarDbSchema.CarTable.NAME,
                null,
                CarDbSchema.Columns.IMAGE_ID + "=?",
                new String[]{String.valueOf(imageId)},
                null,
                null,
                null);
        CarCursorWrapper cursorWrapper = new CarCursorWrapper(cursor);
        cursorWrapper.moveToFirst();
        return cursorWrapper.getCar();
    }

    /**
     * Удаляем авто из таблицы БД с требующимся идентификатором ресурса соответствующей ему картинки
     * */
    public void removeCar(int imageId) {
        String stringImageId = String.valueOf(imageId);
        db.delete(CarDbSchema.CarTable.NAME,
       CarDbSchema.Columns.IMAGE_ID + "=?",
                 new String[]{stringImageId});
    }

    /**
     * Обновляем данные по авто в таблице БД
     * */
    public void updateCar(Car car) {
        ContentValues values = getContentValues(car);
        String stringImageId = String.valueOf(car.getImageId());
        db.update(CarDbSchema.CarTable.NAME, values,
       CarDbSchema.Columns.IMAGE_ID + "=?",
                 new String[]{String.valueOf(stringImageId)});
    }

}
