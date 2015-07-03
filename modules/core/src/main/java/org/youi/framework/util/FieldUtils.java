/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.youi.framework.util;

/**
 * <p></p>
 * @author 
 * @version 1.0.0
 * @see    
 * @since 
 */

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;


/**
* Offers static methods for directly manipulating fields.
*
* @author Ben Alex
*/
public final class FieldUtils {

    //~ Methods ========================================================================================================
    /**
* Attempts to locate the specified field on the class.
*
* @param clazz the class definition containing the field
* @param fieldName the name of the field to locate
*
* @return the Field (never null)
*
* @throws IllegalStateException if field could not be found
*/
    public static Field getField(Class<?> clazz, String fieldName) throws IllegalStateException {
        Assert.notNull(clazz, "Class required");
        Assert.hasText(fieldName, "Field name required");

        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException nsf) {
            // Try superclass
            if (clazz.getSuperclass() != null) {
                return getField(clazz.getSuperclass(), fieldName);
            }

            throw new IllegalStateException("Could not locate field '" + fieldName + "' on class " + clazz);
        }
    }

    /**
* Returns the value of a (nested) field on a bean. Intended for testing.
* @param bean the object
* @param fieldName the field name, with "." separating nested properties
* @return the value of the nested field
*/
    public static Object getFieldValue(Object bean, String fieldName) throws IllegalAccessException {
        Assert.notNull(bean, "Bean cannot be null");
        Assert.hasText(fieldName, "Field name required");
        String[] nestedFields = StringUtils.tokenizeToStringArray(fieldName, ".");
        Class<?> componentClass = bean.getClass();
        Object value = bean;

        for (String nestedField : nestedFields) {
            Field field = getField(componentClass, nestedField);
            field.setAccessible(true);
            value = field.get(value);
            if (value != null) {
                componentClass = value.getClass();
            }
        }

        return value;

    }

    public static Object getProtectedFieldValue(String protectedField, Object object) {
        Field field = FieldUtils.getField(object.getClass(), protectedField);

        try {
            field.setAccessible(true);

            return field.get(object);
        } catch (Exception ex) {
            ReflectionUtils.handleReflectionException(ex);

            return null; // unreachable - previous line throws exception
        }
    }

    public static void setProtectedFieldValue(String protectedField, Object object, Object newValue) {
        Field field = FieldUtils.getField(object.getClass(), protectedField);

        try {
            field.setAccessible(true);
            field.set(object, newValue);
        } catch (Exception ex) {
            ReflectionUtils.handleReflectionException(ex);
        }
    }
}