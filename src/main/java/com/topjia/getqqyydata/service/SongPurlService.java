package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.Song;

import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 20:07
 */
public interface SongPurlService {
    List<Song> getSongPurl(List<Song> songs) throws Exception;
}
