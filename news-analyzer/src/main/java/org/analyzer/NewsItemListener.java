package org.analyzer;

import org.feed.NewsItem;

public interface NewsItemListener {
    void onNewsItemReceived(NewsItem newsItem);
}
