package com.usebilbo.vertx.util;

import java.util.function.Predicate;

public interface Predicates {
    Predicate<String> TRUE = (str) -> true;
    Predicate<String> FALSE = (str) -> false;
}
