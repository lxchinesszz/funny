package com.funny.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Package: com.funny.model.domain
 * @Description: ${todo}
 * @author: liuxin
 * @date: 2017/9/24 下午5:30
 */
@Data
@Builder
@Document(collection = "funny_banner")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"id", "fileName"})
public class Banner {
    /**
     * 广告也地址
     */
    private String bannerUrl;
    /**
     * 广告描述
     */
    private String bannerText;
    /**
     * type:1.视频 2.网页 3.帖子 4，图片
     */
    private String type;
    /**
     * 时间
     */
    private long timestamp;
}
