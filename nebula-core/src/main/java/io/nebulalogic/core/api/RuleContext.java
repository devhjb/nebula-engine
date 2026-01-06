/*
 * Copyright 2024-2034 igourd.com All right reserved. This software is the confidential and proprietary information of
 * igourd.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with igourd.com.
 */
package io.nebulalogic.core.api;


import java.util.Set;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.api
 * @ClassName RuleContext.java
 * @Description 类 RuleContext 的实现描述：规则执行上下文接口-所有业务数据都在此上下文中传递
 * @Date 2026年01月07日 01:13
 * @Version 1.0.0
 */

public interface RuleContext {

    /**
     * 放入业务数据
     *
     * @param key
     * @param value
     */
    void put(String key, Object value);

    /**
     * 获取业务数据
     *
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> type);

    /**
     * 获取业务数据（返回Object）
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 检查是否包含key
     *
     * @param key
     * @return
     */
    boolean contains(String key);

    /**
     * 放入元数据
     */
    void putMeta(String key, Object value);

    /**
     * 获取元数据
     *
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    <T> T getMeta(String key, Class<T> type);

    /**
     * 清空上下文
     */
    void clear();

    /**
     * 获取所有数据key
     *
     * @return
     */
    Set<String> keySet();


    /**
     * 上下文ID（用于追踪）
     *
     * @return
     */
    String getId();

    /**
     * 创建时间
     *
     * @return
     */
    long getCreateTime();

}
