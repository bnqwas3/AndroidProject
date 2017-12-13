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
    String monthNumber;
    String yearNumber;

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
        monthNumber = intent.getStringExtra("tvMonthNumber");
        yearNumber = intent.getStringExtra("tvYearNumber");
        String dateToShow = dayNumber+"."+monthNumber+"."+yearNumber;
        tvDayNumber.setText(dateToShow);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.btnSubmitNote:
                Intent intent = new Intent(this, MainActivity.class);
                Note note = new Note(Integer.parseInt(dayNumber), Integer.parseInt(monthNumber),
                        Integer.parseInt(yearNumber), etNoteContent.getText().toString());
                intent.putExtra("note", note.toString());
                startActivity(intent);
                break;
        }
    }
}
