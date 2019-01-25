package ro.jademy.android.carrental;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import ro.jademy.android.carrental.database.DBHelper;
import ro.jademy.android.carrental.model.Car;
import ro.jademy.android.carrental.viewmodel.CarItem;

import java.util.ArrayList;
import java.util.List;

public class CarListActivity extends AppCompatActivity {

    private static final String TAG = CarListActivity.class.getName();

    private List<CarItem> carItemList = new ArrayList<>();

    private ListView carListView;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        context = getBaseContext();

        carListView = findViewById(R.id.carListView);

        DBHelper myDBHelper = DBHelper.getInstance(context);
        List<Car> cars = myDBHelper.getAllCars();

        Resources resources = context.getResources();
        for (Car car : cars) {
            int resourceId = resources.getIdentifier(car.getImage(), "drawable", context.getPackageName());
            Log.d(TAG, "Resource ID: " + resourceId);

            carItemList.add(new CarItem(car.getModel(), car.getMake(), resourceId));
        }

        carListView.setAdapter(new CustomAdapter(context, carItemList));

        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CarItem carItem = carItemList.get(position);

//                Snackbar.make(view, "Clicked on " + carItem.getMake() + " " + carItem.getModel(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();

                Toast.makeText(getBaseContext(), "Clicked on " + carItem.getMake() + " " + carItem.getModel(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
