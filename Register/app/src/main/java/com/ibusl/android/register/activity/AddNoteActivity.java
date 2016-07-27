package com.ibusl.android.register.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ibusl.android.register.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity {

    @Bind(R.id.add_note_toolbar)
    Toolbar toolbar;
    @Bind(R.id.add_note_edit_text)
    EditText addNoteEditText;
    @Bind(R.id.add_note_button)
    Button addNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(AddNoteActivity.this);
        setSupportActionBar(toolbar);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addNoteEditText.getText().length() > 1){
                    String addNote = String.valueOf(addNoteEditText.getText());
                    Intent result = new Intent();
                    result.putExtra("add_note",addNote);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }else {
                    Toast.makeText(AddNoteActivity.this,"Add some note", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(ItemsActivity.class);
    }

}
