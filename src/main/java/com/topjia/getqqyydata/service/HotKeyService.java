package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.HotKey;

import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 22:18
 */
public interface HotKeyService {
    List<HotKey> getHotKey() throws Exception;
}
