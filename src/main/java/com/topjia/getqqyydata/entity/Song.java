package com.topjia.getqqyydata.entity;

import lombok.Data;

/**
 * @author wjh
 * @date 2019-11-30 20:21
 */
@Data
public class Song {
    private String id;
    private String mid;
    private String singer;
    private String name;
    private String album;
    private String duration;
    private String image = "https://y.gtimg.cn/mediastyle/yqq/extra/player_cover.png?max_age=31536000";
    private String url;
}
