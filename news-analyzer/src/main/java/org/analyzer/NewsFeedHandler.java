package org.analyzer;

import org.feed.NewsItem;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class NewsFeedHandler implements NewsItemListener {
    private final String[] positiveHeadline = {"up", "rise", "good", "success", "high"};
    private static final List<NewsItem> positiveNews = new ArrayList<>();

    @Override
    public void onNewsItemReceived(NewsItem newsItem) {
        boolean isPositive = checkPositiveHeadline(newsItem);
        if (isPositive) {
            positiveNews.add(newsItem);
        }
    }

    public static TimerTask showMessage() {
        System.out.println(showPositiveNewsCount());
        System.out.println(showUniqueHeadline());
        return null;
    }

    static class ShowTask extends TimerTask {
        @Override
        public void run() {
            showMessage();
        }
    }


    public static long showPositiveNewsCount() {
        return positiveNews.stream()
                .filter(item -> item.getReceivedTime().isAfter(LocalDateTime.now().minusSeconds(10)))
                .count();
    }

    public static List<String> showUniqueHeadline() {
        return positiveNews.stream()
                .sorted(Comparator.comparingInt(NewsItem::getPriority).reversed())
                .limit(3)
                .map(NewsItem::getHeadline)
                .collect(Collectors.toList());
    }

    private boolean checkPositiveHeadline(NewsItem newsItem) {
        String newsHeadline = newsItem.getHeadline();
        int totalWordCount = wordList(newsHeadline).size();
        Map<String, Long> wordCount = Arrays.stream(positiveHeadline)
                .collect(Collectors.toMap(Function.identity(), word -> countOccurrences(newsHeadline, word)));
        long positiveWordCount = wordCount.values().stream().collect(Collectors.summarizingLong(Long::longValue)).getCount();
        return positiveWordCount > ((totalWordCount / 2) + 1);
    }

    private List<String> wordList(String inputString) {
        return Arrays.stream(inputString.split("[,\\s]+"))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }

    private long countOccurrences(String text, String word) {
        List<String> wordList = wordList(text);
        return wordList.stream()
                .filter(word::equalsIgnoreCase)
                .count();
    }
}
