package com.xuecheng.orders.service;

import com.xuecheng.orders.model.po.XcPayRecord;

public interface XcPayRecordService {


    /**
     * @description 查询支付记录
     * @param payNo  交易记录号
     * @return com.xuecheng.orders.model.po.XcPayRecord
     * @author Mr.M
     * @date 2022/10/20 23:38
     */
    public XcPayRecord getPayRecordByPayno(String payNo);
}
