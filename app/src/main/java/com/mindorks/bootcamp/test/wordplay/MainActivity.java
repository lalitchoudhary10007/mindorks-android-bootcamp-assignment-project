package com.mindorks.bootcamp.test.wordplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvWord, tvMeaning;
    private Button btRandom;
    JSONArray data_Array = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWord = findViewById(R.id.tv_word);
        tvMeaning = findViewById(R.id.tv_meaning);

        btRandom = findViewById(R.id.btn_random);

        // json data stored in JSONArray
        data_Array = createJSONFromFile(R.raw.words);

        btRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if data is empty
                if(data_Array.length() == 0){
                    Toast.makeText(MainActivity.this, "No more words!",Toast.LENGTH_LONG).show();
                }else {
                    //data is not empty
                    try {
                        // Generate a random index with in data JSONArray length
                        Random rand = new Random();
                        int randomIndex = rand.nextInt(data_Array.length());
                        // Get JSONObject of random index from data JSONArray and then get value of word from JSONObject
                        // set value of word on text view and same as meaning
                        tvWord.setText(data_Array.getJSONObject(randomIndex).getString("word"));
                        tvMeaning.setText(data_Array.getJSONObject(randomIndex).getString("meaning"));
                        //after getting random index remove that index from data JSONArray
                        data_Array.remove(randomIndex);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }


    private JSONArray createJSONFromFile(int fileID) {

        JSONArray result = null;

        try {
            // Read file into string builder
            InputStream inputStream = this.getResources().openRawResource(fileID);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            for (String line = null; (line = reader.readLine()) != null ; ) {
                builder.append(line).append("\n");
            }

            // Parse into JSONArray
            String resultStr = builder.toString();
            JSONTokener tokener = new JSONTokener(resultStr);
            result = new JSONArray(tokener);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
             // return array
        return result;
    }


}
