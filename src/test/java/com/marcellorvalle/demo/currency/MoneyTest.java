package com.marcellorvalle.demo.currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    public void testSum() {
        Money money = Money.from(5.0)
                .add(
                        Money.from(0.5),
                        Money.from(10),
                        Money.from(4.3)
                );

        Assertions.assertEquals(
                Money.from(19.8), money
        );
    }

    @Test
    public void testSumDoubles() {
        Money money = Money.from(5.0)
                .add(0.5, 10.0, 4.299999999);

        Assertions.assertEquals(
                Money.from(19.8), money
        );
    }

    @Test
    public void testSumFloats() {
        Money money = Money.from(5.0f)
                .add(0.50001f, 10.0f, 4.3f);

        Assertions.assertEquals(
                Money.from(19.8f), money
        );
    }

    @Test
    public void testSubtraction() {
        Money money = Money.from(100.0)
                .subtract(
                        Money.from(15.0),
                        Money.from(7.7),
                        Money.from(4.3)
                );

        Assertions.assertEquals(
                Money.from(73), money
        );
    }

    @Test
    public void testSubtractionDouble() {
        Money money = Money.from(100.0)
                .subtract(13.1, 9.7, 0.05);

        Assertions.assertEquals(
                Money.from(77.15), money
        );
    }

    @Test
    public void testSubtractionFloat() {
        Money money = Money.from(100.0f)
                .subtract(23.7f, 5.8f, 0.03f);

        Assertions.assertEquals(
                Money.from(70.47), money
        );
    }

    @Test
    public void testMultiply() {
        Assertions.assertEquals(
                Money.from(200), Money.from(100.0).multiply(2)
        );

        Assertions.assertEquals(
                Money.from(123), Money.from(100.0).multiply(1.23)
        );

        Assertions.assertEquals(
                Money.from(50), Money.from(100.0).multiply(0.5)
        );

        Assertions.assertEquals(
                Money.from(33.33), Money.from(100.0).multiply(0.33333f)
        );

        Assertions.assertEquals(
                Money.from(100), Money.from(100.0).multiply(1.00001f)
        );

        Assertions.assertEquals(
                Money.from(100), Money.from(100.0).multiply(0.99995)
        );

        Assertions.assertEquals(
                Money.from(99.99), Money.from(100.0).multiply(0.999949)
        );
    }

    @Test
    public void testDivide() {
        Assertions.assertEquals(
                Money.from(50), Money.from(100.0).divide(2)
        );

        Assertions.assertEquals(
                Money.from(33.33), Money.from(100.0).divide(3)
        );

        Assertions.assertEquals(
                Money.from(10), Money.from(99.99).divide(10)
        );

        Assertions.assertEquals(
                Money.from(40), Money.from(100.0).divide(2.5)
        );

        Assertions.assertEquals(
                Money.from(40), Money.from(100.0).divide(2.5f)
        );

        Assertions.assertEquals(
                Money.from(1), Money.from(100).divide(100.1)
        );
    }

}
