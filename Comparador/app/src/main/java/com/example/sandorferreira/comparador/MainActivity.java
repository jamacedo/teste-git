package com.example.sandorferreira.comparador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button compareButton;
    EditText firstPrice, firstML, secondPrice, secondML;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compareButton = (Button) findViewById(R.id.button);
        firstPrice = (EditText) findViewById(R.id.firstPrice);
        firstML = (EditText) findViewById(R.id.firstML);
        secondPrice = (EditText) findViewById(R.id.secondPrice);
        secondML = (EditText) findViewById(R.id.secondML);

        compareButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                String resultMessage;
                resultMessage = compare(Double.parseDouble(firstPrice.getText().toString()),
                                        Double.parseDouble(firstML.getText().toString()),
                                        Double.parseDouble(secondPrice.getText().toString()),
                                        Double.parseDouble(secondML.getText().toString()));
                showResult(resultMessage);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String compare(double firstPrice, double firstML, double secondPrice, double secondML){
        double firstWorth, secondWorth, percentage;
        String result;
        firstWorth = firstPrice / firstML;
        secondWorth = secondPrice / secondML;

        if(firstWorth < secondWorth){
            percentage = 100 - (firstWorth/secondWorth)*100;
            result = "1 option is the best option. It is %" + (int) percentage + " cheaper";
        }
        else if(secondWorth < firstWorth){
            percentage = 100 - (secondWorth/firstWorth)*100;
            result = "2 option is the best option. It is %" + (int) percentage + " cheaper";
        }else{
            result = "Same evaluation. Any option will do";
        }

        return result;
    }

    public void showResult(String message){
        Intent resultActivity = new Intent(MainActivity.this, ResultActivity.class);
        resultActivity.putExtra("resultadoMessage", message);
        startActivity(resultActivity);
    }
}
