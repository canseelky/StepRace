package tr.edu.msku.steprace.model;

import java.util.Date;

public class Data {

    private Date date;
    private int numOfSteps;

    public Data(Date date, int numOfSteps) {
        this.date = date;
        this.numOfSteps = numOfSteps;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public void setNumOfSteps(int numOfSteps) {
        this.numOfSteps = numOfSteps;
    }
}
