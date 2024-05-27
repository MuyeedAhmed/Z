package com.max.z.ui.misc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MiscViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MiscViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the misc fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
