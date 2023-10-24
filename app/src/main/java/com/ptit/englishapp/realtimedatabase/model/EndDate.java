package com.ptit.englishapp.realtimedatabase.model;

import java.sql.Timestamp;
import java.util.Date;


public class EndDate {
    private boolean isNeverEnd;
    private long dateEnd;

    public EndDate() {
    }

    public EndDate(boolean isNeverEnd, long dateEnd) {
        this.isNeverEnd = isNeverEnd;
        this.dateEnd = dateEnd;
    }

    public boolean isNeverEnd() {
        return isNeverEnd;
    }

    public void setNeverEnd(boolean neverEnd) {
        isNeverEnd = neverEnd;
    }

    public long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(long dateEnd) {
        this.dateEnd = dateEnd;
    }
}
