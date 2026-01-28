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


import io.nebulalogic.core.model.context.Context;
import io.nebulalogic.core.model.context.ExecutionContext;

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
     * @throws io.nebulalogic.core.exception.NebulaException 如果ctx为null或评估过程中发生错误
     */
    boolean evaluate(ExecutionContext ctx);

    /**
     * 逻辑与组合 (AND)
     *
     * @param other 另一个条件，不应为null
     * @return 组合后的新条件
     * @throws io.nebulalogic.core.exception.NebulaException 如果other为null
     */
    default Condition and(Condition other) {
        return ctx -> this.evaluate(ctx) && other.evaluate(ctx);
    }


    /**
     * 逻辑或组合 (OR)
     * <p>创建新的条件：当前条件 OR 另一个条件</p>
     *
     * @param other 另一个条件，不应为null
     * @return 组合后的新条件
     * @throws io.nebulalogic.core.exception.NebulaException 如果other为null
     */
    default Condition or(Condition other) {
        return ctx -> this.evaluate(ctx) || other.evaluate(ctx);
    }

    /**
     * 逻辑非组合 (NOT)
     * <p>创建新的条件：NOT(当前条件)</p>
     *
     * @return 取反后的新条件
     */
    default Condition negate() {
        return ctx -> !this.evaluate(ctx);
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
