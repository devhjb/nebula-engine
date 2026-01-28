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
 * @ClassName EngineErrorCode.java
 * @Description 类 EngineErrorCode 的实现描述：引擎错误码枚举 - Nebula-Engine 核心错误码定义
 * @Date 2026年01月28日 12:25
 * @Version 1.0.0
 */
public enum EngineErrorCode implements ErrorCode {
    /* --- Context 相关 --- */
    CONTEXT_FROZEN("E-C-001", "Context is frozen and cannot be modified"),
    MUTATOR_NOT_ALLOWED("E-C-003", "Write access is not allowed in this phase"),

    /* --- 数据校验相关 --- */
    TYPE_MISMATCH("E-D-001", "Data type mismatch"),
    VALUE_OUT_OF_RANGE("E-D-002", "Value is out of range"),

    /* --- 逻辑控制相关 --- */
    PHASE_ILLEGAL("E-L-001", "Illegal execution phase");

    private final String code;

    private final String message;

    EngineErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
