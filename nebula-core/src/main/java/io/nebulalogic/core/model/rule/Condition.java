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
import io.nebulalogic.core.model.context.Context;
import io.nebulalogic.core.model.context.ExecutionContext;

import java.util.Map;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.model.rule
 * @ClassName Condition.java
 * @Description 类 Condition 的实现描述：条件评估接口 - 规则触发条件的抽象
 * @Date 2026年01月21日 12:39
 * @Version 1.0.0
 */
@FunctionalInterface
public interface Condition {

    /**
     * 执行条件评估
     *
     * @param ctx 仅提供只读视图的ExecutionContext，不应为null
     * @return true表示条件满足，规则应该执行
     * @throws LogicFault         如果评估过程中发生业务逻辑错误
     * @throws ConfigurationFault 如果ctx为null或参数配置错误
     */
    boolean evaluate(ExecutionContext ctx);

    /**
     * 逻辑与组合 (AND)
     *
     * @param other 另一个条件，不应为null
     * @return 组合后的新条件
     * @throws ConfigurationFault 如果other为null
     */
    default Condition and(Condition other) {
        if (other == null) {
            throw new ConfigurationFault(
                    EngineErrorCode.CONFIGURATION_ERROR,
                    "Condition AND operation requires non-null operand",
                    Map.of("operation", "AND", "context", "condition_composition")
            );
        }

        return ctx -> {
            try {
                return this.evaluate(ctx) && other.evaluate(ctx);
            } catch (EngineFault e) {
                // 包装组合条件评估中的异常，保留原始信息
                throw new LogicFault(
                        EngineErrorCode.CONDITION_EVAL_ERROR,
                        "Condition AND evaluation failed",
                        Map.of("nestedFault", e.getMessage())
                ).withAttribute("operation", "AND");
            }
        };
    }


    /**
     * 逻辑或组合 (OR)
     * <p>创建新的条件：当前条件 OR 另一个条件</p>
     *
     * @param other 另一个条件，不应为null
     * @return 组合后的新条件
     * @throws ConfigurationFault 如果other为null
     */
    default Condition or(Condition other) {
        if (other == null) {
            throw new ConfigurationFault(
                    EngineErrorCode.CONFIGURATION_ERROR,
                    "Condition OR operation requires non-null operand",
                    Map.of("operation", "OR", "context", "condition_composition")
            );
        }

        return ctx -> {
            try {
                return this.evaluate(ctx) || other.evaluate(ctx);
            } catch (EngineFault e) {
                throw new LogicFault(
                        EngineErrorCode.CONDITION_EVAL_ERROR,
                        "Condition OR evaluation failed",
                        Map.of("nestedFault", e.getMessage())
                ).withAttribute("operation", "OR");
            }
        };
    }

    /**
     * 逻辑非组合 (NOT)
     * <p>创建新的条件：NOT(当前条件)</p>
     *
     * @return 取反后的新条件
     */
    default Condition negate() {
        return ctx -> {
            try {
                return !this.evaluate(ctx);
            } catch (EngineFault e) {
                throw new LogicFault(
                        EngineErrorCode.CONDITION_EVAL_ERROR,
                        "Condition negation evaluation failed",
                        Map.of("nestedFault", e.getMessage())
                ).withAttribute("operation", "NOT");
            }
        };
    }

    /**
     * 静态方法：创建总是为true的条件
     *
     * @return 恒真条件
     */
    static Condition always() {
        return ctx -> true;
    }

    /**
     * 静态方法：创建总是为false的条件
     *
     * @return 恒假条件
     */
    static Condition never() {
        return ctx -> false;
    }

}
