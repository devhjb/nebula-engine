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


import java.util.Optional;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.model.context
 * @ClassName ExecutionContext.java
 * @Description 类 ExecutionContext 的实现描述： 规则执行的语义边界模型 - 内核级的执行上下文抽象
 * <p>核心哲学：ExecutionContext 是规则执行的「世界模型」，不是执行器的「内部状态容器」</p>
 * @Date 2026年01月27日 22:08
 * @Version 1.0.0
 */

public interface ExecutionContext {


    /**
     * 返回规则执行可见的不可变数据视图。
     * <p>此上下文代表规则评估期间的唯一数据源</p>
     * <p>所有 {@code Condition} 逻辑必须仅从此视图读取数据</p>
     * <p>返回的 {@link Context} 保证是只读的，并且在任何情况下都不得进行类型转换或假定为可变的。</p>
     *
     * @return the read-only execution context
     */
    Context context();

    /**
     * 如果可用，则返回当前阶段授予的变更权限。
     * <p>变更是一种<strong>特权权限</strong>，仅在特定执行阶段（通常是 {@code ACTION}）授予。</p>
     * <p>调用者不得假定始终具有写入权限。</p>
     *
     * <p>哲学：写能力是「阶段性授予的特权」，不是「与生俱来的权利」</p>
     * <p>如果缺少 {@link MutatorContext}，则表示当前阶段强制执行严格的只读执行契约.</p>
     *
     * @return an {@link Optional} containing the {@link MutatorContext} if mutation
     * is permitted in the current phase; otherwise {@link Optional#empty()}
     */
    Optional<MutatorContext> mutator();

    /**
     * 返回当前语义执行阶段
     * <p>执行阶段定义了一个<strong>语义边界</strong>，而不是一个过程步骤。阶段转换决定：</p>
     * <ul>
     * <li>是否允许修改</li>
     * <li>允许执行哪些规则组件</li>
     * <li>必须保留哪些不变式</li>
     * </ul>
     *
     * @return the current {@link ExecutionPhase}
     */
    ExecutionPhase getPhase();

    /**
     * 将当前执行上下文冻结为严格的只读状态。
     * <p>调用后，无论当前执行阶段如何，都不允许对底层数据进行任何进一步的修改。</p>
     * <p>此操作表示一个<strong>语义锁</strong>，而不是快照或副本。它保证在同一执行时间线内数据的不可变性。</p>
     *
     * @return an immutable {@link Context} representing the frozen state
     */
    Context freeze();
}
