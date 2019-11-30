package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.Song;

import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 21:24
 */
public interface CDInfoService {
    List<Song> getCDInfo(String disstid) throws Exception;
}
