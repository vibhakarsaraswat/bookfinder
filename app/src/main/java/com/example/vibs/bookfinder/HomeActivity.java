package com.example.vibs.bookfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button objSearchButton  = (Button) findViewById(R.id.button_search);
        objSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edtSearch = (EditText) findViewById(R.id.edittext_search);
                String searchedText = edtSearch.getText().toString();

                Intent iSearch = new Intent(HomeActivity.this, BookActivity.class);
                iSearch.putExtra("searchKey", searchedText);
                startActivity(iSearch);
            }
        });
    }
}