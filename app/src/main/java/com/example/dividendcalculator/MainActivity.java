package com.example.dividendcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    EditText editInvestedAmount, editRate, editMonths;
    Button buttonCalculate;
    TextView textResult;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editInvestedAmount = findViewById(R.id.editInvestedAmount);
        editRate = findViewById(R.id.editRate);
        editMonths = findViewById(R.id.editMonths);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textResult = findViewById(R.id.textResult);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        buttonCalculate.setOnClickListener(v -> calculateDividend());
    }

    private void calculateDividend() {
        try {
            double invested = Double.parseDouble(editInvestedAmount.getText().toString());
            double rate = Double.parseDouble(editRate.getText().toString());
            int months = Integer.parseInt(editMonths.getText().toString());

            if (months > 12 || months < 1) {
                textResult.setText("Months must be between 1 and 12.");
                return;
            }

            double monthlyDividend = (invested * (rate / 100)) / 12;
            double totalDividend = monthlyDividend * months;

            String result = "Monthly Dividend: RM " + df.format(monthlyDividend) +
                    "\nTotal Dividend: RM " + df.format(totalDividend);

            textResult.setText(result);

        } catch (Exception e) {
            textResult.setText("Please enter valid numbers.");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, android.view.Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    java.lang.reflect.Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.menu_home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
