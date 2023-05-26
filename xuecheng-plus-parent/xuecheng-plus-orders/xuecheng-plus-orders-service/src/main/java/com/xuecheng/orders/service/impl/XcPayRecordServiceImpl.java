package com.xuecheng.orders.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.orders.config.AlipayConfig;
import com.xuecheng.orders.mapper.XcPayRecordMapper;
import com.xuecheng.orders.model.po.XcPayRecord;
import com.xuecheng.orders.service.XcPayRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;




@Slf4j
@Service
public class XcPayRecordServiceImpl implements XcPayRecordService {


    @Autowired
    private XcPayRecordMapper xcPayRecordMapper;


    @Override
    public XcPayRecord getPayRecordByPayno(String payNo) {

        XcPayRecord xcPayRecord = xcPayRecordMapper.selectOne(new LambdaQueryWrapper<XcPayRecord>().eq(XcPayRecord::getPayNo, payNo));
        return xcPayRecord;
    }
}
