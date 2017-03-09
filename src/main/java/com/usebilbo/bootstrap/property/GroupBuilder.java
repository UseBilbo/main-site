package com.usebilbo.bootstrap.property;

import java.util.List;
import java.util.Map;

public interface GroupBuilder {
    Map<String, Map<String, List<String>>> buildGroups(Map<String, List<String>> properties);
}
