package com.marcellorvalle.demo.data.specification;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Pojo com os metadados básicos de reflexão de um builder.
 *
 * Seu objetivo é permitir que as informações de metadados sejam carregados uma única vez, evitando múltiplas chamadas
 * a getClass ou getParameterTypes.
 */
class Metadata implements Serializable {
    final private Method method;
    final private Class<?> parameterClass;
    final private Class<?> parameterBaseClass;

    Metadata(Method method) {
        this.method = method;
        //Por definição, os métodos filterBy tem apenas um parâmetro
        parameterClass = method.getParameterTypes()[0];
        parameterBaseClass = parameterIsList() ? getGenericListType() : parameterClass;
    }

    protected Metadata() {
        method = null;
        parameterClass = null;
        parameterBaseClass = null;
    }

    Method getMethod() {
        return method;
    }

    Class<?> getParameterClass() {
        return parameterBaseClass;
    }

    boolean parameterIsList() {
        return parameterClass == List.class;
    }

    boolean isValidFilter() {
        return true;
    }

    private Class<?> getGenericListType() {
        ParameterizedType type = (ParameterizedType) method.getGenericParameterTypes()[0];

        return (Class<?>)type.getActualTypeArguments()[0];
    }
}
