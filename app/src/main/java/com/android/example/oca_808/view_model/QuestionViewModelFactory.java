package com.android.example.oca_808.view_model;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.io.UncheckedIOException;

/**
 * Created by charlotte on 12/13/17.
 */

public class QuestionViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;

    public QuestionViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(QuestionsViewModel.class)) {
            return (T) new QuestionsViewModel(mApplication);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
