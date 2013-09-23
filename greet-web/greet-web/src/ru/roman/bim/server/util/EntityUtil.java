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





    /**
     * поиск первой сущности
     *
     * @param kind           искомый тип
     * @param searchBy       имя поля по которому искать тип
     * @param bean           значение брать из бина рефлексионно
     * @return
     */
    public static Entity findFirstEntityByBean(String kind, String searchBy, Object bean) {
        return findFirstEntityByValue(kind, searchBy, PropUtil.getProperty(bean, searchBy));

    }

    /**
     * поиск первой сущности
     *
     * @param kind           искомый тип
     * @param searchBy       имя поля по которому искать тип
     * @param searchFor      значение поля
     * @return
     */
    public static Entity findFirstEntityByValue(String kind, String searchBy, Object searchFor) {
        final Iterator<Entity> iterator = listEntities(kind, searchBy, searchFor).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * поиск сущности
     *
     * @param kind           искомый тип
     * @param searchBy       имя поля по которому искать тип
     * @param searchFor      значение поля
     * @return
     */
    public static Iterable<Entity> listEntities(String kind, String searchBy, Object searchFor) {
        final Query q = new Query(kind);
        if (searchFor != null && !"".equals(searchFor)) {
            q.setFilter(new Query.FilterPredicate(searchBy, FilterOperator.EQUAL, searchFor));
        }
        final PreparedQuery pq = storeService.prepare(q);
        return pq.asIterable();
    }

    /**
     * ищет дочернюю сущность по параметру
     *
     * @param kind       искомый дочерний тип
     * @param ancestor   родительский ключ
     * @param searchBy   имя поля по которому искать дочерний тип
     * @param searchFor  значение поля
     * @return
     */
    public static Entity findFirstChild(String kind, Key ancestor, String searchBy, Object searchFor) {
        final Query q = new Query(kind);
        q.setAncestor(ancestor);
        q.setFilter(new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor));
        if (searchFor != null && !"".equals(searchFor)) {
            q.setFilter(new Query.FilterPredicate(searchBy, FilterOperator.EQUAL, searchFor));
        }
        final PreparedQuery pq = storeService.prepare(q);
        final Iterator<Entity> iterator = pq.asIterable().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * итератор дочерних сущностей
     *
     * @param kind      искомый дочерний тип
     * @param ancestor  родительский ключ
     * @param keysOnly  только ключи
     * @return
     */
    public static Iterable<Entity> getChildrenEntities(String kind, Key ancestor, boolean keysOnly) {
        final Query q = new Query(kind);
        q.setAncestor(ancestor);
        if (keysOnly) {
            q.setKeysOnly();
        }
        q.setFilter(new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor));
        final PreparedQuery pq = storeService.prepare(q);
        return pq.asIterable();
    }

    public static List<Entity> getAllChildrenEntities(String kind, Key ancestor, boolean keysOnly) {
        final Iterator<Entity> iterator = EntityUtil.getChildrenEntities(kind, ancestor, keysOnly).iterator();
        return iteratorToList(iterator);
    }

    /**
     * кол-во сущностей по имени
     *
     * @param entName
     * @return
     */
    public static int getCount(String entName) {
        final Query q2 = new Query(entName).setKeysOnly();
        final PreparedQuery pq2 = getDataStore().prepare(q2);
        return pq2.countEntities(FetchOptions.Builder.withDefaults());
    }

    /**
     * все сущности по имени
     *
     * @param entName
     * @return
     */
    public static List<Entity> getAllEntities(String entName) {
        final Query q2 = new Query(entName);
        final PreparedQuery pq2 = EntityUtil.getDataStore().prepare(q2);
        final List<Entity> res2 = pq2.asList(FetchOptions.Builder.withDefaults());
        return res2;
    }

    /**
     * все ключи по имени сущности
     *
     * @param entName
     * @return
     */
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


    public static List<Entity> iteratorToList(Iterator<Entity> iterator) {
        final List<Entity> res = new ArrayList<Entity>();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            res.add(entity);
        }
        return res;
    }
}