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
 * @ClassName EngineFault.java
 * @Description 类 EngineFault 的实现描述：引擎故障抽象基类 - Nebula-Engine 统一的异常体系核心
 * <p>设计哲学：在内核中，我们不谈“错误”，只谈“故障（Fault）”。</p>
 * <p>每一个故障都具备：确定的编码、明确的分类以及现场的属性快照。</p>
 * @Date 2026年01月28日 11:45
 * @Version 1.0.0
 */
public abstract class EngineFault extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 机器可读的错误码
     * <p>用于程序化错误处理、监控指标统计、国际化消息映射等场景</p>
     */
    private final ErrorCode errorCode;

    /**
     * 故障所属的领域分类
     * <p>为故障处理提供第一层分类维度，便于不同领域采用不同的处理策略</p>
     */
    private final FaultCategory category;

    /**
     * 故障发生时的现场快照（如变量名、非法值等）
     * <p>记录故障相关的关键变量、参数值、状态信息等，用于问题诊断和审计</p>
     */
    private final Map<String, Object> attributes;

    /**
     * 完整构造器
     *
     * @param errorCode  错误码，不可为null
     * @param category   故障分类，不可为null
     * @param message    人类可读的错误描述，用于日志和调试
     * @param cause      原始异常（如果是包装其他异常）
     * @param attributes 故障现场的快照信息，可为null
     * @throws ConfigurationFault 如果errorCode或category为null
     */
    protected EngineFault(
            ErrorCode errorCode,
            FaultCategory category,
            String message,
            Throwable cause,
            Map<String, Object> attributes
    ) {
        // 构建格式化的异常消息：[类别] 错误码: 描述
        super(String.format("[%s] %s: %s", category, errorCode.code(), message), cause);

        this.errorCode = errorCode;
        this.category = category;

        // 确保attributes的不可变性
        this.attributes = attributes == null ? Map.of() : Map.copyOf(attributes);
    }

    /**
     * 快捷构造器（无cause和attributes）
     */
    protected EngineFault(
            ErrorCode errorCode,
            FaultCategory category,
            String message
    ) {
        this(errorCode, category, message, null, null);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public FaultCategory getCategory() {
        return category;
    }

    /**
     * 获取故障现场的快照信息
     *
     * @return 不可变的属性Map，永远不会返回null
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
