package com.github.jsontemplate.jsonbuild;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class JsonStringNodeTest {

    @ParameterizedTest
    @ValueSource(strings = {"abcde", "", " ", "!@#$%^&*()+-=\\/\n\t"})
    void testOf(String value) {
        JsonStringNode node = JsonStringNode.of(value);
        String printedValue = node.print();

        assertThat(printedValue, is("\"" + value + "\""));
    }
}