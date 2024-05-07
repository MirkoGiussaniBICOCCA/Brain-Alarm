package it.unimib.brain_alarm.util;


import android.app.Application;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.database.NewsRoomDatabase;
import it.unimib.brain_alarm.Repository.INewsRepositoryWithLiveData;
import it.unimib.brain_alarm.Repository.NewsRepositoryWithLiveData;
import it.unimib.brain_alarm.service.NewsApiService;
import it.unimib.brain_alarm.source.BaseNewsLocalDataSource;
import it.unimib.brain_alarm.source.BaseNewsRemoteDataSource;
import it.unimib.brain_alarm.source.NewsLocalDataSource;
import it.unimib.brain_alarm.source.NewsMockRemoteDataSource;
import it.unimib.brain_alarm.source.NewsRemoteDataSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Registry to provide the dependencies for the classes
 *  used in the application.
 */
public class ServiceLocator {

    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * It creates an instance of NewsApiService using Retrofit.
     * @return an instance of NewsApiService.
     */
    public NewsApiService getNewsApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.NEWS_API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(NewsApiService.class);
    }

    public NewsRoomDatabase getNewsDao(Application application) {
        return NewsRoomDatabase.getDatabase(application);
    }

    public INewsRepositoryWithLiveData getNewsRepository(Application application, boolean debugMode) {
        BaseNewsRemoteDataSource newsRemoteDataSource;
        BaseNewsLocalDataSource newsLocalDataSource;
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);

        //logica per inizializzare la sorgente remota
        //debug mode inizializzo sorgente dati remota (legge dal file) altrimenti con chiamata a beckend
        if (debugMode) {
            JSONParserUtil jsonParserUtil = new JSONParserUtil(application);
            newsRemoteDataSource =
                    new NewsMockRemoteDataSource(jsonParserUtil, JSONParserUtil.JsonParserType.GSON);
        } else {
            newsRemoteDataSource =
                    new NewsRemoteDataSource(application.getString(R.string.news_api_key));
        }

        newsLocalDataSource = new NewsLocalDataSource(getNewsDao(application), sharedPreferencesUtil);

        //chiamo costruttore Repository a cui passo sorgenti dati
        return new NewsRepositoryWithLiveData(newsRemoteDataSource, newsLocalDataSource);
    }
}
