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
    private static DatastoreService storeService = DatastoreServiceFactory.getDatastoreService();


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

    public static Entity findEntity(String kind, Key key) {
        Query query = new Query(kind)
            .addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, key);
        List<Entity> results = storeService.prepare(query).asList(
                FetchOptions.Builder.withDefaults());
        //log.log(Level.INFO, "query : " + query);
        if (!results.isEmpty()) {
            return results.remove(0);
        }
        return null;
    }

    public static Entity findFirstEntity(String kind, String field, Object key) {
        Query query = new Query(kind);
        query.addFilter(field, FilterOperator.EQUAL, key);
        List<Entity> results = storeService.prepare(query).asList(
                FetchOptions.Builder.withDefaults());
        if (!results.isEmpty()) {
            return results.remove(0);
        }
        return null;
    }

    public static Iterable<Entity> listEntities(String kind, String searchBy, String searchFor) {
        Query q = new Query(kind);
        if (searchFor != null && !"".equals(searchFor)) {
            q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
        }
        PreparedQuery pq = storeService.prepare(q);
        return pq.asIterable();
    }

    public static Iterable<Entity> listChildren(String kind, Key ancestor) {
        Query q = new Query(kind);
        q.setAncestor(ancestor);
        q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
        PreparedQuery pq = storeService.prepare(q);
        return pq.asIterable();
    }

    public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
        Query q = new Query(kind);
        q.setAncestor(ancestor).setKeysOnly();
        q.addFilter(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor);
        PreparedQuery pq = storeService.prepare(q);
        return pq.asIterable();
    }

    public static String writeJSON(Iterable<Entity> entities) {
        StringBuilder sb = new StringBuilder();

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
        StringBuilder sb = new StringBuilder();
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

    public static int getCount(String entName) {
        Query q2 = new Query(entName);
        q2.setKeysOnly();
        PreparedQuery pq2 = EntityUtil.getDataStore().prepare(q2);
        List<Entity> res2 = pq2.asList(FetchOptions.Builder.withDefaults());
        return res2.size();
    }
}