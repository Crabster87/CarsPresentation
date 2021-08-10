package crabster.rudakov.carspresentation.auxiliaryClasses;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastMessage {

    /**
     * Создаём Toast message с заданными характеристиками
     * */
    public static void displayToast(Context context, String message) {
        Toast toast = Toast.makeText(context,
                message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
