package tr.edu.msku.steprace.service;

import android.os.AsyncTask;

public class WorkerThread extends AsyncTask<Void,Void,Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //onPreExecute—Override this handler to update the UI immediately
        // before doInBackground runs. For example, to show a loading
        // Progress Bar
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}

