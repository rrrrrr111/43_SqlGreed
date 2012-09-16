package ru.roman.greed.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Roman
 * Date: 01.09.12
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static List<String[]> retrieveGettersValues(Object obj, String... excludes) {
        try {
            final List<String[]> result = new ArrayList<String[]>();
            final BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
            final PropertyDescriptor[] properties = bi.getPropertyDescriptors();
            Method method;
            String methodName;
            String name;
            Object val;
            for (PropertyDescriptor descriptor: properties) {
                method = descriptor.getReadMethod();
                name = descriptor.getDisplayName();
                if (method != null && StringUtils.isNotBlank(methodName = method.getName())
                        && !ArrayUtils.contains(excludes, name)) {
                    try {
                        val = MethodUtils.invokeMethod(obj, methodName);
                    } catch (Exception e) {
                        val = "err : " + e.getMessage();
                    }

                    result.add(new String[]{name, val + ""});
                }
            }
            return result;
        } catch (Exception e) {
            throw new AppGreedException("Error wile retrieving " + obj.getClass() + " properties", e);
        }
    }
}
