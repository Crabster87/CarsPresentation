package crabster.rudakov.carspresentation.fullScreenActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import crabster.rudakov.carspresentation.R;

public class FullScreenActivity extends AppCompatActivity {

    /**
     * Используя библиотеку 'Subsampling Scale Image View' отображаем на отдельном
     * макете переданное изображение. Изображение можно масштабировать
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        SubsamplingScaleImageView imageView = findViewById(R.id.full_screen_image_view);

        int carImageId = getIntent().getIntExtra("drawable", 0);
        imageView.setImage(ImageSource.resource(carImageId));
    }

}