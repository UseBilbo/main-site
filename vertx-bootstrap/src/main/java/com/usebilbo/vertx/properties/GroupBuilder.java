package com.usebilbo.vertx.properties;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.usebilbo.vertx.properties.impl.GroupBuilderImpl;

@ImplementedBy(GroupBuilderImpl.class)
public interface GroupBuilder {
    Map<String, Map<String, List<String>>> buildGroups(Map<String, List<String>> properties);
}
