package com.usebilbo.vertx.util;

import static org.assertj.core.api.Assertions.assertThat;
import static com.usebilbo.vertx.util.ListUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Test;

public class ListUtilsTest {
    @Test
    public void orEmptyAlwaysReturnsList() throws Exception {
        assertThat(orEmpty(null)).isEmpty();
        
        List<String> source = Arrays.asList("a");
        
        assertThat(orEmpty(null)).isEmpty();        
        assertThat(orEmpty(source)).isSameAs(source);        
    }
    
    @Test
    public void addReturnsListWithElementAdded() throws Exception {
        assertThat(add(null, "a")).containsExactly("a");
        
        List<String> source = new ArrayList<>(Arrays.asList("b"));
        assertThat(add(source, "a")).containsExactly("b", "a");
    }

    @Test
    public void removeReturnsListWithElementRemoved() throws Exception {
        assertThat(remove(null, "a")).isEmpty();
        
        List<String> source = new ArrayList<>(Arrays.asList("a", "b", "c"));
        assertThat(remove(source, "b")).containsExactly("a", "c");
    }
    
    @Test
    public void enumerationIsConvertedToList() throws Exception {
        StringTokenizer st = new StringTokenizer("a b c aa bb cc");
        
        assertThat(toList(st)).containsExactly("a", "b", "c", "aa", "bb", "cc");
    }
}
