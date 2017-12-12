package by.bsu.simplecalendar;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowNotesActivity extends Activity implements View.OnClickListener {

    TextView tvAmountOfNotes;
    Intent intent;
    int amountOfNotes;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);
        tvAmountOfNotes = (TextView) findViewById(R.id.tvAmountOfNotes);
        linearLayout = (LinearLayout) findViewById(R.id.notesList);
        intent = getIntent();
        String allNotes = intent.getStringExtra("allNotes");
        String[]noteArray = allNotes.split("\\|");
        amountOfNotes = Integer.parseInt(noteArray[0]);
        tvAmountOfNotes.setText(noteArray[0]);
        for(int i = 1; i <= amountOfNotes; i++){
            Note note = new Note(Note.parseNote(noteArray[i]));
            TextView textView = new TextView(this);
            textView.setText(note.getFullString());
            linearLayout.addView(textView);
        }
    }

    @Override
    public void onClick(View v){

    }
}
