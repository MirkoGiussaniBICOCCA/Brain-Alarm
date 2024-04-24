package it.unimib.brain_alarm.util;

import java.util.List;

import it.unimib.brain_alarm.News.News;

public interface ResponseCallback {

    void onSuccess(List<News> newsList, long lastUpdate);
    void onFailure(String errorMessage);
    void onNewsFavoriteStatusChanged(News news);  //per capire se lo stato della notizia è passato da preferito a non preferito
}

