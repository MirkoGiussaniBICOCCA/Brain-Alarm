package it.unimib.brain_alarm.News;


//rappresenta il risultato di una chiamata
//a seconda se ho chiamta andata a buon fine avrò success oppure il messaggio con il relativo errore

import android.util.Log;

import it.unimib.brain_alarm.NewsFragment;

public abstract class Result {
    private Result() {}

    public boolean isSuccess() {
        return this instanceof Success;
    }


    public static final class Success extends Result {

        private static final String TAG = Result.class.getSimpleName();
        private final NewsResponse newsResponse;
        public Success(NewsResponse newsResponse) {
            this.newsResponse = newsResponse;
        }
        public NewsResponse getData() {
            Log.d(TAG, "get Data " + newsResponse);
            return newsResponse;
        }
    }

    /**
     * Class that represents an error occurred during the interaction
     * with a Web Service or a local database.
     */
    public static final class Error extends Result {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }
}
