package com.github.jsontemplate.templatetests;

import com.github.jsontemplate.JsonTemplate;
import com.jayway.jsonpath.DocumentContext;
import org.junit.jupiter.api.Test;

import static com.github.jsontemplate.templatetests.ParserUtils.parse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class DefaultTypeTest {

    @Test
    void test_DefaultStringTypeAsDefaultType() {
        DocumentContext document = parse(new JsonTemplate("{fieldA, fieldB}"));
        assertThat(document.read("$.fieldA", String.class), is(notNullValue()));
        assertThat(document.read("$.fieldB", String.class), is(notNullValue()));
    }

    @Test
    void test_simpleDefaultType() {
        DocumentContext document = parse(new JsonTemplate("@i{fieldA, fieldB}"));
        assertThat(document.read("$.fieldA", Integer.class), is(notNullValue()));
        assertThat(document.read("$.fieldB", Integer.class), is(notNullValue()));
    }

    @Test
    void test_overwriteDefaultType() {
        DocumentContext document = parse(new JsonTemplate("@i{fieldA, fieldB : @f { fieldC }}"));
        assertThat(document.read("$.fieldA", Integer.class), is(notNullValue()));
        assertThat(document.read("$.fieldB.fieldC", Float.class), is(notNullValue()));
    }

    @Test
    void test_simpleParamerizedDefaultType() {
        DocumentContext document = parse(new JsonTemplate("@s(length=10){fieldA, fieldB: @s(length=20)}"));
        assertThat(document.read("$.fieldA", String.class).length(), is(10));
        assertThat(document.read("$.fieldB", String.class).length(), is(20));
    }

    @Test
    void test_customSimpleTypeAsDefaultType() {
        DocumentContext document = parse(new JsonTemplate("@ls:@s(length=20), @ls{name, role}"));
        assertThat(document.read("$.name", String.class).length(), is(20));
        assertThat(document.read("$.role", String.class).length(), is(20));
    }

    @Test
    void test_customObjectTypeAsDefaultType() {
        DocumentContext document = parse(new JsonTemplate("@address:{city,street,number:@i}, @address{home, office}"));
        assertThat(document.read("$.home.city", String.class), is(notNullValue()));
        assertThat(document.read("$.home.street", String.class), is(notNullValue()));
        assertThat(document.read("$.home.number", Integer.class), is(notNullValue()));
        assertThat(document.read("$.office.city", String.class), is(notNullValue()));
        assertThat(document.read("$.office.street", String.class), is(notNullValue()));
        assertThat(document.read("$.office.number", Integer.class), is(notNullValue()));
    }

    @Test
    void test_customArrayTypeAsDefaultType() {
        DocumentContext document = parse(new JsonTemplate("@names:[](3), @names{group1, group2}"));
        assertThat(document.read("$.group1[0]", String.class), is(notNullValue()));
        assertThat(document.read("$.group1[1]", String.class), is(notNullValue()));
        assertThat(document.read("$.group1[2]", String.class), is(notNullValue()));
        assertThat(document.read("$.group2[0]", String.class), is(notNullValue()));
        assertThat(document.read("$.group2[1]", String.class), is(notNullValue()));
        assertThat(document.read("$.group2[2]", String.class), is(notNullValue()));
    }
}