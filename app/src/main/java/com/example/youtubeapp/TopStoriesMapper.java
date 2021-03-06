package com.example.youtubeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.youtubeapp.network.dto.MultimediaDTO;
import com.example.youtubeapp.network.dto.NewsDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public final class TopStoriesMapper {

    private static final String MULTIMEDIA_TYPE_IMAGE = "image";

    private TopStoriesMapper() {
        throw new AssertionError("Must be no instance");
    }

    static List<NewsItem> map(@NonNull List<NewsDTO> dtos) {

        final List<NewsItem> items = new ArrayList<>();

        for (NewsDTO dto : dtos) {
            final NewsItem newsItem = mapItem(dto);
            items.add(newsItem);
        }
        return Collections.unmodifiableList(items);
    }


    private static NewsItem mapItem(@NonNull NewsDTO dto) {
        final String title = dto.getTitle();
        final String uid = "";
        final List<MultimediaDTO> multimedia = dto.getMultimedia();
        final String imageUrl = mapImage(multimedia);

        final String category = dto.getSubsection();
        final String publishDate = dto.getPublished_date();
        final String preview = dto.getAbstract();
        final String url = dto.getUrl();

        return NewsItem.create(
                uid,
                title,
                imageUrl,
                category,
                publishDate,
                preview,
                url);
    }

    @Nullable
    private static String mapImage(@Nullable List<MultimediaDTO> multimedias) {

        if (multimedias == null || multimedias.isEmpty()) {
            return null;
        }

        final int imageImMaximumQutilityIndex = multimedias.size() - 1;
        final MultimediaDTO multimedia = multimedias.get(imageImMaximumQutilityIndex);

        if (!multimedia.getType().equals(MULTIMEDIA_TYPE_IMAGE)) {
            return null;
        }

        return multimedia.getUrl();
    }
}
