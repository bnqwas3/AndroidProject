package by.bsu.simplecalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputDataActivity extends Activity implements View.OnClickListener {
    EditText firstName;
    EditText lastName;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
//        firstName = findViewById(R.id.etFName);
//        lastName = findViewById(R.id.etLName);
//        btnSubmit = findViewById(R.id.btnSubmitPerson);
//        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("firstName", firstName.getText().toString());
        intent.putExtra("lastName", lastName.getText().toString());
        startActivity(intent);
    }
}
