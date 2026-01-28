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
 * @ClassName ExecutionInterrupted.java
 * @Description 类 ExecutionInterrupted 的实现描述：执行中断异常 - 用于业务流程的控制性中断
 * <p>这不是错误，而是正常的业务流程控制手段</p>
 * <p>将流程控制与错误处理分离，避免滥用异常控制流程</p>
 * <ul>
 *     <li>规则命中断言后续规则</li>
 *     <li>手动中断规则链执行</li>
 *     <li>条件不满足时的正常退出</li>
 * </ul>
 * @Date 2026年01月28日 12:24
 * @Version 1.0.0
 */

public final class ExecutionInterrupted extends EngineFault {

    /**
     * 构造执行中断
     *
     * @param reason 中断原因描述
     */
    public ExecutionInterrupted(String reason) {
        super(
                EngineErrorCode.PHASE_ILLEGAL,
                FaultCategory.CONTROL,
                reason
        );
    }

    /**
     * 执行中断不需要诊断属性，所以不继承DescriptiveFault
     * 保持轻量级设计
     */
    @Override
    public String toString() {
        return String.format("[CONTROL] Execution interrupted: %s", getMessage());
    }
}
