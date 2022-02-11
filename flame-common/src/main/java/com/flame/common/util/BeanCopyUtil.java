package com.flame.common.util;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

/**
 * @author king
 * @date 2022年02月11日 10:33
 * @DOTO:bean复制工具,使用 cglibs的效率更高一点
 */
public class BeanCopyUtil {

    public static void copy(Object source, Object target, boolean useConverter, Converter converter) {
        BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), useConverter);
        beanCopier.copy(source, target,converter);
    }

    public static void copy(Object source,Object target) {
        copy(source, target,false,null);
    }

}
