package by.bsu.simplecalendar;


import java.util.Comparator;

public class Note {
    private int day;
    private int month;
    private int year;
    private String data;


    public Note(int day, int month, int year, String data) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.data = data;
    }

    public Note(Note note){
        this.day = note.day;
        this.month = note.month;
        this.year = note.year;
        this.data = note.data;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    static Note parseNote(String str){
        String[] strings = str.split(" ");
        return new Note(Integer.parseInt(strings[0]),
                Integer.parseInt(strings[1]),
                Integer.parseInt(strings[2]),
                strings[3]);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(day);
        str.append(" ");
        str.append(month);
        str.append(" ");
        str.append(year);
        str.append(" ");
        str.append(data);
        return new String(str);
    }
}


class SortByDate implements Comparator<Note> {
    @Override
    public int compare(Note note1, Note note2) {
        return ((note1.getYear() > note2.getYear()) ? -1 :
                (note1.getMonth() > note2.getMonth()) ? -1 :
                        note1.getDay() > note2.getDay() ? 1 : -1);
    }
}
