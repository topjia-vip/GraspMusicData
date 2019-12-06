package com.topjia.getqqyydata.entity;

import lombok.Data;

import java.util.List;

/**
 * @author wjh
 * @date 2019-12-06 21:33
 */
@Data
public class Top {
    private String topId;
    private String picUrl;
    private String topTitle;
    private String updateTips;
    private List<RankSong> rankSong;
}
