package org.zephyr.orm.util;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.zephyr.orm.model.Configuration;

import java.io.InputStream;

/**
 * @author yu.zhang
 * @date 2019-09-06
 * config文件名称应当为固定的zybatis，可在zybatis后面添加后缀作为环境切换标记（涉及与spring的整合）
 */
public class PropertyUtils {
    private static final String DEFAULT_CONFIG_NAME = "zybatis.yml";

    public static Configuration buildConfig() {
        return buildConfig(null);
    }

    public static Configuration buildConfig(String path) {
        Yaml yaml = new Yaml(new Constructor(Configuration.class));
        InputStream inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream(StringUtils.isNotBlank(path) ? path : DEFAULT_CONFIG_NAME);
        return yaml.load(inputStream);
    }
}
