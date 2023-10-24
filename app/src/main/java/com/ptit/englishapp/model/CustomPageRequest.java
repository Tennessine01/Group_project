package com.ptit.englishapp.model;

import com.google.gson.annotations.SerializedName;

public class CustomPageRequest {
    @SerializedName("key_search")
    private String keySearch;

    @SerializedName("page_number")
    private Integer pageNumber;

    @SerializedName("page_size")
    private Integer pageSize;

    public CustomPageRequest() {
    }

    public CustomPageRequest(String keySearch, Integer pageNumber, Integer pageSize) {
        this.keySearch = keySearch;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
