package com.zfy.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CommonResult
 * @description: 通用返回结果
 **/
@Data
@NoArgsConstructor//空参数构造方法1
@AllArgsConstructor//全部参数构造方法2
public class CommonResult<T> {
    /**
     * 返回code信息
     */
    private Integer code;

    /**
     * 返回的信息
     */
    private String message;

    /**
     * 返回的具体数据
     */
    private T data;

    /**
     * 新增的构造方法3
     * @param code
     * @param message
     */
    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
