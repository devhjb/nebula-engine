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


/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.exception
 * @ClassName FaultCategory.java
 * @Description 类 FaultCategory 的实现描述：故障分类枚举 - 定义故障的业务领域边界
 * @Date 2026年01月28日 12:19
 * @Version 1.0.0
 */
public enum FaultCategory {

    /**
     * 规则 / 条件 / 动作逻辑错误
     * <p>涉及业务规则执行过程中的逻辑问题</p>
     */
    LOGIC,

    /**
     * 配置 / 模型错误
     * <p>规则配置错误、模型定义不合规等</p>
     */
    CONFIGURATION,

    /**
     * 引擎自身错误
     * <p>引擎运行时内部错误，通常需要开发人员介入</p>
     */
    ENGINE,

    /**
     * 外部系统错误
     * <p>依赖的外部服务或资源不可用</p>
     */
    EXTERNAL,

    /**
     * 用于控制执行流程（非错误）
     * <p>正常的业务流程控制，不应视为系统错误</p>
     */
    CONTROL;

    /**
     * 判断是否为真正的错误（需要告警和处理）
     *
     * @return true/false
     */
    public boolean isError() {
        return this != CONTROL;
    }
}
