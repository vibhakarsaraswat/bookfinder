package com.example.vibs.bookfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.edittext_search)
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_search)
    public void onClick(View view) {
        String searchedText = edtSearch.getText().toString();

        if(TextUtils.isEmpty(searchedText)) {
            edtSearch.setError(getText(R.string.search_text_missing));
            return;
        }

        Intent iSearch = new Intent(HomeActivity.this, BookActivity.class);
        iSearch.putExtra("searchKey", searchedText);
        startActivity(iSearch);
    }
}