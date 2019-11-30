package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.Lyric;

/**
 * @author wjh
 * @date 2019-11-30 21:51
 */
public interface LyricService {
    Lyric getLyric(String songmid) throws Exception;
}
