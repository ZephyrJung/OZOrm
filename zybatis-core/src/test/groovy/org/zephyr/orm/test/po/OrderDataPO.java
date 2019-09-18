package org.zephyr.orm.test.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 */
@Data
public class OrderDataPO implements Serializable {
    /**
     * 订单号
     */
    private Long orderId;
    /**
     * 下单账号
     */
    private String pin;
}