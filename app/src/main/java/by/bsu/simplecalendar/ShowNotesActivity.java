package by.bsu.simplecalendar;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowNotesActivity extends Activity implements View.OnClickListener {

    TextView tvAmountOfNotes;
    Intent intent;
    int amountOfNotes;
    LinearLayout linearLayout;
    String indexesToRemove;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);

        tvAmountOfNotes = (TextView) findViewById(R.id.tvAmountOfNotes);
        linearLayout = (LinearLayout) findViewById(R.id.notesList);
        btnBack = (Button) findViewById(R.id.btnBack);
        intent = getIntent();
        String allNotes = intent.getStringExtra("allNotes");
        String[]noteArray = allNotes.split("\\|");
        amountOfNotes = Integer.parseInt(noteArray[0]);
        tvAmountOfNotes.setText(noteArray[0]);
        indexesToRemove = "";
        btnBack.setOnClickListener(this);
        for(int i = 1; i <= amountOfNotes; i++){
            Note note = new Note(Note.parseNote(noteArray[i]));
            Button button = new Button(this);
            button.setText(getString(R.string.removeButton));
            button.setTag(R.id.btnRemove,"btnRemove");
            button.setTag(R.id.btnRemoveNumber,String.valueOf(i-1));
            button.setOnClickListener(this);
            TextView textView = new TextView(this);
            textView.setText(note.getFullString());
            linearLayout.addView(textView);
            linearLayout.addView(button);
        }
    }

    @Override
    public void onClick(View v){
        switch (String.valueOf(v.getTag(R.id.btnRemove))){
            case "btnRemove":
                for(int i = 0; i < amountOfNotes; i++){
                    if(i == Integer.parseInt(v.getTag(R.id.btnRemoveNumber).toString())){
                        indexesToRemove+=i;
                        indexesToRemove+=" ";
                    }
                }
                break;
        }
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("indexesToRemove",indexesToRemove);
                startActivity(intent);
                break;

        }
    }
}
