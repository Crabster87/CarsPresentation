package crabster.rudakov.carspresentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import crabster.rudakov.carspresentation.carAddActivity.CarAddFormActivity;
import crabster.rudakov.carspresentation.carDataBase.CarDbHelper;
import crabster.rudakov.carspresentation.carDataBase.CarDbManager;
import crabster.rudakov.carspresentation.carEditRemoveActivity.CarEditRemoveFormActivity;
import crabster.rudakov.carspresentation.fullScreenActivity.FullScreenActivity;
import crabster.rudakov.carspresentation.recyclerViewHelpers.Car;
import crabster.rudakov.carspresentation.recyclerViewHelpers.ItemTouchHelperAdapter;
import crabster.rudakov.carspresentation.recyclerViewHelpers.SimpleItemTouchHelperCallback;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Car> cars = new ArrayList<>();
    private CarAdapter carAdapter;

    /**
     * Помимо стандартных функций создаём базу данных, если её ещё не существует, берём из неё
     * список авто, устанавливаем для RecyclerView адаптер со списком. А также инициализируем
     * активность по добавлению нового автомобиля
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.single_car);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CarDbHelper dbHelper = new CarDbHelper(this);
        SQLiteDatabase db = openOrCreateDatabase(CarDbHelper.DATABASE_NAME, MODE_PRIVATE, null);
        dbHelper.onCreate(db);

        cars = new CarDbManager(this).getCarList();
        carAdapter = new CarAdapter(cars);
        recyclerView.setAdapter(carAdapter);

        Button addCarButton = findViewById(R.id.add_new_car_button);
        addCarButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CarAddFormActivity.class);
            startActivity(intent);
        });

    }

    /**
     * Реализуем отрисовку обновлённого списка после добавления, редактирования или удаления авто.
     * А также после перетягивания элеметнов списка и их удаления с экрана
     * */
    @Override
    protected void onResume() {
        super.onResume();
        CarDbManager manager = new CarDbManager(this);
        cars = manager.getCarList();
        carAdapter = new CarAdapter(cars);
        recyclerView.setAdapter(carAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(carAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    public class CarHolder extends RecyclerView.ViewHolder {

        private ImageView carImageView;
        private TextView carModelTextView;
        private TextView carPriceTextView;
        private TextView carPowerTextView;
        private Car car;


        /**
         * Создаём holder и назначаем слушателей кликов отдельным его View
         * */
        public CarHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.single_car, viewGroup, false));
            initFullScreenActivity();
            initCarEditRemoveActivity();
        }

        /**
         * Привязываем данные поступившего авто соответствующим полям макета
         * */
        public void bind(Car car) {
            carImageView.setImageResource(car.getImageId());
            carImageView.setTag(car.getImageId());

            carModelTextView.setText(car.getModel());
            DecimalFormat dF = new DecimalFormat( "###,###" );
            carPriceTextView.setText(dF.format(car.getPrice()) + "$");
            carPowerTextView.setText(car.getPower() + "hp");
            this.car = car;
        }

        /**
         * Назначаем слушатель клика на изображение, инициализируем активность по увеличению фото
         * */
        public void initFullScreenActivity() {
            carImageView = itemView.findViewById(R.id.car_image);
            carImageView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, FullScreenActivity.class);
                int carImageIdName = (int) carImageView.getTag();
                intent.putExtra("drawable", carImageIdName);
                startActivity(intent);
            });
        }

        /**
         * Назначаем слушатель клика на все части CardView кроме изображения, инициализируем
         * активность по редактированию данных
         * */
        public void initCarEditRemoveActivity() {
            carModelTextView = itemView.findViewById(R.id.car_model);
            carPriceTextView = itemView.findViewById(R.id.car_price);
            carPowerTextView = itemView.findViewById(R.id.car_power);

            View.OnClickListener groupOnClickListener = v -> {
                Intent intentEdit = new Intent(MainActivity.this, CarEditRemoveFormActivity.class);
                intentEdit.putExtra("car", car);
                startActivity(intentEdit);
            };
            carModelTextView.setOnClickListener(groupOnClickListener);
            carPriceTextView.setOnClickListener(groupOnClickListener);
            carPowerTextView.setOnClickListener(groupOnClickListener);
        }

    }

    public class CarAdapter extends RecyclerView.Adapter<CarHolder> implements ItemTouchHelperAdapter {

        private final List<Car> cars;

        public CarAdapter(List<Car> cars) {
            this.cars = cars;
        }

        /**
         * Создаём holder'а, раздувая активность полученного View элемента
         * */
        @Override
        public CarHolder onCreateViewHolder(ViewGroup viewGroup, int count) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            return new CarHolder(inflater, viewGroup);
        }

        /**
         * Получаем авто из списка по номеру его позиции в нём и передаём holder'у для привязки
         * */
        @Override
        public void onBindViewHolder(CarHolder holder, int position) {
            Car car = cars.get(position);
            holder.bind(car);
        }

        /**
         * Получаем и передаём размер списка (количество автомобилей)
         * */
        @Override
        public int getItemCount() {
            return cars.size();
        }

        /**
         * Вызываем когда элемент был перетащен достаточно далеко, чтобы вызвать перемещение.
         * Вызываем каждый раз, когда элемент перемещается, уведомляем адаптер об изменениях
         * */
        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            Car prev = cars.remove(fromPosition);
            cars.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
            notifyItemMoved(fromPosition, toPosition);
        }

        /**
         * Вызываем когда элемент был уже удалён. Вызываем после корретировки данных,
         * чтобы отобразить изменеия, уведомляем адаптер об изменениях
         * */
        @Override
        public void onItemDismiss(int position) {
            cars.remove(position);
            notifyItemRemoved(position);
        }
    }

}