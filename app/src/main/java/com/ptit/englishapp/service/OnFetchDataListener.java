package com.ptit.englishapp.service;

import com.ptit.englishapp.oxford.APIResponse;

public interface OnFetchDataListener {
    void onFetchData(APIResponse apiResponse, String message);
    void onError(String message);

}
