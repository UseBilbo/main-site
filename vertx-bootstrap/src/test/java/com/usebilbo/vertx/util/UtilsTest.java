package com.usebilbo.vertx.util;

import static com.usebilbo.vertx.util.Utils.coalesce;
import static com.usebilbo.vertx.util.Utils.ifNotNull;
import static com.usebilbo.vertx.util.Utils.merge;
import static com.usebilbo.vertx.util.Utils.parentPackage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.usebilbo.vertx.Launcher;

public class UtilsTest {
    @Test
    public void mergeWorksProperly() throws Exception {
        assertThat(merge(null, "test")).containsExactly("test");
        assertThat(merge(null, "test", "test1")).containsExactly("test", "test1");
    }
    
    @Test
    public void parentPackageIsCalculatedProperly() throws Exception {
        assertThat(parentPackage(null)).isNull();
        assertThat(parentPackage("")).isEqualTo("");
        assertThat(parentPackage("a")).isEqualTo("a");
        assertThat(parentPackage(Launcher.class.getPackage().getName())).isEqualTo("com.usebilbo");
    }
    
    @Test
    public void coalesceSelectsFirstNotNullParameter() throws Exception {
        assertThat(coalesce((Object)null, null, null)).isNull();
        assertThat(coalesce((Object)null, null, "third")).isEqualTo("third");
        assertThat(coalesce((Object)null, "second", null)).isEqualTo("second");
        assertThat(coalesce((Object)null, "second", "third")).isEqualTo("second");
        assertThat(coalesce("first", "second", "third")).isEqualTo("first");        
        assertThat(coalesce("first", "second", null)).isEqualTo("first");        
        assertThat(coalesce("first", null, "third")).isEqualTo("first");        
        assertThat(coalesce("first", null, null)).isEqualTo("first");        
    }
    
    @Test
    public void ifNotNullEvaluatesExpressionAndReturnItsResultOnlyWhenFirstArgIsNotNull() throws Exception {
        assertThat(ifNotNull("", () -> "a")).isEqualTo("a");
        assertThat(ifNotNull(null, () -> "a")).isNull();
    }

    @Test
    public void ifNotNullReturnsResultOfFirstExpressionIfFirstArgIsNotNullAndResultsOfSecondExpressionOtherwise() throws Exception {
        assertThat(ifNotNull("", () -> "a", () -> "b")).isEqualTo("a");
        assertThat(ifNotNull(null, () -> "a", () -> "b")).isEqualTo("b");
    }
}
