package it.unimib.brain_alarm;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.brain_alarm.Repository.INewsRepositoryWithLiveData;


public class NewsViewModelFactory implements ViewModelProvider.Factory {

    private final INewsRepositoryWithLiveData iNewsRepositoryWithLiveData;

    public NewsViewModelFactory(INewsRepositoryWithLiveData iNewsRepositoryWithLiveData) {
        this.iNewsRepositoryWithLiveData = iNewsRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsViewModel(iNewsRepositoryWithLiveData);
    }
}

