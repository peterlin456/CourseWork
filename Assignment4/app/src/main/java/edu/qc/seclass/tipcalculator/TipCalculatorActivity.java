package edu.qc.seclass.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class TipCalculatorActivity extends AppCompatActivity {

    public void tip(View view) {
        final double fifteenPercentMoney = 0.15;
        final double twentyPercentMoney = 0.20;
        final double twentyFivePercentMoney = 0.25;


        EditText fifteenPercentTip = findViewById(R.id.fifteenPercentTipValue);
        EditText twentyPercentTip = findViewById(R.id.twentyPercentTipValue);
        EditText twentyfivePercentTip = findViewById(R.id.twentyfivePercentTipValue);
        EditText checkAmountInput = findViewById(R.id.checkAmountValue);
        EditText partySizeInput = findViewById(R.id.partySizeValue);

        //convert the input string to number
        double checkAmount = Double.parseDouble(checkAmountInput.getText().toString());
        int partySize = Integer.parseInt(partySizeInput.getText().toString());

        //set input(check amount and party size) to string the test for toast
        String textcheckinput = checkAmountInput.getText().toString();
        String textpartyinput = partySizeInput.getText().toString();

        //if the input is null then shows error message
       if(textcheckinput.length()==0 || textpartyinput.length()==0){
           Toast.makeText(this, "Empty or incorrect values(s)!", Toast.LENGTH_SHORT).show();
           return;
       }

        double split = checkAmount / partySize;

        int Tip15 = (int) Math.ceil(split * fifteenPercentMoney);
        int Tip20 = (int) Math.ceil( split * twentyPercentMoney);
        int Tip25 = (int) Math.ceil(split * twentyFivePercentMoney);


        fifteenPercentTip.setText(Integer.toString(Tip15));
        twentyPercentTip.setText(Integer.toString(Tip20));
        twentyfivePercentTip.setText(Integer.toString(Tip25));


        EditText fifteenPercent = findViewById(R.id.fifteenPercentTotalValue);
        EditText twentyPercent = findViewById(R.id.twentyPercentTotalValue);
        EditText twentyfivePercent = findViewById(R.id.twentyfivePercentTotalValue);



        fifteenPercent.setText(Integer.toString((int)Math.ceil( Tip15 + split)));
        twentyPercent.setText(Integer.toString ((int) Math.ceil(Tip20 + split)));
        twentyfivePercent.setText(Integer.toString((int) Math.ceil(Tip25 + split)));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

