package com.yi.udfs;

import com.facebook.presto.spi.Plugin;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author YI
 * @description
 * @date create in 2021/8/17 9:38
 */
public class PrestoPlugin implements Plugin {
    private static Logger logger = LoggerFactory.getLogger(PrestoPlugin.class);

    /**
     * 加载类
     * @return 返回类对象
     */
    public Set<Class<?>> getFunctions() {
        try {
            List<Class<?>> classes = getFunctionClasses();
            Set<Class<?>> set = Sets.newHashSet();
            for (Class<?> clazz : classes) {
                if (clazz.getName().startsWith("com.yi.udfs.scalar")) {
                    logger.info("加载函数: " + clazz);
                    set.add(clazz);
                }
            }
            return ImmutableSet.<Class<?>>builder().addAll(set).build();
        } catch (IOException e) {
            logger.error("无法从jar文件加载类!", e);
            return ImmutableSet.of();
        }
    }

    /**
     * 通过反射获取类对象
     * @return 类对象
     * @throws IOException
     */
    private List<Class<?>> getFunctionClasses() throws IOException {
        List<Class<?>> classes = Lists.newArrayList();
        String classResource = this.getClass().getName().replace(".", "/") + ".class";
        String jarURLFile = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(classResource)).getFile();
        int jarEnd = jarURLFile.indexOf('!');
        // 这是URL格式，再次转换以获得实际的文件位置
        String jarLocation = jarURLFile.substring(0, jarEnd);
        jarLocation = new URL(jarLocation).getFile();

        ZipInputStream zip = new ZipInputStream(new FileInputStream(jarLocation));
        for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
            if (entry.getName().endsWith(".class") && !entry.isDirectory()) {
                String className = entry.getName().replace("/", ".");
                // 删除.class后缀
                className = className.substring(0, className.length() - 6);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    logger.error("无法加载类{}，异常: {}", className, e);
                }
            }
        }
        return classes;
    }
}
