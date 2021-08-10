package crabster.rudakov.carspresentation.carEditActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import crabster.rudakov.carspresentation.recyclerViewHelpers.Car;
import crabster.rudakov.carspresentation.auxiliaryClasses.EnteredDataVerifier;
import crabster.rudakov.carspresentation.R;
import crabster.rudakov.carspresentation.carDataBase.CarDbManager;

public class CarEditActivity extends AppCompatActivity {

    private Car car;
    private EditText carImageIdTextViewEditable;
    private EditText carModelTextViewEditable;
    private EditText carPriceTextViewEditable;
    private EditText carPowerTextViewEditable;
    private CarDbManager dbManager;

    /**
     * Помимо отображения реализуем логику проверки введённых данных, обновляем только в случае
     * прохождения проверки данных на валидность и отсутствия аналогичного объекта в таблице БД
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_edit);

        car = (Car) getIntent().getSerializableExtra("car");
        carImageIdTextViewEditable = findViewById(R.id.imageId_editable);
        carModelTextViewEditable = findViewById(R.id.modelId_editable);
        carPriceTextViewEditable = findViewById(R.id.priceId_editable);
        carPowerTextViewEditable = findViewById(R.id.powerId_editable);
        displayCarParameters();

        Button rewriteCarButton = findViewById(R.id.rewrite_car_button);
        rewriteCarButton.setOnClickListener(v -> {
            EnteredDataVerifier verifier = new EnteredDataVerifier();
            Car car = verifier.verifyEnteredData(this,
                                                        carImageIdTextViewEditable,
                                                        carModelTextViewEditable,
                                                        carPriceTextViewEditable,
                                                        carPowerTextViewEditable);
            dbManager = new CarDbManager(this);
            if (car != null && !car.equals(dbManager.getCarFromDB(car.getImageId()))) updateCarInDb(car);
        });
    }

    /**
     * Отображаем данные авто в редактируемых полях. Вместо значения ID картинки отображаем её имя
     * */
    public void displayCarParameters() {
        String imageName = getResources().getResourceEntryName(car.getImageId());
        carImageIdTextViewEditable.setText(imageName);
        carModelTextViewEditable.setText(car.getModel());
        carPriceTextViewEditable.setText(String.valueOf(car.getPrice()));
        carPowerTextViewEditable.setText(String.valueOf(car.getPower()));
    }

    /**
     * Обновляем данные по авто в таблице БД с завершением текущей аткивности
     * */
    private void updateCarInDb(Car car) {
        dbManager.updateCar(car);
        finish();
    }

}