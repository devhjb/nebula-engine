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


import io.nebulalogic.core.model.context.ExecutionContext;

import java.util.List;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.model.rule
 * @ClassName Rule.java
 * @Description 类 Rule 的实现描述：规则模型 - Nebula-Engine 的核心业务逻辑单元
 * <p>规则采用经典的 Condition-Action 模式：当条件满足时，执行对应的动作序列</p>
 * <ul>
 *     <li>规则是业务意图的直接映射，不应包含技术实现细节</li>
 *     <li>Condition 和 Action 通过统一的 Context 进行数据交换</li>
 *     <li>优先级机制确保规则执行的确定性</li>
 * </ul>
 * @Date 2026年01月21日 12:38
 * @Version 1.0.0
 */

public interface Rule {


    /**
     * 规则唯一标识
     * <p>用于规则识别、持久化、追踪等场景，应保证全局唯一</p>
     *
     * @return 规则ID，不应为null或空字符串
     */
    String getId();

    /**
     * 规则名称（用于业务描述）
     * <p>面向业务人员的可读描述，用于界面展示和日志输出</p>
     *
     * @return 规则名称，可为null（但推荐提供有意义的名称）
     */
    String getName();

    /**
     * 获取规则条件逻辑
     * <p>规则评估的"大脑"，决定何时触发规则动作</p>
     * <p><b>契约：</b>Condition 执行过程必须是幂等的且无副作用的</p>
     *
     * @return 条件实例，不应为null
     */
    Condition getCondition();

    /**
     * 获取规则动作序列
     * <p>当条件满足时，按顺序执行的业务操作集合</p>
     * <p><b>注意：</b>动作执行顺序对业务逻辑至关重要</p>
     *
     * @return 动作列表，可为空列表（但不建议），不为null
     */
    List<Action> getActions();

    /**
     * 获取规则执行优先级
     * <p>在规则集中，数值越小优先级越高。默认优先级为0</p>
     * <p><b>使用场景：</b></p>
     * <ul>
     *     <li>同一业务事件的多个规则排序</li>
     *     <li>短路优化：高优先级规则命中后可跳过后续规则</li>
     * </ul>
     *
     * @return 优先级数值，默认实现返回0
     */
    default int getPriority() {
        return 0;
    }

    /**
     * 规则描述信息（可选）
     * <p>提供更详细的业务说明，辅助规则理解和维护</p>
     *
     * @return 描述信息，可为null
     */
    default String getDescription() {
        return null;
    }

    /**
     * 检查规则是否启用
     * <p>禁用规则将被引擎完全忽略，不参与任何评估</p>
     *
     * @return true表示规则启用，默认返回true
     */
    default boolean isEnabled() {
        return true;
    }

    /**
     * 获取规则版本号
     * <p>支持规则版本管理和灰度发布</p>
     *
     * @return 版本号字符串，默认返回"1.0"
     */
    default String getVersion() {
        return "1.0";
    }
}
