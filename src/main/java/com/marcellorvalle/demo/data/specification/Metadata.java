package com.marcellorvalle.demo.data.specification;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Pojo com os metadados básicos de reflexão de um builder.
 *
 * Seu objetivo é permitir que as informações de metadados sejam carregados uma única vez, evitando múltiplas chamadas
 * a getClass ou getParameterTypes.
 */
class Metadata {
    final private Method method;
    final private Class<?> parameterClass;

    Metadata(Method method) {
        this.method = method;
        //Por definição, os métodos filterBy tem apenas um parâmetro
        parameterClass = method.getParameterTypes()[0];
    }

    Method getMethod() {
        return method;
    }

    Class<?> getParameterClass() {
        return parameterClass;
    }

    boolean parameterIsList() {
        return parameterClass == List.class;
    }
}
