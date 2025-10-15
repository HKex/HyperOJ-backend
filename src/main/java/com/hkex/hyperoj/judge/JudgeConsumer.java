package com.hkex.hyperoj.judge;

import com.hkex.hyperoj.config.RabbitMQConfig;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class JudgeConsumer {

    @Resource
    private JudgeService judgeService;

    @RabbitListener(queues = RabbitMQConfig.CODE_EXECUTE_CONSUMER_QUEUE)
    public void onMessage(@Payload ExecuteCodeResponse result, org.springframework.amqp.core.Message message) {
        try {
            //
            long submitId = 0;
            // 沙箱回调结果，更新判题状态
            judgeService.handleExecuteResponse(submitId, result);
        } catch (Exception e) {
            log.error("Handle ExecuteCodeResponse failed", e);
            throw e;
        }
    }
}


