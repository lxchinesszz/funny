package com.funny.util;


import com.funny.config.status.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.collections.map.HashedMap;


import java.util.Map;

/**
 * @Package: com.example.MJsonBuilder
 * @Description: 动态构建json
 * @author: liuxin
 * @date: 17/3/30 下午4:56
 */
public class ResponseBuilder {
    private Map<Object, Object> map = new HashedMap();
    private Gson GSON = new Gson();
    private static final Gson stastusGson = new Gson();

    /**
     * 生成指定字段类型的json对象
     *
     * @param key
     * @param value
     * @param type
     * @return
     */
    public ResponseBuilder set(String key, Object value, Class<?> type) {
        map.put(key, type.cast(value));
        return this;
    }


    /**
     * 格式化
     *
     * @return
     */
    public ResponseBuilder setPrettyPrint() {
        GSON = new GsonBuilder().setPrettyPrinting().create();
        return this;
    }

    public ResponseBuilder create() {
        return this;
    }

    public String toJson() {
        return GSON.toJson(map);
    }

    /**
     * 成功
     *
     * @param date
     * @return
     */
    public static String SUCCESS(Object date) {
        IResponseVo responseVo = new ResponseBuilder().new ResponseShortVo(0,"");
        return stastusGson.toJson(responseVo);
    }

    /**
     * 成功
     *
     * @param date
     * @return
     */
    public static ResponseVo SUCCESSByJackson(Object date) {
        ResponseVo responseVo = new ResponseBuilder().new ResponseVo(date);
        return responseVo;
    }


    /**
     * 成功
     */
    public static String SUCCESS() {
        return String.format("{\"code\":%d,\"message\":%s}", 0, "处理成功");
    }

    public static ResponseShortVo SUCCESSByJackson() {
        ResponseShortVo responseShortVo = new ResponseBuilder().new ResponseShortVo(ResponseStatus.SUCCESS);
        return responseShortVo;
    }

    /**
     * 失败传入失败状态
     *
     * @return
     */
    public static String ERROR(ResponseStatus responseStatus) {
        return String.format("{\"code\":%d,\"message\":%s}", responseStatus.getCode(), responseStatus.getMessage());
    }


    /**
     * 失败传入失败状态
     *
     * @return
     */
    public static String ERROR(int code, String message) {
        return String.format("{\"code\":%d,\"message\":%s}", code, message);
    }

    /**
     * 失败传入失败状态
     * 使用jackson渲染
     *
     * @return
     */
    public static ResponseShortVo ERRORByJackson(Object responseStatus) {
        ResponseShortVo responseShortVo = new ResponseBuilder().new ResponseShortVo(0,"");
        return responseShortVo;
    }


    /**
     * 失败传入失败状态
     * 使用jackson渲染
     *
     * @return
     */
    public static ResponseShortVo ERRORByJackson(int code, String message) {
        ResponseShortVo responseShortVo = new ResponseBuilder().new ResponseShortVo(code, message);
        return responseShortVo;
    }

    class ResponseVo implements IResponseVo{
        public int code;

        public String message;

        public Object data;

        ResponseVo(Object obj){
           this.code=0;
           this.message="操作成功";
           this.data=obj;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    class ResponseShortVo implements IResponseVo {
        public int code;

        public String message;
        public ResponseShortVo(ResponseStatus responseStatus) {
            this.code = responseStatus.getCode();
            this.message = responseStatus.getMessage();
        }


        public ResponseShortVo(int code, String msg) {
            this.code = code;
            this.message = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public interface IResponseVo {
    }
}
