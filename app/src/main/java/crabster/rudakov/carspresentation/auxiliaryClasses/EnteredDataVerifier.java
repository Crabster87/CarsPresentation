package crabster.rudakov.carspresentation.auxiliaryClasses;

import android.content.Context;
import android.widget.EditText;

import crabster.rudakov.carspresentation.recyclerViewHelpers.Car;

public class EnteredDataVerifier {

    /**
     * Проверяем введённые пользователем данные, если данные сооветсвуют, то создаём новое авто,
     * иначе дальше не двигаемся
     */
    public Car verifyEnteredData(Context context,
                                 EditText imageView,
                                 EditText modelView,
                                 EditText priceView,
                                 EditText powerView) {
        int carImageId = getEnteredImage(context, imageView);
        String carModelName = getEnteredModel(modelView);
        int carPrice = getEnteredPrice(priceView);
        int carPower = getEnteredPower(powerView);
        if (carImageId == 0) {
            ToastMessage.displayToast(context, "You entered invalid value of 'image resource'!");
        } else if (carModelName.isEmpty()) {
            ToastMessage.displayToast(context, "You didn't enter a name of 'model'!");
        } else if (carPrice <= 0) {
            ToastMessage.displayToast(context, "You entered invalid value of 'price'!");
        } else if (carPower <= 0) {
            ToastMessage.displayToast(context, "You entered invalid value of 'power'!");
        } else {
            return new Car(carImageId,
                    carModelName,
                    carPrice,
                    carPower);
        }
        return null;
    }

    /**
     * Получаем введённое имя картинки для авто и на его основе идентификатор соответствующего ресурса
     */
    private int getEnteredImage(Context context, EditText imageView) {
        String carImageName = imageView.getText().toString();
        return context.getResources().getIdentifier(carImageName,
                "drawable",
                context.getPackageName());
    }

    /**
     * Получаем введённое имя модели авто
     */
    private String getEnteredModel(EditText modelView) {
        return modelView.getText().toString();
    }

    /**
     * Получаем введённую стоимость авто, преобразуем в целое число, обрабатывая исключение
     */
    private int getEnteredPrice(EditText priceView) {
        int carPrice = 0;
        try {
            carPrice = Integer.parseInt(priceView.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return carPrice;
    }

    /**
     * Получаем введённую мощность авто, преобразуем в целое число, обрабатывая исключение
     */
    private int getEnteredPower(EditText powerView) {
        int carPower = 0;
        try {
            carPower = Integer.parseInt(powerView.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return carPower;
    }

}
