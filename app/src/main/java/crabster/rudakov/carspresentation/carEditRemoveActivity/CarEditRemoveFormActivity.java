package crabster.rudakov.carspresentation.carEditRemoveActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import crabster.rudakov.carspresentation.recyclerViewHelpers.Car;
import crabster.rudakov.carspresentation.R;
import crabster.rudakov.carspresentation.carDataBase.CarDbManager;
import crabster.rudakov.carspresentation.carEditActivity.CarEditActivity;

public class CarEditRemoveFormActivity extends AppCompatActivity {

    private CarDbManager dbManager;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_edit_remove_form);
        dbManager = new CarDbManager(this);
        car = (Car) getIntent().getSerializableExtra("car");

        Button editCarButton = findViewById(R.id.edit_car_button);
        editCarButton.setOnClickListener(v -> {
            initCarEditActivity();
        });

        Button deleteCarButton = findViewById(R.id.delete_car_button);
        deleteCarButton.setOnClickListener(v -> {
            dbManager.removeCar(car.getImageId());
            onBackPressed();
        });
    }

    /**
     * Создаём активность, которая будет запускаться в случае необходимости редактирвоания данных
     * */
    private void initCarEditActivity() {
        Intent intent = new Intent(this, CarEditActivity.class);
        intent.putExtra("car", car);
        startActivity(intent);
        onBackPressed();
    }

    /**
     * Отображаем данные авто, полученные из таблицы БД, в соотвествующие нередактируемые текстовые
     * поля, предварительно заменив значение ID картинки на её имя
     * */
    @Override
    protected void onResume() {
        super.onResume();
        dbManager.getCarFromDB(car.getImageId());

        TextView carImageIdTextViewRemovable = findViewById(R.id.imageId_removable);
        String imageName = getResources().getResourceEntryName(car.getImageId());
        carImageIdTextViewRemovable.setText(imageName);
        carImageIdTextViewRemovable.setEnabled(false);

        TextView carModelTextViewRemovable = findViewById(R.id.modelId_removable);
        carModelTextViewRemovable.setText(car.getModel());
        carModelTextViewRemovable.setEnabled(false);

        TextView carPriceTextViewRemovable = findViewById(R.id.priceId_removable);
        carPriceTextViewRemovable.setText(String.valueOf(car.getPrice()));
        carPriceTextViewRemovable.setEnabled(false);

        TextView carPowerTextViewRemovable = findViewById(R.id.powerId_removable);
        carPowerTextViewRemovable.setText(String.valueOf(car.getPower()));
        carPowerTextViewRemovable.setEnabled(false);
    }

}