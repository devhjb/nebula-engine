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
package io.nebulalogic.core.model.context;


/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.model.context
 * @ClassName ExecutionPhase.java
 * @Description 类 ExecutionPhase 的实现描述：执行阶段 - 内核级的语义边界定义枚举
 * @Date 2026年01月28日 09:43
 * @Version 1.0.0
 */
public enum ExecutionPhase {

    /**
     * 前置准备 - 全局只读
     * <p>规则链初始化、上下文验证等</p>
     */
    PRE_EVALUATION,

    /**
     * 条件判断 - 严格只读
     * <p>所有 Condition 在此阶段执行，禁止任何写操作</p>
     */
    CONDITION,

    /**
     * 动作执行 - 授予写能力
     * <p>Action 在此阶段执行，允许修改上下文</p>
     */
    ACTION,

    /**
     * 执行后收敛 - 回归只读
     * <p>结果格式化、审计日志等最终处理</p>
     */
    POST_ACTION;


    /**
     * 检查当前阶段是否允许写操作
     *
     * @return
     */
    public boolean isWritable() {
        return this == ACTION;
    }

}
