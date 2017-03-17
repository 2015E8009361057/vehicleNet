package com.cit.its.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassCopyUtil{
	static public void classCopy(Object s,Object d){
		Class c = d.getClass();
		Field field[] = c.getDeclaredFields();
		for (Field f : field) {
			Object object = getFeildValue(d, f.getName(),f.getType());
			setFeildValue(d,f.getName(),object);
		}
	}
	static Object getFeildValue(Object owner, String methodName,Class fieldType){
		Class<?> objectClass = owner.getClass();
		methodName = methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
		Method method = null;
		try {
			if (fieldType == boolean.class) {
				method = objectClass.getMethod("is" + methodName);
			} else {
				method = objectClass.getMethod("get" + methodName);
			}
		} catch (Exception e) {
		}
		Object retObject = null;
		try {
			retObject = method.invoke(method);
		} catch (Exception e) {
		}
		return retObject;
	}
	static void setFeildValue(Object owner,String methodName,Object value){
		Class<?> objectClass = owner.getClass();
		methodName = methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
		Method method = null;
		try {
			method = objectClass.getMethod("set" + methodName);
		} catch (Exception e) {
		}
		try {
			method.invoke(method,value);
		} catch (Exception e) {
		}
	}
}
