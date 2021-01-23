package tr.edu.msku.steprace.model;

public class Data {
    private String date;
    private int num;
    private int month_num;
    private int week_num;


    public Data() {

    }


    public Data(String date, int num) {
        this.date = date;
        this.num = num;
    }



    public int getWeek_num() {
        return week_num;
    }

    public void setWeek_num(int week_num) {
        this.week_num = week_num;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMonth_num() {
        return month_num;
    }

    public void setMonth_num(int month_num) {
        this.month_num = month_num;
    }
}
