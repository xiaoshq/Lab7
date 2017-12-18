package com.xiaoshq.lab7;

import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEdit extends AppCompatActivity {
    public EditText fileName;
    public EditText fileContent;
    public Button saveBTN;
    public Button loadBTN;
    public Button clearBTN;
    public Button deleteBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);

        fileName = (EditText) findViewById(R.id.fileName);
        fileContent = (EditText) findViewById(R.id.fileContent);
        saveBTN = (Button) findViewById(R.id.saveBTN);
        loadBTN = (Button) findViewById(R.id.loadBTN);
        clearBTN = (Button) findViewById(R.id.clearBTN);
        deleteBTN = (Button) findViewById(R.id.deleteBTN);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fileName.getText().toString().trim();
                String content = fileContent.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(FileEdit.this, "File name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    try (FileOutputStream fileOutputStream = openFileOutput(name, MODE_PRIVATE)){
                        fileOutputStream.write(content.getBytes());
                        Toast.makeText(FileEdit.this, "Save successfully", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(FileEdit.this, "Fail to save file", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fileName.getText().toString().trim();

                if (name.equals("")) {
                    Toast.makeText(FileEdit.this, "File name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    try (FileInputStream fileInputStream = openFileInput(name)){
                        byte[] contents = new byte[fileInputStream.available()];
                        fileInputStream.read(contents);
                        Toast.makeText(FileEdit.this, "Load successfully", Toast.LENGTH_SHORT).show();
                        fileContent.setText(new String(contents));
                    } catch (IOException e) {
                        Toast.makeText(FileEdit.this, "Fail to load file", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        clearBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileContent.setText("");
            }
        });

        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fileName.getText().toString().trim();

                if (name.equals("")) {
                    Toast.makeText(FileEdit.this, "File name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean isDeleted = deleteFile(name);
                    if (isDeleted) {
                        Toast.makeText(FileEdit.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(FileEdit.this, "Fail to delete file", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
