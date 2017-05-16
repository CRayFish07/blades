
package com.iusofts.blades.sys.common.util.internal;

import static com.iusofts.blades.sys.common.util.StringUtil.*;
import static java.lang.reflect.Modifier.*;

import java.lang.reflect.Method;

import com.iusofts.blades.sys.common.util.ClassLoaderUtil;
import com.iusofts.blades.sys.common.util.ObjectUtil;
import net.sf.cglib.asm.Type;
import net.sf.cglib.core.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class DynamicClassBuilder {
    private final static int    PUBLIC_STATIC_MODIFIERS = PUBLIC | STATIC;
    protected final      Logger log                     = LoggerFactory.getLogger(getClass());
    private final ClassLoader classLoader;

    public DynamicClassBuilder() {
        this(null);
    }

    public DynamicClassBuilder(ClassLoader cl) {
        this.classLoader = cl;
    }

    public ClassLoader getClassLoader() {
        return classLoader == null ? ClassLoaderUtil.getContextClassLoader() : classLoader;
    }

    protected Signature getSignature(Method method, String rename) {
        String name = ObjectUtil.defaultIfNull(trimToNull(rename), method.getName());
        Type returnType = Type.getType(method.getReturnType());
        Type[] paramTypes = Type.getArgumentTypes(method);

        return new Signature(name, returnType, paramTypes);
    }

    protected boolean isPublicStatic(Method method) {
        return (method.getModifiers() & PUBLIC_STATIC_MODIFIERS) == PUBLIC_STATIC_MODIFIERS;
    }

    protected boolean isEqualsMethod(Method method) {
        if (!"equals".equals(method.getName())) {
            return false;
        }

        Class<?>[] paramTypes = method.getParameterTypes();

        return paramTypes.length == 1 && paramTypes[0] == Object.class;
    }

    protected boolean isHashCodeMethod(Method method) {
        return "hashCode".equals(method.getName()) && method.getParameterTypes().length == 0;
    }

    protected boolean isToStringMethod(Method method) {
        return "toString".equals(method.getName()) && method.getParameterTypes().length == 0;
    }
}
