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
package io.nebulalogic.core.model.types;


import java.math.BigDecimal;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.model.types
 * @ClassName FinancialValue.java
 * @Description 类 FinancialValue 的实现描述：金融级数值包装器 : 闭合架构中唯一的“数值标准”
 * 注意: FinancialValue 的具体实现必须在 runtime / internal 中完成，model 只定义接口
 * <p>该接口是引擎内核中唯一的数值标准，旨在解决以下核心问题：</p>
 * <ul>
 *     <li><b>精度陷阱：</b> 屏蔽 BigDecimal.equals() 包含 scale 的副作用，确保 1.0 == 1.00。</li>
 *     <li><b>语义丢失：</b> 通过 Metadata 机制，为数值关联币种、单位等金融属性。</li>
 *     <li><b>计算安全：</b> 预留兼容性检查 (isCompatible)，防止不同币种或单位的数值被非法混合计算。</li>
 * </ul>
 * @Date 2026年01月21日 10:54
 * @Version 1.0.0
 */

public interface FinancialValue extends Comparable<FinancialValue> {

    /**
     * 获取底层的 BigDecimal 数值。
     * <p>注意：该方法主要用于底层数学运算，业务逻辑判定应优先使用 FinancialValue 提供的比较方法。</p>
     *
     * @return 原始 BigDecimal 数值
     */
    BigDecimal decimalValue();

    /**
     * 获取数值关联的元数据。
     * <p>在 V1.0 中，该方法可用于存储币种 (CURRENCY) 或精度策略 (SCALE_POLICY)。</p>
     *
     * @param key 元数据键名
     * @return 元数据值，若不存在则返回 null
     */
    Object getMetadata(String key);

    /**
     * 检查当前数值与另一个数值是否具备计算兼容性。
     * <p>例如：检查两者的币种是否一致。这是防止金融计算事故的第一道防线。</p>
     *
     * @param other 待比较的另一个数值
     * @return true 表示兼容，可以进行加减或比较运算
     */
    default boolean isCompatible(FinancialValue other) {
        // V1.0 默认实现：仅做非空检查。V2.0 将引入币种强校验逻辑。
        return other != null;
    }

    /**
     * 零值语义
     * <p>判断数值是否为零。引导开发者避开直接操作底层 BigDecimal，维持包装器的封闭性。</p>
     *
     * @return true 表示数值为 0
     */
    default boolean isZero() {
        return decimalValue().signum() == 0;
    }

    /**
     * 正数语义
     *
     * @return true 表示数值大于0
     */
    default boolean isPositive() {
        return decimalValue().signum() > 0;
    }

    /**
     * 负数语义
     *
     * @return true 表示数值小于0
     */
    default boolean isNegative() {
        return decimalValue().signum() < 0;
    }

    /**
     * 强制实现金融级比较逻辑。
     * <p>实现类必须确保：比较时忽略 scale 差异（即使用 compareTo 而非 equals）。</p>
     *
     * @param other 待比较对象
     * @return 比较结果 (-1, 0, 1)
     */
    @Override
    int compareTo(FinancialValue other);

    /**
     * 重新定义相等性契约。
     * <p>在金融引擎中，1.0 必须等于 1.00。实现类必须重写此方法以符合 compareTo == 0 的逻辑。</p>
     */
    @Override
    boolean equals(Object obj);

    /**
     * 必须与 equals 保持一致。
     */
    @Override
    int hashCode();
}
