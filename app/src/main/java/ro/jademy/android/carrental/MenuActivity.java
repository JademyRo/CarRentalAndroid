package ro.jademy.android.carrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button dailyIncomeButton;
    private Button carListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dailyIncomeButton = (Button) findViewById(R.id.dailyIncomeButton);
        carListButton = (Button) findViewById(R.id.carListButton);

        carListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carListIntent = new Intent(getBaseContext(), CarListActivity.class);
                startActivity(carListIntent);
            }
        });

        dailyIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent incomeActivity = new Intent(getBaseContext(), IncomeActivity.class);
                startActivity(incomeActivity);
            }
        });
    }
}
