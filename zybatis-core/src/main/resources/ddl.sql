CREATE TABLE `order_data` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NOT NULL COMMENT '订单号',
  `pin` varchar(50) NOT NULL DEFAULT '' COMMENT '下单账号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='订单基础信息'