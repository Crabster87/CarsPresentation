package crabster.rudakov.carspresentation.carAddActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import crabster.rudakov.carspresentation.recyclerViewHelpers.Car;
import crabster.rudakov.carspresentation.auxiliaryClasses.EnteredDataVerifier;
import crabster.rudakov.carspresentation.R;
import crabster.rudakov.carspresentation.carDataBase.CarDbManager;

public class CarAddFormActivity extends AppCompatActivity {

    /**
     * Получаем введённые данные, после проверки создаём авто и добавляем его в БД
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add_form);

        Button addCarButton = findViewById(R.id.add_car_button);
        addCarButton.setOnClickListener(v -> {
            EditText imageIdAddable = findViewById(R.id.imageId_addable);
            EditText modelAddable = findViewById(R.id.modelId_addable);
            EditText priceAddable = findViewById(R.id.priceId_addable);
            EditText powerAddable = findViewById(R.id.powerId_addable);
            EnteredDataVerifier verifier = new EnteredDataVerifier();
            Car car = verifier.verifyEnteredData(this,
                                                        imageIdAddable,
                                                        modelAddable,
                                                        priceAddable,
                                                        powerAddable);
            if (car != null) addCarToDb(car);
        });
    }

    /**
     * Добавляем авто в базу данных с завершением текущей активности
     * */
    private void addCarToDb(Car car) {
        CarDbManager dbManager = new CarDbManager(this);
        dbManager.addCar(car);
        finish();
    }

}