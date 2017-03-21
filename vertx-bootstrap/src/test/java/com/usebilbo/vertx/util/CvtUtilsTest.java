package com.usebilbo.vertx.util;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import static com.usebilbo.vertx.util.CvtUtils.*;

import org.junit.Test;

public class CvtUtilsTest {

    @Test
    public void intToHexIsGeneratedProperly() throws Exception {
        assertThat(toHex(0x01020304)).isEqualTo("01020304");
        assertThat(toHex(0x10203040)).isEqualTo("10203040");
    }

    @Test
    public void longToHexIsGeneratedProperly() throws Exception {
        assertThat(toHex(0x0102030405060708L)).isEqualTo("0102030405060708");
        assertThat(toHex(0x1020304050607080L)).isEqualTo("1020304050607080");
    }

    @Test
    public void byteArrayToHexIsGeneratedProperly() throws Exception {
        assertThat(toHex(null, new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07})).isEqualTo("01020304050607");
        assertThat(toHex("prefix", new byte[] {0x10, 0x20, 0x30, 0x40, 0x50, 0x60, 0x70})).isEqualTo("prefix10203040506070");
    }

    @Test
    public void abbreviatedToLongWorksProperly() throws Exception {
        assertThat(abbreviatedToLong("abc")).isEqualTo(Optional.empty());
        assertThat(abbreviatedToLong("123")).isEqualTo(Optional.of(123L));
        assertThat(abbreviatedToLong("1K")).isEqualTo(Optional.of(1024L));
        assertThat(abbreviatedToLong("15M")).isEqualTo(Optional.of(15L * 1024 * 1024));
        assertThat(abbreviatedToLong("3G")).isEqualTo(Optional.of(3L * 1024 * 1024 * 1024));
        assertThat(abbreviatedToLong("2T")).isEqualTo(Optional.of(2L * 1024 * 1024 * 1024 * 1024));
    }

    @Test
    public void abbreviatedTimeToLongWorksProperly() throws Exception {
        assertThat(abbreviatedTimeToLong("")).isEqualTo(Optional.empty());
        assertThat(abbreviatedTimeToLong("abc")).isEqualTo(Optional.empty());
        assertThat(abbreviatedTimeToLong("3ms")).isEqualTo(Optional.of(3L));
        assertThat(abbreviatedTimeToLong("3Ms")).isEqualTo(Optional.of(3L));
        assertThat(abbreviatedTimeToLong("3mS")).isEqualTo(Optional.of(3L));
        assertThat(abbreviatedTimeToLong("3MS")).isEqualTo(Optional.of(3L));
        assertThat(abbreviatedTimeToLong("12s")).isEqualTo(Optional.of(12*1000L));
        assertThat(abbreviatedTimeToLong("12S")).isEqualTo(Optional.of(12*1000L));
        assertThat(abbreviatedTimeToLong("4m")).isEqualTo(Optional.of(4*60*1000L));
        assertThat(abbreviatedTimeToLong("4M")).isEqualTo(Optional.of(4*60*1000L));
        assertThat(abbreviatedTimeToLong("3h")).isEqualTo(Optional.of(3*60*60*1000L));
        assertThat(abbreviatedTimeToLong("3H")).isEqualTo(Optional.of(3*60*60*1000L));
        assertThat(abbreviatedTimeToLong("5d")).isEqualTo(Optional.of(5*24*60*60*1000L));
        assertThat(abbreviatedTimeToLong("5D")).isEqualTo(Optional.of(5*24*60*60*1000L));
        assertThat(abbreviatedTimeToLong("-1")).isEqualTo(Optional.of(-1L));
        assertThat(abbreviatedTimeToLong("-1000")).isEqualTo(Optional.of(-1000L));
        assertThat(abbreviatedTimeToLong("-5d")).isEqualTo(Optional.of(-(5*24*60*60*1000L)));
    }

    @Test
    public void toBoolWorksProperly() throws Exception {
        assertThat(toBool("Yes", false)).isTrue();
        assertThat(toBool("oN", false)).isTrue();
        assertThat(toBool("TRUE", false)).isTrue();
        assertThat(toBool("no", false)).isFalse();
        assertThat(toBool(null, false)).isFalse();
        assertThat(toBool("false", false)).isFalse();
    }
}
