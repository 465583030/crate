/*
 * Licensed to Crate under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.  Crate licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.  You may
 * obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * However, if you have executed another commercial license agreement
 * with Crate these terms will supersede the license and you may use the
 * software solely pursuant to the terms of the relevant commercial
 * agreement.
 */

package io.crate.plugin;


import com.google.common.collect.Lists;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.InternalHDRPercentiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Utility class to load classes dynamically using a serviceLoader.
 * (Classes are discovered using META-INF/services files)
 */
public final class EnterpriseLoader {

    @Nullable
    public static <T> T loadSingle(Class<T> clazz) {
        Iterator<T> it = ServiceLoader.load(clazz).iterator();
        T instance = null;
        while (it.hasNext()) {
            if (instance != null) {
                throw new ServiceConfigurationError(clazz.getSimpleName() + " found twice");
            }
            instance = it.next();
        }
        return instance;
    }

    @Nullable
    public static <T> List<T> loadMultiple(Class<T> clazz) {
        return Lists.newArrayList(ServiceLoader.load(clazz).iterator());
    }
}
