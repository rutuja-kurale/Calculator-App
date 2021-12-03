package com.android.calcsx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.faendir.rhino_android.RhinoAndroidHelper;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {
    private EditText input_,result_;
    private  String operation, answer;
    String result;
    boolean dot_inserted,operator_inserted = false;
    boolean checkBracket = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_ = findViewById(R.id.input);
        input_.setShowSoftInputOnFocus(false);
        result_ = findViewById(R.id.result);


        input_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getString(R.string.input).equals(input_.getText().toString())) {
                    input_.setText("");
                }
            }
        });
    }
    public void delete(){
        if(!operation.isEmpty()){
            if(operation.substring(operation.length()-1, operation.length()).equals(".")){
                dot_inserted = false;
            }
            if(operation.substring(operation.length()-1,operation.length()).equals(" ")){
                operation = operation.substring(0,operation.length()-3);
                operator_inserted = false;
            }else {
                operation = operation.substring(0,operation.length()-1);
            }
        }
    }

    public void btnBackSpace(View view){
        delete();
        displayInput();
        }


    public void displayInput(){
        input_.setText(operation);
    }
    public void displayResult(){
        input_.setText(result);
    }

    private void updateInput(String toAdd){
//        we use these method to add new characters into the value
        String oldStr = input_.getText().toString();
//        to print text in between
        int cursorPosition = input_.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPosition);
        String newStr = oldStr.substring(cursorPosition);
//        now added string to update
        if(getString(R.string.input).equals(input_.getText().toString())){
            input_.setText(toAdd);
            input_.setSelection(cursorPosition+1);
        }
        else {
            input_.setText(String.format("%s%s%s", leftStr, toAdd, newStr));
            input_.setSelection(cursorPosition+1);
            operation = operation + toAdd;
        }
    }
    public void btnZero(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "0");
    }
    public void btnOne(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "1");
    }
    public void btnTwo(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "2");
    }
    public void btnThree(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "3");
    }
    public void btnFour(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "4");
    }
    public void btnFive(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "5");
    }
    public void btnSix(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "6");
    }
    public void btnSeven(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "7");
    }
    public void btnEight(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "8");
    }
    public void btnNine(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "9");
    }
    public void btnDot(View view){
        if(operation.isEmpty()){
            operation = "0.";
            dot_inserted = true;
        }
        if(dot_inserted == false) {
            operation = ".";
        }
        displayInput();
    }
    public void btnPlus(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "+");
    }
    public void btnMinus(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "-");
    }
    public void btnMultiply(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "×");
    }
    public void btnDivide(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "÷");
    }
    public void btnMod(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "%");
    }
    public void btnDegree(View view){
        operation = input_.getText().toString();
        input_.setText(operation + "^");
    }
    public void btnCancel(View view){
        input_.setText("");
        result_.setText("");
    }

    public void addBrackets(View view){
        if(checkBracket){
            operation = input_.getText().toString();
            input_.setText(operation + ")");
            checkBracket = false;
        }
        else{
            operation = input_.getText().toString();
            input_.setText(operation + "(");
            checkBracket = true;
        }
    }

    public void btnEqual(View view) {
        operation = input_.getText().toString();
        if(!operation.isEmpty()) {
            operation = operation.replaceAll("×", "*");
            operation = operation.replaceAll("%", "/100");
            operation = operation.replaceAll("÷", "/");

            RhinoAndroidHelper rhinoAndroidHelper = new RhinoAndroidHelper(this);
            Context context = rhinoAndroidHelper.enterContext();
            context.setOptimizationLevel(-1);

            String finalResult = "";
            try {
                Scriptable scriptable = context.initStandardObjects();
                finalResult = context.evaluateString(scriptable, operation, "javascript", 1, null).toString();
            } catch (Exception e) {
                finalResult = "0";
            }
            result_.setText(finalResult);
        }
        else{
            result_.setText("0");
            displayResult();
        }
    }


//    code for exit alertbox
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.info)
                .setTitle("Exit")
                .setMessage("Do you Want to Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNeutralButton("Help", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"Press Yes to exit from app.",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}