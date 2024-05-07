package it.unimib.brain_alarm.util;


import static it.unimib.brain_alarm.util.Constants.API_KEY_ERROR;
import static it.unimib.brain_alarm.util.Constants.RETROFIT_ERROR;

import android.app.Application;

import it.unimib.brain_alarm.R;

/**
 * Utility class to get the proper message to be show
 * to the user when an error occurs.
 */
public class ErrorMessagesUtil {

    private Application application;

    public ErrorMessagesUtil(Application application) {
        this.application = application;
    }

    //in base alla stringa che mi arriva mi permette di restituire messaggio da
    //mostrare a front end
    public String getErrorMessage(String errorType) {
        switch(errorType) {
            case RETROFIT_ERROR:
                return application.getString(R.string.error_retrieving_news);
            case API_KEY_ERROR:
                return application.getString(R.string.api_key_error);
            default:
                return application.getString(R.string.unexpected_error);
        }
    }
}
