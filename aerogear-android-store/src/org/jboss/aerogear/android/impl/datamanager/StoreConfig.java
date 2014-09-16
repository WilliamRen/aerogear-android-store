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
package org.jboss.aerogear.android.impl.datamanager;

import org.jboss.aerogear.android.Config;
import org.jboss.aerogear.android.datamanager.OnStoreCreatedListener;

import java.util.Collection;
import java.util.HashSet;

public abstract class StoreConfig<CFG extends StoreConfig<CFG>> implements Config<CFG> {

    private String name;
    private Collection<OnStoreCreatedListener> listeners;

    public StoreConfig() {
        listeners = new HashSet<OnStoreCreatedListener>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CFG setName(String name) {
        this.name = name;
        return (CFG) this;
    }

    public Collection<OnStoreCreatedListener> getOnStoreCreatedListeners() {
        return listeners;
    }

    public CFG addOnStoreCreatedListener(OnStoreCreatedListener listener) {
        this.listeners.add(listener);
        return (CFG) this;
    }

    public CFG setOnStoreCreatedListeners(Collection<OnStoreCreatedListener> listeners) {
        listeners.addAll(listeners);
        return (CFG) this;
    }

}
