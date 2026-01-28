/*
 * Copyright 2026 jabbey and Nebula-Engine contributors
 * Nebula-Engine: A lightweight, rule-driven automation engine designed for complex SaaS business logic.
 *
 * "Nebulae are silent, but destined to collapse into stars."
 *
 * Licensed under the Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0)
 *
 * Project: https://github.com/devhjb/nebula-engine
 */
package io.nebulalogic.core.exception;


import java.util.HashMap;
import java.util.Map;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.exception
 * @ClassName LogicFault.java
 * @Description 类 LogicFault 的实现描述：逻辑故障 - 规则引擎核心业务逻辑相关的故障
 * <p>用于表示规则、条件、动作执行过程中的业务逻辑错误</p>
 * <ul>
 *     <li>条件评估失败（如数据类型不匹配）</li>
 *     <li>动作执行违反业务约束</li>
 *     <li>规则间的逻辑冲突</li>
 * </ul>
 * @Date 2026年01月28日 14:10
 * @Version 1.0.0
 */

public class LogicFault extends DescriptiveFault {

    /**
     * 构造逻辑故障
     *
     * @param errorCode 逻辑相关的错误码
     * @param message   具体的故障描述
     * @param attrs     相关的业务参数和状态信息
     */
    public LogicFault(ErrorCode errorCode, String message, Map<String, Object> attrs) {
        super(errorCode, FaultCategory.LOGIC, message, attrs);
    }

    @Override
    public LogicFault withAttribute(String key, Object value) {
        Map<String, Object> next = new HashMap<>(attributes);
        next.put(key, value);
        return new LogicFault(getErrorCode(), getMessage(), next);
    }

    /**
     * 便捷工厂方法 - 创建空的逻辑故障
     */
    public static LogicFault of(ErrorCode errorCode, String message) {
        return new LogicFault(errorCode, message, null);
    }

    /**
     * 便捷工厂方法 - 创建带单个属性的逻辑故障
     */
    public static LogicFault of(ErrorCode errorCode, String message, String key, Object value) {
        return new LogicFault(errorCode, message, Map.of(key, value));
    }
}
