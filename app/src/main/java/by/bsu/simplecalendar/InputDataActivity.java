package by.bsu.simplecalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputDataActivity extends Activity implements View.OnClickListener {
    EditText etNoteContent;
    TextView tvDayNumber;
    Button btnSubmit;

    String dayNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        etNoteContent = findViewById(R.id.etNoteContent);
        tvDayNumber = findViewById(R.id.tvNoteDay);
        btnSubmit = findViewById(R.id.btnSubmitNote);
        btnSubmit.setOnClickListener(this);

        Intent intent = getIntent();
        dayNumber = intent.getStringExtra("tvDayNumber");
        tvDayNumber.setText(dayNumber);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.btnSubmitNote:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Note note = new Note(Integer.parseInt(dayNumber), 5, 2017, etNoteContent.getText().toString());
                intent.putExtra("note", note.toString());
                startActivity(intent);
                break;
        }
    }
}
