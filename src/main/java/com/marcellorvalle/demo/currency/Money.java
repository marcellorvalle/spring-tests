package com.marcellorvalle.demo.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

public class Money {
    public static final Money ZERO = Money.from(0.0);

    private static final int PRECISION = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_EVEN;

    private final BigDecimal amount;

    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money from(float value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money from(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money from(String value) {
        if (Objects.isNull(value) || "".equals(value)) {
            return Money.ZERO;
        }

        return new Money(new BigDecimal(value));
    }

    public static Money addAll(Money... money) {
        return ZERO.add(money);
    }

    public Money add(Float... values) {
        return Arrays.stream(values)
                .map(Money::from)
                .reduce(this, Money::add);
    }

    public Money add(Double... values) {
        return Arrays.stream(values)
                .map(Money::from)
                .reduce(this, Money::add);
    }

    public Money add(Money... money) {
        return Arrays.stream(money)
                .reduce(this, Money::add);
    }

    public Money add(Money other) {
        return new Money(amount.add(other.amount));
    }

    public Money subtract(Float... values) {
        return Arrays.stream(values)
                .map(Money::from)
                .reduce(this, Money::subtract);
    }

    public Money subtract(Double... values) {
        return Arrays.stream(values)
                .map(Money::from)
                .reduce(this, Money::subtract);
    }

    public Money subtract(Money... money) {
        return Arrays.stream(money)
                .reduce(this, Money::subtract);
    }

    public Money subtract(Money other) {
        return new Money(amount.subtract(other.amount));
    }

    public Money percentile(double percentage) {
        return multiply(percentage).divide(100);
    }

    public Money multiply(float value) {
        return new Money(amount.multiply(BigDecimal.valueOf(value)));
    }

    public Money multiply(double value) {
        return new Money(amount.multiply(BigDecimal.valueOf(value)));
    }

    public Money divide(float value) {
        return new Money(amount.divide(BigDecimal.valueOf(value), PRECISION, ROUNDING));
    }

    public Money divide(double value) {
        return new Money(amount.divide(BigDecimal.valueOf(value), PRECISION, ROUNDING));
    }

    public Money abs() {
        return isNegative() ? invert() : this;
    }

    public Money invert() {
        return multiply(-1);
    }

    public boolean isPositive() {
        return gt(ZERO);
    }

    public boolean isNegative() {
        return lt(ZERO);
    }

    public boolean isZero() {
        return eq(ZERO);
    }

    public boolean gt(Money other) {
        return compareTo(other) > 0;
    }

    public boolean ge(Money other) {
        return compareTo(other) >= 0;
    }

    public boolean lt(Money other) {
        return compareTo(other) < 0;
    }

    public boolean le(Money other) {
        return compareTo(other) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != Money.class) {
            return false;
        }

        return eq((Money) other);
    }

    public boolean eq(Money other) {
        return compareTo(other) == 0;
    }

    public int compareTo(Money other) {
        return getAmountWithPrecision().compareTo(other.getAmountWithPrecision());
    }

    public long cents() {
        return (long) multiply(100).toDouble();
    }

    @Override
    public String toString() {
        return toBigDecimal().toString();
    }

    public double toDouble() {
        return toBigDecimal().doubleValue();
    }

    public BigDecimal toBigDecimal() {
        return getAmountWithPrecision();
    }

    private BigDecimal getAmountWithPrecision() {
        return amount.setScale(PRECISION, ROUNDING);
    }
}
