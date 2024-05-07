package it.unimib.brain_alarm.Repository;


import static it.unimib.brain_alarm.util.Constants.FRESH_TIMEOUT;
import static it.unimib.brain_alarm.util.Constants.TOP_HEADLINES_PAGE_SIZE_VALUE;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.database.NewsDao;
import it.unimib.brain_alarm.database.NewsRoomDatabase;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.News.NewsApiResponse;
import it.unimib.brain_alarm.service.NewsApiService;
import it.unimib.brain_alarm.util.ResponseCallback;
import it.unimib.brain_alarm.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository implements INewsRepository {

    private static final String TAG = NewsRepository.class.getSimpleName();

    private final Application application;
    private final NewsApiService newsApiService;
    private final NewsDao newsDao;
    private final ResponseCallback responseCallback;


    public NewsRepository(Application application, ResponseCallback responseCallback) {
        this.application = application;
        this.newsApiService = ServiceLocator.getInstance().getNewsApiService();
        NewsRoomDatabase newsRoomDatabase = ServiceLocator.getInstance().getNewsDao(application);
        this.newsDao = newsRoomDatabase.newsDao();
        this.responseCallback = responseCallback;
    }

    //fa effettivamente la chiamata
    @Override
    public void fetchNews(String country, int page, long lastUpdate) {

        long currentTime = System.currentTimeMillis();

        Log.d(TAG, "prima di if " + (currentTime - lastUpdate > FRESH_TIMEOUT));

        // fa la chiamata se l'ultimo download della notizia è stato prima del tempo FRESH_TIMEOUT
        // se stato selezionato diverso
        if (currentTime - lastUpdate > FRESH_TIMEOUT) {

            Call<NewsApiResponse> newsResponseCall = newsApiService.getNews(country,
                    TOP_HEADLINES_PAGE_SIZE_VALUE, application.getString(R.string.news_api_key));

            newsResponseCall.enqueue(new Callback<NewsApiResponse>() {
                //onResponse se tutto è andato bene altrimenti onFailure
                @Override
                public void onResponse(@NonNull Call<NewsApiResponse> call,
                                       @NonNull Response<NewsApiResponse> response) {

                    Log.d(TAG, "if dentro onResponse " + (response.body() != null && response.isSuccessful() &&
                            !response.body().getStatus().equals("error")));
                    Log.d(TAG, "response.body() != null " + (response.body() != null ));
                    Log.d(TAG, "response.body() " + (response.body()));
                    Log.d(TAG, "response " + (response));
                    /*
                    Log.d(TAG, "response.isSuccessful() " + (response.isSuccessful()));
                    Log.d(TAG, "!response.body().getStatus().equals(\"error\") " + (!response.body().getStatus().equals("error")));
                    */

                    if (response.body() != null && response.isSuccessful() &&   //controllo chiamata andata a buon fine
                            !response.body().getStatus().equals("error")) {
                        List<News> newsList = response.body().getNewsList();  //ottengo lista oggetti News
                        saveDataInDatabase(newsList);
                        Log.d(TAG, "onResponse");
                    } else {
                        //callback per notificare
                        Log.d(TAG, "onfailure");
                        responseCallback.onFailure(application.getString(R.string.error_retrieving_news));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NewsApiResponse> call, @NonNull Throwable t) {
                    responseCallback.onFailure(t.getMessage());
                }
            });
        } else {
            Log.d(TAG, "else local");
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
            readDataFromDatabase(lastUpdate);
        }
    }

    @Override
    public void deleteFavoriteNews() {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<News> favoriteNews = newsDao.getFavoriteNews();
            for (News news : favoriteNews) {
                news.setFavorite(false);
            }
            newsDao.updateListFavoriteNews(favoriteNews);
            responseCallback.onSuccess(newsDao.getFavoriteNews(), System.currentTimeMillis());
        });
    }

    /**
     * Update the news changing the status of "favorite"
     * in the local database.
     * @param news The news to be updated.
     */
    public void updateNews(News news) {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {
            newsDao.updateSingleFavoriteNews(news);
            responseCallback.onNewsFavoriteStatusChanged(news);
        });
    }

    /**
     * Gets the list of favorite news from the local database.
     */
    public void getFavoriteNews() {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {
            responseCallback.onSuccess(newsDao.getFavoriteNews(), System.currentTimeMillis());
        });
    }

    /**
     * Saves the news in the local database.
     * The method is executed in a Runnable because the database access
     * cannot been executed in the main thread.
     * @param newsList the list of news to be written in the local database.
     */
    private void saveDataInDatabase(List<News> newsList) {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {

            List<News> allNews = newsDao.getAll();  //restituisce lista notizie

            // Checks if the news just downloaded has already been downloaded earlier
            // in order to preserve the news status (marked as favorite or not)
            for (News news : allNews) {
                // This check works because News and NewsSource classes have their own
                // implementation of equals(Object) and hashCode() methods
                if (newsList.contains(news)) {
                    // The primary key and the favorite status is contained only in the News objects
                    // retrieved from the database, and not in the News objects downloaded from the
                    // Web Service. If the same news was already downloaded earlier, the following
                    // line of code replaces the the News object in newsList with the corresponding
                    // News object saved in the database, so that it has the primary key and the
                    // favorite status.
                    newsList.set(newsList.indexOf(news), news);
                }
            }

            // scrivo le news nel database e ottengo le chiavi primarie associate
            List<Long> insertedNewsIds = newsDao.insertNewsList(newsList);
            for (int i = 0; i < newsList.size(); i++) {
                // Adds the primary key to the corresponding object News just downloaded so that
                // if the user marks the news as favorite (and vice-versa), we can use its id
                // to know which news in the database must be marked as favorite/not favorite
                newsList.get(i).setId(insertedNewsIds.get(i));
            }
            //restituisco lista news
            responseCallback.onSuccess(newsList, System.currentTimeMillis());   //lista notizie con orario di quando ho scaricato notizie
        });
    }


    private void readDataFromDatabase(long lastUpdate) {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {
            responseCallback.onSuccess(newsDao.getAll(), lastUpdate);
        });
    }
}

