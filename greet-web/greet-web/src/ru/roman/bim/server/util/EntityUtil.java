// Copyright 2011, Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package ru.roman.bim.server.util;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * This is the utility class for all servlets. It provides method for inserting,
 * deleting, searching the entity from data store. Also contains method for
 * displaying the entity in JSON format.
 *
 */
public class EntityUtil {

    private static final Logger log = Logger.getLogger(EntityUtil.class.getCanonicalName());
    private static final DatastoreService storeService = DatastoreServiceFactory.getDatastoreService();



    public static void persistEntity(Entity entity) {
        storeService.put(entity);
    }

    public static void deleteEntity(Key key) {
        storeService.delete(key);
    }

    public static void deleteEntity(final List<Key> keys){
        storeService.delete(new Iterable<Key>() {
            public Iterator<Key> iterator() {
                return keys.iterator();
            }
        });
    }

    public static Entity findEntity(Key key) {
        try {
            return storeService.get(key);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public static Map<Key, Entity> findEntities(List<Key> keys) {
        return storeService.get(keys);
    }

    public static Entity findEntity(String kind, Key key) {
        final Query query = new Query(kind)
            .addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, key);
        final List<Entity> results = storeService.prepare(query).asList(
                FetchOptions.Builder.withDefaults());
        //log.log(Level.INFO, "query : " + query);
        if (!results.isEmpty()) {
            return results.remove(0);
        }
        return null;
    }

    public static Entity findFirstEntity(String kind, String field, Object bean) {
        return findFirstEntityByValue(kind, field, PropUtil.getProperty(bean, field));
    }

    public static Entity findFirstEntityByValue(String kind, String field, Object value) {
        final Query query = new Query(kind);
        query.addFilter(field, FilterOperator.EQUAL, value);
        final List<Entity> results = storeService.prepare(query).asList(
                FetchOptions.Builder.withDefaults());
        if (!results.isEmpty()) {
            return results.remove(0);
        }
        return null;
    }

    public static Iterable<Entity> listEntities(String kind, String searchBy, Object searchFor) {
        final Query q = new Query(kind);
        if (searchFor != null && !"".equals(searchFor)) {
            q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
        }
        final PreparedQuery pq = storeService.prepare(q);
        return pq.asIterable();
    }

    public static Iterable<Entity> listChildren(String kind, Key ancestor) {
        final Query q = new Query(kind);
        q.setAncestor(ancestor);
        q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
        final PreparedQuery pq = storeService.prepare(q);
        return pq.asIterable();
    }

    public static Entity findFirstChild(String kind, Key ancestor, String searchBy, Object searchFor) {
        final Query q = new Query(kind);
        q.setAncestor(ancestor);
        q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
        if (searchFor != null && !"".equals(searchFor)) {
            q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
        }
        final PreparedQuery pq = storeService.prepare(q);
        final Iterator<Entity> iterator = pq.asIterable().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
        final Query q = new Query(kind);
        q.setAncestor(ancestor).setKeysOnly();
        q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
        PreparedQuery pq = storeService.prepare(q);
        return pq.asIterable();
    }

    public static int getCount(String entName) {
        final Query q2 = new Query(entName).setKeysOnly();
        final PreparedQuery pq2 = getDataStore().prepare(q2);
        final List<Entity> res2 = pq2.asList(FetchOptions.Builder.withDefaults());
        return res2.size();
    }

    public static List<Entity> getAll(String entName) {
        final Query q2 = new Query(entName);
        final PreparedQuery pq2 = EntityUtil.getDataStore().prepare(q2);
        final List<Entity> res2 = pq2.asList(FetchOptions.Builder.withDefaults());
        return res2;
    }

    public static List<Key> getAllKeys(String entName) {
        final Query q2 = new Query(entName).setKeysOnly();
        final PreparedQuery pq2 = getDataStore().prepare(q2);
        final List<Entity> entityList = pq2.asList(FetchOptions.Builder.withDefaults());
        final List<Key> keyList = new ArrayList<Key>(entityList.size());
        for (Entity entity : entityList) {
            keyList.add(entity.getKey());
        }
        return keyList;

    }

    public static String writeJSON(Iterable<Entity> entities) {
        final StringBuilder sb = new StringBuilder();

        int i = 0;
        sb.append("{\"data\": [");
        for (Entity result : entities) {
            Map<String, Object> properties = result.getProperties();
            sb.append("{");
            if (result.getKey().getName() == null)
                sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
            else
                sb.append("\"name\" : \"" + result.getKey().getName() + "\",");

            for (String key : properties.keySet()) {
                sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("},");
            i++;
        }
        if (i > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("]}");
        return sb.toString();
    }

    public static String writeJSON(Iterable<Entity> entities, String childKind, String fkName) {
        final StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("{\"data\": [");
        for (Entity result : entities) {
            Map<String, Object> properties = result.getProperties();
            sb.append("{");
            if (result.getKey().getName() == null)
                sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
            else
                sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
            for (String key : properties.keySet()) {
                sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
            }
            Iterable<Entity> child = listEntities(childKind, fkName, String.valueOf(result.getKey().getId()));
            for (Entity en : child) {
                for (String key : en.getProperties().keySet()) {
                    sb.append("\"" + key + "\" : \"" + en.getProperties().get(key)+ "\",");
                }
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("},");
            i++;
        }
        if (i > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("]}");
        return sb.toString();
    }

    public static DatastoreService getDataStore(){
        return storeService;
    }
}