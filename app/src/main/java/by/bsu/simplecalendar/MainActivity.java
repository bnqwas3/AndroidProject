package by.bsu.simplecalendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Calendar calendar;
    Button btnPrevMonth;
    Button btnNextMonth;
    Button btnShowNotes;
    LinearLayout[] weeks;
    int currentDateDay;
    int currentDateMonth;
    int currentDateYear;
    int daysInCurrentMonth;
    int dayOfWeek;
    int count;
    int startsFrom;
    TextView monthName;
    Button[][] days;

    Intent intent;

    DisplayMetrics metrics;
    String month;


    static List<Note> notes= new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("myLogs", "mainActivity onCreate");
        calendar = Calendar.getInstance();
        createWeeks();
        createButtons();
        setCalendar(calendar);
        Log.d("myLogs","setCalendar");
        btnPrevMonth = (Button) findViewById(R.id.btnPrevMonth);
        btnNextMonth = (Button) findViewById(R.id.btnNextMonth);
        btnShowNotes = (Button) findViewById(R.id.btnShowNotes);
        btnPrevMonth.setOnClickListener(this);
        btnNextMonth.setOnClickListener(this);
        btnShowNotes.setOnClickListener(this);
        intent = getIntent();
        Log.d("myLogs","getIntent");
        String str = intent.getStringExtra("note");
        if(str!=null) {
            notes.add(Note.parseNote(str));
        }
        String toRemove = intent.getStringExtra("indexesToRemove");
        if(toRemove!=null && !toRemove.equals("")) {
            String[] arrToRemove = toRemove.split(" ");
            System.out.println(toRemove);
            for (String indexToRemove : arrToRemove) {
                if(Integer.parseInt(indexToRemove)<notes.size()) {
                    Log.d("myLogs","remove skipped");
                    Log.d("arrayToRemove: ",toRemove);
                    Log.d("IndexToRemove: ",indexToRemove);
                    for(int i = 0; i < notes.size(); i++){
                        Log.d("number of note: ",String.valueOf(i));
                        Log.d("notes",String.valueOf(notes.get(i)));
                    }
                    notes.remove(Integer.parseInt(indexToRemove));
                }
            }
        }
        Log.d("myLogs", "notesRemoved");

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnPrevMonth:
                Log.d("myLogs","prev month pressed");
                clearCalendar();
                setCalendar(prevMonthCal(calendar));
                break;
            case R.id.btnNextMonth:
                Log.d("myLogs","next month pressed");
                clearCalendar();
                setCalendar(nextMonthCal(calendar));
                break;
            case R.id.btnShowNotes:
                Log.d("myLogs", "show notes pressed");
                intent = new Intent(this, ShowNotesActivity.class);
                Collections.sort(notes, new SortByDate());
                StringBuilder notesStringBld = new StringBuilder();
                notesStringBld.append(notes.size());
                notesStringBld.append("|");
                for(int i = 0; i < notes.size(); i++){
                    notesStringBld.append(notes.get(i).toString());
                    notesStringBld.append("|");
                }
                String notesString = new String(notesStringBld);
                intent.putExtra("allNotes",notesString);
                startActivity(intent);
                break;

        }
        switch(String.valueOf(v.getTag())){
            case "button":
                Log.d("myLogs", "some date pressed");
                Button btn = (Button) v;
                intent = new Intent(this, InputDataActivity.class);
                intent.putExtra("tvDayNumber", btn.getText().toString());
                intent.putExtra("tvMonthNumber",String.valueOf(calendar.get(Calendar.MONTH)));
                intent.putExtra("tvYearNumber",String.valueOf(calendar.get(Calendar.YEAR)));
                startActivity(intent);
                break;
        }
    }



    public void setCalendar(Calendar calendar){
        Log.d("myLogs","setCalendar call");
        this.calendar = calendar;
        setMonthName();

        currentDateDay =  calendar.get(Calendar.DAY_OF_MONTH);
        currentDateMonth =  calendar.get(Calendar.MONTH);
        currentDateYear = calendar.get(Calendar.YEAR);
        daysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        count = 0;

        startsFrom = dayOfWeek - ((currentDateDay-1)%7);
        if(startsFrom <= 0) {
            startsFrom+=7;
        }

        Calendar prevCal = prevMonthCal(calendar);
        setFirstWeek(prevCal);
        setWeeks();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Calendar prevMonthCal(Calendar calendar){
        Calendar prevCalendar = Calendar.getInstance();
        if(calendar.get(Calendar.MONTH) !=0) {
            prevCalendar.set(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH)-1,
                    calendar.get(Calendar.DAY_OF_MONTH));
        }else{
            prevCalendar.set(
                    calendar.get(Calendar.YEAR) - 1,
                    11,
                    calendar.get(Calendar.DAY_OF_MONTH));
        }
        return prevCalendar;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Calendar nextMonthCal(Calendar calendar){
        Calendar nextCalendar = Calendar.getInstance();
        if(calendar.get(Calendar.MONTH) !=11) {
            nextCalendar.set(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH)+1,
                    calendar.get(Calendar.DAY_OF_MONTH));
        }else{
            nextCalendar.set(
                    calendar.get(Calendar.YEAR) + 1,
                    0,
                    calendar.get(Calendar.DAY_OF_MONTH));
        }
        return nextCalendar;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void createWeeks(){
        weeks = new LinearLayout[6];
        weeks[0] = (LinearLayout) findViewById(R.id.calendar_week_1);
        weeks[1] = (LinearLayout) findViewById(R.id.calendar_week_2);
        weeks[2] = (LinearLayout) findViewById(R.id.calendar_week_3);
        weeks[3] = (LinearLayout) findViewById(R.id.calendar_week_4);
        weeks[4] = (LinearLayout) findViewById(R.id.calendar_week_5);
        weeks[5] = (LinearLayout) findViewById(R.id.calendar_week_6);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void createButtons(){
        monthName = (TextView) findViewById(R.id.textView_MonthName);
        days = new Button[6][7];

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        for (int weekNumber = 0; weekNumber < 6; ++weekNumber) {
            for (int dayInWeek = 0; dayInWeek < 7; dayInWeek++) {
                final Button day = new Button(this);
                day.setTextColor(Color.parseColor("#636161"));
                day.setBackgroundColor(Color.TRANSPARENT);
                day.setLayoutParams(buttonParams);
                day.setTextSize((int) metrics.density * 20); 
                day.setSingleLine();
                day.setTag("button");
                day.setOnClickListener(this);

                days[weekNumber][dayInWeek] = day;
                weeks[weekNumber].addView(day);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setMonthName(){
        month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        monthName.setText(month);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setFirstWeek(Calendar prevCal){
        for(int i = 0; i < startsFrom-1; i++){
            days[0][i].setText(String.valueOf(prevCal.getActualMaximum(Calendar.DAY_OF_MONTH)-startsFrom+i+2));
        }
        for(int i = startsFrom-1; i<7 ;i++){
            count++;
            days[0][i].setText(String.valueOf(count));
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setWeeks(){
        for(int i = 1; i < 6; i++){
            for(int j = 0; j < 7;j++){
                count++;
                if(count > daysInCurrentMonth) {
                    count = 1;
                }
                days[i][j].setText(String.valueOf(count));
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void clearCalendar(){
        for(int i = 0 ; i < 6; i++){
            for(int j = 0; j < 7; j++){
                days[i][j].setText("");
            }
        }
    }
}
