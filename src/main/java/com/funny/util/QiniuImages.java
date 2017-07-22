package com.funny.util;

import com.qiniu.util.Auth;

/**
 * @Package: com.funny.util
 * @Description: 工具类，根据文件名获取，真实地址
 * @author: liuxin
 * @date: 2017/7/22 下午11:09
 */
public class QiniuImages {

    /**
     * https://os76ha42j.bkt.clouddn.com/13162211551.png?e=1500353864&token=3jws4LSQj3Nwi_bWktpNReSf2Rh4D4CU6rTcZlrA:ILGAoi2lnSFUM0j1BqMdeNMSQ8I=
     * @param fileName
     * @return
     */
    public static String getQiniuImgUrl(String fileName){
        String ak = "3jws4LSQj3Nwi_bWktpNReSf2Rh4D4CU6rTcZlrA";
        String sk = "WrROm6H4tHqcQ5ZlosarRLIXn1OE8WcKv9XtSpTF";
        String bucket = "funny";
        long expireSeconds = 10;
        Auth auth = Auth.create(ak, sk);
        String url ="https://os76ha42j.bkt.clouddn.com/"+fileName;
        return auth.privateDownloadUrl(url);
    }
}
