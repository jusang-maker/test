package Spring_Test.util;

import lombok.Data;

/**
 * 使用泛型类统一json数据格式
 * @param <T>
 */
@Data
public class JsonResult<T> {
    private T data;
    private String code;
    private String msg;

    /**
     * 若没有数据返回，默认状态码为0，提示信息为：操作成功！
     */
    public JsonResult() {
        this.code = "200";
        this.msg = "操作成功！";
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param msg
     */
    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为0，默认提示信息为：操作成功！
     * @param data
     */
    public JsonResult(T data) {
        this.data= data;
        this.code = "200";
        this.msg = "操作成功！";
    }

    /**
     * 有数据返回，状态码为0，人为指定提示信息
     * @param data
     * @param msg
     */
    public JsonResult(T data, String msg) {
        this.data= data;
        this.code = "200";
        this.msg = msg;
    }
    /**
     * 无数据返回时,认为设置提示信息
     */
    public JsonResult(String msg) {
        this.code = "200";
        this.msg = msg;
    }
}
