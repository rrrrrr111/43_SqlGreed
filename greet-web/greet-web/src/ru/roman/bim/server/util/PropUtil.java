package ru.roman.bim.server.util;

import com.google.appengine.api.datastore.Entity;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/** @author Roman 02.02.13 4:55 */
public class PropUtil {
    private static final Logger log = Logger.getLogger(PropUtil.class.getCanonicalName());
    private static final PropertyUtilsBean pub = new PropertyUtilsBean();
    private static final BeanUtilsBean bub = BeanUtilsBean.getInstance();

    public static Map<String, Object> describe(Object obj) {
        try {
            Map<String, Object> props = bub.describe(obj);
            props.remove("class");
            props.remove("id");
            log.info("bean properties described : " + props);
            return props;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean equalsProperty(Entity ent, Object model, String prop) {
        return ObjectUtils.equals(getProperty(model, prop), ent.getProperty(prop));
    }

    public static Object getProperty(Object bean, String name) {
        try {
            return pub.getProperty(bean, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setEntProperty(Entity ent, String name, Object model) {
        ent.setProperty(name, getProperty(model, name));
    }

    public static void copyEntProperty(Entity dest, Entity src, String name) {
        dest.setProperty(name, src.getProperty(name));
    }

    public static void setBeanProperty(Object bean, String name, Object value) {
        try {
            //log.info("bean: " + bean + ", name: " + name + ", value: " + value
            //        + (value != null ? ", valueClass: " + value.getClass() : ""));
            pub.setProperty(bean, name, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean setBeanPropertyIfNull(Object model, String name, Object value) {
        if (getProperty(model, name) == null) {
            setBeanProperty(model, name, value);
            return true;
        }
        return false;
    }

    public static boolean setEntPropertyIfNull(Entity ent, String name, Object value) {
        Object entVal = ent.getProperty(name);
        if (entVal == null) {
            ent.setProperty(name, value);
            return true;
        }
        return false;
    }

    public static void copyEntProperties(Entity entity, Object model, List<String> exclude) {
        Map<String, Object> props = describe(model);
        if (exclude != null) {
            for (String excl : exclude) {
                props.remove(excl);
            }
        }
        for (Map.Entry<String, Object> entry : props.entrySet()) {
            entity.setProperty(entry.getKey(), getProperty(model, entry.getKey()));
        }
    }

    public static void setAllProperties(Object model, Entity entity) {
        setAllProperties(model, entity, Collections.EMPTY_LIST);
    }

    public static void setAllProperties(Object model, Entity entity, List<String> exclude) {
        Map<String, Object> props = new HashMap<String, Object>(entity.getProperties());
        for (String excl : exclude) {
            props.remove(excl);
        }
        for (Map.Entry<String, Object> entry : props.entrySet()) {
            setBeanProperty(model, entry.getKey(), entry.getValue());
        }
    }

    public static void copyModelProperties(Object dest, Object src) {
        try {
            pub.copyProperties(dest, src);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getEntProperty(Entity ent, String name) {
        return (T) ent.getProperty(name);
    }


}
