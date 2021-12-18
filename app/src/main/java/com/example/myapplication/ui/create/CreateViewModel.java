package com.example.myapplication.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("생성하려는 그룹의 이름을 입력해주세요.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}