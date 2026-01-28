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


/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.exception
 * @ClassName ErrorCode.java
 * @Description 类 ErrorCode 的实现描述：错误码接口 - 定义机器可识别的错误编码契约
 * @Date 2026年01月28日 12:18
 * @Version 1.0.0
 */

public interface ErrorCode {

    /**
     * 获取稳定且唯一的错误编码
     * <p>编码应该遵循项目规范，具有良好的可读性和稳定性</p>
     *
     * @return 错误码字符串，如"E-C-001"
     */
    String code();

    /**
     * 默认错误描述（不一定给最终用户）
     * <p>主要用于开发和调试，不一定直接展示给最终用户</p>
     *
     * @return 描述信息
     */
    String message();
}
