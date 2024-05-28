package it.unimib.brain_alarm.source;

import static android.content.ContentValues.TAG;
import static it.unimib.brain_alarm.util.Constants.API_KEY_ERROR;
import static it.unimib.brain_alarm.util.Constants.RETROFIT_ERROR;
import static it.unimib.brain_alarm.util.Constants.TOP_HEADLINES_PAGE_SIZE_VALUE;

import android.util.Log;

import androidx.annotation.NonNull;

import it.unimib.brain_alarm.News.NewsApiResponse;
import it.unimib.brain_alarm.util.ServiceLocator;
import it.unimib.brain_alarm.service.NewsApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//utilizza retrofit per interrogare backend

public class NewsRemoteDataSource extends BaseNewsRemoteDataSource {

    private final NewsApiService newsApiService;
    private final String apiKey;

    public NewsRemoteDataSource(String apiKey) {
        this.apiKey = apiKey;
        this.newsApiService = ServiceLocator.getInstance().getNewsApiService();
    }
    @Override
    public void getNews(String country) {
        Call<NewsApiResponse> newsResponseCall = newsApiService.getNews(country,
                TOP_HEADLINES_PAGE_SIZE_VALUE, apiKey);

        //fa chiamata tramite retrofit
        newsResponseCall.enqueue(new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsApiResponse> call,
                                   @NonNull Response<NewsApiResponse> response) {

                Log.d(TAG, "response " + response );
                if (response.body() != null && response.isSuccessful() &&
                        !response.body().getStatus().equals("error")) {

                    newsCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                    Log.d(TAG, "dentro if response " + response.body());

                } else {
                    Log.d(TAG, "problema : " + response.body() );
                    Log.d(TAG, "problema : " + response.isSuccessful());
                    //Log.d(TAG, "problema : " + !response.body().getStatus().equals("error"));
                    Log.d(TAG, "NewsRemoteDataSource" );
                    newsCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsApiResponse> call, @NonNull Throwable t) {
                newsCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }
        });
    }
}
