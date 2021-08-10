package crabster.rudakov.carspresentation.carDataBase;

import android.database.Cursor;
import android.database.CursorWrapper;

import crabster.rudakov.carspresentation.recyclerViewHelpers.Car;

public class CarCursorWrapper extends CursorWrapper {

    public CarCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Путём изъятия данных из соответствующих колонок БД создаём и возвращаем авто
     * */
    public Car getCar() {
        int imageId = getInt(getColumnIndex(CarDbSchema.Columns.IMAGE_ID));
        String model = getString(getColumnIndex(CarDbSchema.Columns.MODEL));
        int price = getInt(getColumnIndex(CarDbSchema.Columns.PRICE));
        int power = getInt(getColumnIndex(CarDbSchema.Columns.POWER));
        return new Car(imageId, model, price, power);
    }

}
