/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.android;

import org.jboss.aerogear.android.datamanager.OnStoreCreatedListener;
import org.jboss.aerogear.android.datamanager.Store;
import org.jboss.aerogear.android.impl.datamanager.*;

import java.util.HashMap;
import java.util.Map;

public final class DataManager {

    private static Map<String, Store<?>> stores = new HashMap<String, Store<?>>();

    private static Map<Class<? extends StoreConfig<?>>, ConfigurationProvider<?>>
            configurationProviderMap = new HashMap<Class<? extends StoreConfig<?>>, ConfigurationProvider<?>>();

    private static OnStoreCreatedListener onStoreCreatedListener = new OnStoreCreatedListener() {
        @Override
        public void onStoreCreated(StoreConfig<?> configuration, Store<?> store) {
            stores.put(configuration.getName(), store);
        }
    };

    static {
        DataManager.registerConfigurationProvider(MemoryStoreConfig.class, new MemoryStoreConfigurationProvider());
        DataManager.registerConfigurationProvider(SQLStoreConfig.class, new SQLStoreConfigurationProvider());
        DataManager.registerConfigurationProvider(EncryptedMemoryStoreConfig.class, new EncryptedMemoryStoreConfigurationProvider());
        DataManager.registerConfigurationProvider(EncryptedSQLStoreConfig.class, new EncryptedSQLStoreConfigurationProvider());
    }

    private DataManager() {
    }

    public static <CFG extends StoreConfig<CFG>> void registerConfigurationProvider
            (Class<CFG> configurationClass, ConfigurationProvider<CFG> provider) {
        configurationProviderMap.put(configurationClass, provider);
    }

    public static <CFG extends StoreConfig<CFG>> CFG config(String name, Class<CFG> storeImplementationClass) {

        @SuppressWarnings("unchecked")
        ConfigurationProvider<? extends StoreConfig<CFG>> provider =
                (ConfigurationProvider<? extends StoreConfig<CFG>>)
                        configurationProviderMap.get(storeImplementationClass);

        if (provider == null) {
            throw new IllegalArgumentException("Configuration not registered!");
        }

        return provider.newConfiguration()
                .setName(name)
                .addOnStoreCreatedListener(onStoreCreatedListener);

    }

    public static Store getStore(String name) {
        return stores.get(name);
    }

}
