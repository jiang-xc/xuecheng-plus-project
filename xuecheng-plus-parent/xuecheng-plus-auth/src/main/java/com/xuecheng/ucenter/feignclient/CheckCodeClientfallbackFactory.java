package com.xuecheng.ucenter.feignclient;

import com.xuecheng.base.exception.XueChengPlusException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CheckCodeClientfallbackFactory implements FallbackFactory<CheckCodeClient> {
    @Override
    public CheckCodeClient create(Throwable throwable) {
        log.error("调用验证码服务熔断异常:{}",throwable.getMessage());
        XueChengPlusException.cast("验证码服务暂不可用。。。");
        return null;
    }
}
