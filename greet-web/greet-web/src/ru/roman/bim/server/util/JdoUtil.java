package ru.roman.bim.server.util;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.util.logging.Logger;


/** @author Roman 30.01.13 23:42 */
public class JdoUtil {
    private static final Logger log = Logger.getLogger(JdoUtil.class.getCanonicalName());
    private static final PersistenceManagerFactory em =
            JDOHelper.getPersistenceManagerFactory("transactions-optional");
//    private static final LocalServiceTestHelper helper =
//            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
//                    .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));


    public static void deletePersistent(final Object obj) {
        execTrans(new PmCall() {
            @Override
            public Object exec(PersistenceManager pm) {
                pm.deletePersistent(obj);
                return null;
            }
        });
    }

    public static <T> T makePersistent(final T obj) {
        return execTrans(new PmCall() {
            @Override
            public Object exec(PersistenceManager pm) {
                return pm.makePersistent(obj);
            }
        });
    }

    public static void getObjectById(final Class clazz, final Long id) {
        final Key k = KeyFactory.createKey(clazz.getSimpleName(), id);
        Object e = exec(new PmCall() {
            @Override
            public Object exec(PersistenceManager pm) {
                return pm.getObjectById(clazz, k);
            }
        });
    }

    public static <T> T createQuery(final Class clazz, final QueryCall call) {
        return exec(new PmCall() {
            @Override
            public Object exec(PersistenceManager pm) {
                javax.jdo.Query query = pm.newQuery(clazz);
                try {
                    final Object resNt = call.exec(pm, query);
                    return resNt;
                } finally {
                    query.closeAll();
                }
            }
        });
    }

    public static <T> T createTransQuery(final Class clazz, final QueryCall call) {
        return execTrans(new PmCall() {
            @Override
            public Object exec(PersistenceManager pm) {
                javax.jdo.Query query = pm.newQuery(clazz);
                try {
                    final Object resTr = call.exec(pm, query);
                    return resTr;
                } finally {
                    query.closeAll();
                }
            }
        });
    }

    public static <T> T exec(PmCall call) {
        final PersistenceManager pm = em.getPersistenceManager();
        try {
            return (T)call.exec(pm);
        } finally {
            pm.close();
        }
    }

    public static <T> T execTrans(final PmCall call) {
        return exec(new PmCall() {
            @Override
            public Object exec(PersistenceManager pm) {
                final javax.jdo.Transaction tx = pm.currentTransaction();
                //tx.
                try {
                    tx.begin();
                    final Object obj = call.exec(pm);
                    tx.commit();
                    return obj;
                } finally {
                    if (tx.isActive()) {
                        tx.rollback();
                    }
                }
            }
        });
    }

    public static interface PmCall {
        Object exec(PersistenceManager pm);
    }

    public static abstract class QueryCall {
        public Object exec(PersistenceManager pm, javax.jdo.Query query){
            return null;
        }
    }


}
