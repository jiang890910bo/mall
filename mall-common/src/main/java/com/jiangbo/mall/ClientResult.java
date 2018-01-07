package com.jiangbo.mall;

import com.jiangbo.mall.enums.ClientResultStatusEnum;

/**
 * @author Jiangbo Cheng on 2017/10/26.
 */

public class ClientResult<T> {

    /**
     * 状态
     */
    private Integer status;
    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 返回结果
     */
    private T result;

    public ClientResult() {
        this.status = ClientResultStatusEnum.ERROR.getNum();
        this.errorMsg = ClientResultStatusEnum.ERROR.getDesc();
    }

    public ClientResult(ClientResultStatusEnum status) {
        this.status = status.getNum();
        this.errorMsg = status.getDesc();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
