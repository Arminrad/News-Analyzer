package org.feed;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NewsItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String headline;
    private int priority;
    private LocalDateTime receivedTime = LocalDateTime.now();

    public NewsItem(String headline, int priority) {
        this.headline = headline;
        this.priority = priority;
    }

    public NewsItem() {
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(LocalDateTime receivedTime) {
        this.receivedTime = receivedTime;
    }
}
