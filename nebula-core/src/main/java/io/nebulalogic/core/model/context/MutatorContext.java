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


import java.util.Map;
import java.util.function.Consumer;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.model.context
 * @ClassName MutatorContext.java
 * @Description 类 MutatorContext 的实现描述：读写上下文接口 - 主要用于数据注入与结果收集阶段。
 * @Date 2026年01月21日 10:30
 * @Version 1.0.0
 */

public interface MutatorContext extends Context {

    /**
     * 写入单条数据。
     * <p>注意：若 Key 已存在，新值将覆盖旧值。</p>
     * <p><b>架构哲学：</b> 返回 this 并非为了缩短代码，而是为了定义“状态演进流”。</p>
     * <p><b>严谨性约束：</b> 建议在调用时保持“一行一注”，以确保金融级调试的精确性。</p>
     *
     * @param key   键
     * @param value 值（支持基本类型、String、BigDecimal 等，实现类应负责 FinancialValue 的转换）
     * @return 演进后的 MutatorContext 实例
     */
    MutatorContext put(String key, Object value);

    /**
     * 批量注入数据。
     * <p>该方法用于引擎初始化或大规模上下文导入，比循环调用 put 具备更好的性能。</p>
     *
     * @param data 外部数据源
     */
    void putAll(Map<String, Object> data);

    /**
     * 移除数据。
     * <p>高风险操作。在 Action 中调用时应极度谨慎，防止下游规则出现变量缺失导致的逻辑中断。</p>
     *
     * @param key 键名
     */
    void remove(String key);

    /**
     * 在受控闭包中执行上下文的状态演进
     * 允许在一个受控的闭包内完成一系列复杂的“状态迁移”。
     * 这既保持了代码的流畅性，又通过闭合空间隔离了副作用。
     * * <p><b>使用范例：</b></p>
     * <pre>{@code
     * Context finalCtx = bootstrapCtx.evolve(ctx -> {
     * ctx.put("orderId", "TX_1001")
     * .put("amount", "500.00")
     * .put("currency", "CNY");
     * // 可以在此逻辑块中进行复杂的中间计算或外部判定
     * });
     * }</pre>
     *
     * @param action 包含演进逻辑的函数式回调，接收当前的 {@link MutatorContext}
     * @return 演进完成并锁定的只读 {@link Context} 实例
     * @see #asReadOnly()
     */
    default Context evolve(Consumer<MutatorContext> action) {
        action.accept(this);
        return this.asReadOnly();
    }

    /**
     * 强制状态转换
     * <p>将当前的 Mutator 状态锁定。返回的实例将失去写入能力，
     * 任何尝试强转回 MutatorContext 并调用的行为都应抛出异常。</p>
     *
     * @return 不可变的 Context 实例
     */
    @Override
    Context asReadOnly();
}
