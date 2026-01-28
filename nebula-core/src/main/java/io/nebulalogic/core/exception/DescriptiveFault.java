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


import java.util.Map;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.exception
 * @ClassName DescriptiveFault.java
 * @Description 类 DescriptiveFault 的实现描述：可描述故障抽象基类 - 为故障提供结构化的诊断信息支持
 * <p>设计哲学：故障不仅仅是错误消息，更应该包含丰富的上下文信息以便于诊断和修复</p>
 *
 * <p><b>核心特性：</b></p>
 * <ul>
 *     <li><b>不可变属性：</b>所有诊断属性在构造时确定，确保线程安全</li>
 *     <li><b>诊断增强：</b>支持在异常传播过程中补充诊断信息</li>
 *     <li><b>模板方法：</b>强制子类实现属性扩展逻辑，确保一致性</li>
 * </ul>
 * @Date 2026年01月28日 14:47
 * @Version 1.0.0
 */

public abstract class DescriptiveFault extends EngineFault {

    /**
     * 结构化诊断属性（只读）
     * <p>用于携带与当前故障相关的上下文信息，</p>
     * <p>该 Map 在构造时即被防御性复制，确保不可变性。</p>
     */
    protected final Map<String, Object> attributes;

    /**
     * 构造可描述故障
     *
     * @param errorCode  错误码，用于机器识别
     * @param category   故障分类，定义业务领域边界
     * @param message    人类可读的故障描述
     * @param attributes 诊断属性集合，可为null（转换为空Map）
     */
    protected DescriptiveFault(
            ErrorCode errorCode,
            FaultCategory category,
            String message,
            Map<String, Object> attributes
    ) {
        super(errorCode, category, message);
        this.attributes = attributes == null ? Map.of() : Map.copyOf(attributes);
    }

    /**
     * 获取诊断属性集合
     *
     * @return 不可变的诊断属性Map
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 获取特定诊断属性的值
     *
     * @param key 属性键名
     * @return 属性值，如果不存在返回null
     */
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    /**
     * 检查是否包含特定诊断属性
     *
     * @param key 属性键名
     * @return true表示包含该属性
     */
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    /**
     * 创建一个附加了额外诊断属性的新故障实例
     * <p>用于在异常向上传播的过程中补充更多上下文信息，而不修改原有故障对象（保持不可变性）。</p>
     * <p>该方法应只用于补充<strong>诊断维度</strong>的信息，而不应改变故障的业务语义。</p>
     *
     * @param key   属性键（如 "ruleId"、"phase"）
     * @param value 属性值
     * @return 包含新属性的全新故障实例
     */
    public abstract DescriptiveFault withAttribute(String key, Object value);

    /**
     * 转换为格式化的字符串，包含所有诊断信息
     * <p>用于日志记录和调试输出</p>
     */
    @Override
    public String toString() {
        return String.format("%s [attributes=%s]", super.toString(), attributes);
    }
}
