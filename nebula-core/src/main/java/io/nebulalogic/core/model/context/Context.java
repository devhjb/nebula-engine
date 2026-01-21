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


import io.nebulalogic.core.model.types.FinancialValue;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.model.context
 * @ClassName Context.java
 * @Description 类 Context 的实现描述：Nebula-Engine 核心只读上下文接口 - 闭合架构中的数据载体视图。
 * @Date 2026年01月21日 10:18
 * @Version 1.0.0
 */

public interface Context {

    /**
     * 获取原始数据对象
     * ConditionEvaluator 禁止直接使用 get(String)，仅允许 Action 或 Trace 使用
     *
     * @param key 键名
     * @return 原始对象，若不存在则返回 null
     */
    Object get(String key);

    /**
     * 获取金融级数值。
     * <p>所有涉及金额、利率、费率、权重的读取，必须通过此方法获取，以确保进入引擎的数值符合金融级契约。</p>
     *  <ul>
     *     <li>如果原始对象已是 FinancialValue，应直接返回。</li>
     *     <li>如果是 String/Number，则进行转换并保证单向一致性。</li>
     *     <li>避免重复转换带来的性能损耗。</li>
     * </ul>
     *
     * @param key 键名
     * @return FinancialValue 实例
     * @throws com.nebula.engine.core.exception.LogicException 若数据无法转换为数值类型
     */
    FinancialValue getFinancial(String key);

    /**
     * 获取强类型数据。
     *
     * @param key  键名
     * @param type 目标类型
     * @param <T>  类型泛型
     * @return 封装在 Optional 中的强类型对象
     */
    @SuppressWarnings("unchecked")
    default <T> Optional<T> getTyped(String key, Class<T> type) {
        Object val = get(key);
        if (type.isInstance(val)) {
            return Optional.of((T) val);
        }
        return Optional.empty();
    }

    /**
     * 获取布尔值
     *
     * @param key 键名
     * @return 布尔结果
     */
    default Boolean getBoolean(String key) {
        Object val = get(key);
        if (val instanceof Boolean b) return b;
        if (val instanceof String s) return Boolean.parseBoolean(s);
        return false;
    }

    /**
     * 获取字符串类型数据
     * Condition 中不建议使用 getString，Parser 层应优先构建 LiteralExpression
     *
     * @param key 键名
     * @return 字符串值，若不存在则返回空字符串
     */
    default String getString(String key) {
        Object val = get(key);
        return val == null ? "" : String.valueOf(val).trim();
    }

    /**
     * 检查键是否存在
     *
     * @param key 键名
     * @return 是否存在
     */
    default boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * 转换为 Map 结构（通常用于序列化或 Trace 记录）
     *
     * @return 包含所有数据的只读 Map
     */
    Map<String, Object> asMap();

    /**
     * 获取当前上下文的所有键名集合
     *
     * @return 只读的键名集合
     */
    Set<String> keySet();

    /**
     * 转换为只读视图。
     * <p>若当前已是只读，则返回自身</p>
     *
     * @return 不可变的 Context 实例
     */
    Context asReadOnly();
}
