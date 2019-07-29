package com.marcellorvalle.demo.SpecificationTools;

import org.junit.jupiter.api.Test;

import java.lang.invoke.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaMetaFactoryTests {
    @Test
    public void testFunctionWithParameter() throws Throwable {
        SimpleBean simpleBean = new SimpleBean();

        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType invokedType = MethodType.methodType(BiFunction.class);
        MethodType getter = MethodType.methodType(String.class, String.class);
        MethodHandle target = caller.findVirtual(SimpleBean.class, "simpleFunction", getter);
        MethodType func = target.type();


        CallSite site = LambdaMetafactory.metafactory(
                caller,
                "apply",
                invokedType,
                func.generic(),
                target,
                MethodType.methodType(String.class, SimpleBean.class, String.class)
        );

        BiFunction<SimpleBean, String, String> getterFunction = (BiFunction<SimpleBean, String, String>) site.getTarget().invokeExact();

        System.out.println(getterFunction.apply(simpleBean, "FOO"));
    }

    private class SimpleBean {
        public String simpleFunction(String in) {
            return "The parameter was " + in;
        }
    }
}
