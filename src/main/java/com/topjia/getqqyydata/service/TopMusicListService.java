package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.Song;

import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 22:11
 */
public interface TopMusicListService {
    List<Object> getTopMusicList(String topid) throws Exception;
}
