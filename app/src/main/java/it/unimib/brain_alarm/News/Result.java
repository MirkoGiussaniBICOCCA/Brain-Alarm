package it.unimib.brain_alarm.News;


//rappresenta il tisultato di una chiamta
//a seconda se ho chiamta anmdata a buon fiune avrò success oppure il messaggio con il relativo errore

public abstract class Result {
    private Result() {}

    public boolean isSuccess() {
        return this instanceof Success;
    }


    public static final class Success extends Result {
        private final NewsResponse newsResponse;
        public Success(NewsResponse newsResponse) {
            this.newsResponse = newsResponse;
        }
        public NewsResponse getData() {
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
