package it.unimib.brain_alarm.Repository;


import java.util.List;

import it.unimib.brain_alarm.News.News;

/**
 * Interface to send data from Repositories that implement
 * INewsRepository interface to Activity/Fragment.
 */
public interface ResponseCallback {
    void onSuccess(List<News> newsList, long lastUpdate);
    void onFailure(String errorMessage);
    void onNewsFavoriteStatusChanged(News news);
}
