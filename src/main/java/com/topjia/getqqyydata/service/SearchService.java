package com.topjia.getqqyydata.service;

import java.util.HashMap;

/**
 * @author wjh
 * @date 2019-11-30 22:24
 */
public interface SearchService {
    HashMap<String, Object> search(String w, String p, String perpage, String n, String catZhida) throws Exception;
}
