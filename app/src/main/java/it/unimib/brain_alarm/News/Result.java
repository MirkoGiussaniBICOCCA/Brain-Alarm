package it.unimib.brain_alarm.News;


//rappresenta il risultato di una chiamata
//a seconda se ho chiamta andata a buon fine avrò success oppure il messaggio con il relativo errore

import android.util.Log;

import it.unimib.brain_alarm.NewsFragment;
import it.unimib.brain_alarm.ui.user.User;

public abstract class Result {
    private Result() {}

    public boolean isSuccess() {
        if (this instanceof NewsResponseSuccess || this instanceof UserResponseSuccess) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Class that represents a successful action during the interaction
     * with a Web Service or a local database.
     */
    public static final class NewsResponseSuccess extends Result {
        private final NewsResponse newsResponse;
        public NewsResponseSuccess(NewsResponse newsResponse) {
            this.newsResponse = newsResponse;
        }
        public NewsResponse getData() {
            return newsResponse;
        }
    }

    /**
     * Class that represents a successful action during the interaction
     * with a Web Service or a local database.
     */
    public static final class UserResponseSuccess extends Result {
        private final User user;
        public UserResponseSuccess(User user) {
            this.user = user;
        }
        public User getData() {
            return user;
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
