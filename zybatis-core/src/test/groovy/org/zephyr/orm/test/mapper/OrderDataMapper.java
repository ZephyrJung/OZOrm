package org.zephyr.orm.test.mapper;


import org.zephyr.orm.annotation.Insert;
import org.zephyr.orm.annotation.Mapper;
import org.zephyr.orm.annotation.Param;
import org.zephyr.orm.annotation.Select;
import org.zephyr.orm.test.po.OrderDataPO;

@Mapper
public interface OrderDataMapper {
    @Insert("insert into order_data(order_id,pin) values(?,?)")
    void insert(OrderDataPO record);

    @Select("select * from order_data where order_id=?")
    OrderDataPO selectByOrderId(@Param("orderId") long orderId);
}