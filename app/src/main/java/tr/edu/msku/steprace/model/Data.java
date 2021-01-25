package tr.edu.msku.steprace.model;

public class Data {
    private String date;
    private int num;



    public Data() {

    }


    public Data(String date, int num) {
        this.date = date;
        this.num = num;
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

}
