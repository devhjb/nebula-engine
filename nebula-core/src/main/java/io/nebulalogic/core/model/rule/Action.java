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
package io.nebulalogic.core.model.rule;


import io.nebulalogic.core.exception.ConfigurationFault;
import io.nebulalogic.core.exception.EngineErrorCode;
import io.nebulalogic.core.exception.EngineFault;
import io.nebulalogic.core.exception.LogicFault;
import io.nebulalogic.core.model.context.MutatorContext;

import java.util.Map;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.model.rule
 * @ClassName Action.java
 * @Description 类 Action 的实现描述：动作执行接口 - 规则触发后的业务操作
 * <p>每个Action代表一个独立的业务操作单元</p>
 * <ul>
 *     <li><b>专注执行：</b>只关心"做什么"，不判断"是否该做"</li>
 *     <li><b>可组合性：</b>多个Action可以组合成复杂业务流程</li>
 *     <li><b>异常处理：</b>应妥善处理异常，避免影响后续Action</li>
 * </ul>
 * @Date 2026年01月28日 11:00
 * @Version 1.0.0
 */
@FunctionalInterface
public interface Action {

    /**
     * 执行动作逻辑
     * <p>引擎保证执行时授予了写权限的MutatorContext</p>
     * <p><b>设计哲学：</b>Action不判断条件，完全信任调用方的决策</p>
     *
     * @param ctx 已授予写能力的上下文，不应为null
     * @throws RuntimeException                                 动作执行失败时应抛出运行时异常
     * @throws io.nebulalogic.core.exception.ConfigurationFault 如果ctx为null或参数配置错误
     */
    void execute(MutatorContext ctx);

    /**
     * 动作组合：顺序执行
     * <p>创建新的动作：先执行当前动作，再执行另一个动作</p>
     *
     * @param next 下一个动作，不应为null
     * @return 组合后的新动作
     * @throws io.nebulalogic.core.exception.ConfigurationFault 如果next为null
     */
    default Action andThen(Action next) {
        if (next == null) {
            throw new ConfigurationFault(
                    EngineErrorCode.CONFIGURATION_ERROR,
                    "Action chain requires non-null next action",
                    Map.of("operation", "andThen", "context", "action_chaining")
            );
        }

        return ctx -> {
            try {
                this.execute(ctx);
                next.execute(ctx);
            } catch (EngineFault e) {
                // 保留原始异常信息，添加上下文
                if (e instanceof LogicFault) {
                    throw ((LogicFault) e).withAttribute("actionChain", "sequential");
                }
                throw e;
            }
        };
    }

    /**
     * 静态方法：创建空动作（无操作）
     *
     * @return 空动作实例
     */
    static Action noop() {
        return ctx -> {
            //  intentionally do nothing
        };
    }

    /**
     * 静态方法：创建设置变量的动作
     *
     * @param key   变量名
     * @param value 变量值
     * @return 设置变量动作
     */
    static Action setVariable(String key, Object value) {
        return ctx -> {
            if (ctx == null) {
                throw new ConfigurationFault(
                        EngineErrorCode.CONFIGURATION_ERROR,
                        "MutatorContext cannot be null for variable assignment",
                        Map.of("key", key, "value", value)
                );
            }
            try {
                ctx.put(key, value);
            } catch (EngineFault e) {
                throw new LogicFault(
                        EngineErrorCode.ACTION_EXEC_ERROR,
                        "Failed to set variable",
                        Map.of("key", key, "value", value, "nestedFault", e.getMessage())
                );
            }
        };
    }
}
