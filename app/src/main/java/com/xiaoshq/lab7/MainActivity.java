package com.xiaoshq.lab7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 访问模式
    public static int MODE = MODE_PRIVATE;
    // 名称
    public static final String PREFERENCE_NAME = "SaveSetting";

    public EditText newPassword;
    public EditText confirmPassword;
    public EditText inputPassword;
    public Button okBTN;
    public Button clearBTN;
    public boolean isFirst;
    public String savedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        okBTN = (Button) findViewById(R.id.okBTN);
        clearBTN = (Button) findViewById(R.id.clearBTN);

        isFirst = readIsFirst();
        savedPassword = readPassword();

        if (isFirst == true) {
            newPassword.setVisibility(View.VISIBLE);
            confirmPassword.setVisibility(View.VISIBLE);
            inputPassword.setVisibility(View.GONE);
        } else {
            newPassword.setVisibility(View.GONE);
            confirmPassword.setVisibility(View.GONE);
            inputPassword.setVisibility(View.VISIBLE);
        }

        okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirst == true) {
                    if (savePassword()) {
                        Intent intent = new Intent(MainActivity.this, FileEdit.class);
                        startActivity(intent);
                    }
                }
                else {
                    String str = inputPassword.getText().toString().trim();
                    if (savedPassword.equals(str)) {
                        Intent intent = new Intent(MainActivity.this, FileEdit.class);
                        startActivity(intent);
                    }
                    else if (str.equals("")) {
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        clearBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPassword.setText("");
                confirmPassword.setText("");
                inputPassword.setText("");
            }
        });
    }

    public String readPassword(){
        // 创建SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        //获得保存在SharedPredPreferences中的密码
        String password = sharedPreferences.getString("password", "");
        return password;
    }

    public boolean readIsFirst(){
        // 创建SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        //获得保存在SharedPredPreferences中的密码
        boolean isFirst = sharedPreferences.getBoolean("isFirst", true);
        return isFirst;
    }

    public boolean savePassword(){
        // 获取输入的新密码和确认密码
        String newStr = newPassword.getText().toString().trim();
        String confirmStr = confirmPassword.getText().toString().trim();

        if (newStr.equals("") || confirmStr.equals("")) {
            Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!newStr.equals(confirmStr)) {
            Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            // 创建sharedPreference对象
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
            // 获得sharedPreferences的编辑器
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // 以键值对的显示将密码保存到sharedPreferences中
            editor.putString("password", newStr);
            editor.putBoolean("isFirst", false);
            // 提交密码
            editor.commit();

            isFirst = false;
            return true;
        }
    }

}
