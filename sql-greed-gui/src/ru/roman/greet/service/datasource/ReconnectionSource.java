package ru.roman.greet.service.datasource;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.ConnectionProxy;
import org.springframework.jdbc.datasource.SmartDataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ClassUtils;
import ru.roman.greet.service.datasource.dto.ReconnectionException;
import ru.roman.greet.service.datasource.dto.ReconnectionInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Roman 15.09.12 12:44
 */
public class ReconnectionSource extends AbstractDataSource implements SmartDataSource, DisposableBean {

    /** real connection */
    private Connection target;
    /** close suppression proxy */
    private Connection connection;
    /** connection info */
    private ReconnectionInfo info;


    public Connection getConnection(ReconnectionInfo info) throws SQLException {
        Validate.notNull(info, "parameter ReconnectionInfo can't be null");
        if (needReconnect(info)) {
            return reconnect(info);
        }
        return connection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (info != null) {
            return getConnection(info);
        }
        throw new ReconnectionException("There is no ReconnectionInfo, call getConnection(ReconnectionInfo)");
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        if (info != null) {
            info.setUsername(username);
            info.setPassword(password);
            return getConnection(info);
        }
        throw new ReconnectionException("There is no ReconnectionInfo, call getConnection(ReconnectionInfo)");
    }


    private synchronized Connection reconnect(ReconnectionInfo info) {
        if (needReconnect(info)) {
            Validate.notBlank(info.getDriverClazz(), "Driver class can't be empty");
            try {
                closeConnection();
                Class.forName(info.getDriverClazz(), true, ClassUtils.getDefaultClassLoader());
                final Connection tmp = DriverManager.getConnection(info.getUrl(), info.getUsername(), info.getPassword());
                if (info.getAutoCommit() != null) {
                    tmp.setAutoCommit(info.getAutoCommit());
                }
                this.target = tmp;
                this.connection = getCloseSuppressingConnectionProxy(target);
                this.info = info;
                logger.info("Established JDBC Connection : " + info.getUrl());
            } catch (Exception e) {
                throw new ReconnectionException(e);
            }
        }
        return connection;
    }

    private boolean needReconnect(ReconnectionInfo info) {
        try {
            return connection == null || connection.isClosed() || !info.equals(this.info);
        } catch (SQLException e) {
            closeConnection();
            return true;
        }
    }

    public synchronized void closeConnection() {
        if (target != null) {
            if (!info.getAutoCommit()) {
                try {
                    target.rollback();
                    logger.info("Connection rollback");
                } catch (SQLException e) {/** */}
            }
            JdbcUtils.closeConnection(target);
            logger.info("Connection closed");
            target = null;
            connection = null;
            info = null;
        }
    }

    @Override
    public void destroy() throws Exception {
        closeConnection();
    }

    @Override
    public synchronized boolean shouldClose(Connection con) {
        return (con != this.connection && con != this.target);
    }

    /**
     * Wrap the given Connection with a proxy that delegates every method call to it
     * but suppresses close calls.
     * @param target the original Connection to wrap
     * @return the wrapped Connection
     */
    private Connection getCloseSuppressingConnectionProxy(Connection target) {
        return (Connection) Proxy.newProxyInstance(
                ConnectionProxy.class.getClassLoader(),
                new Class[]{ConnectionProxy.class},
                new CloseSuppressingInvocationHandler(target));
    }

    /**
     * Invocation handler that suppresses close calls on JDBC Connections.
     */
    private static class CloseSuppressingInvocationHandler implements InvocationHandler {

        private final Connection target;

        public CloseSuppressingInvocationHandler(Connection target) {
            this.target = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("equals")) {
                return (proxy == args[0]);
            } else if (method.getName().equals("hashCode")) {
                return System.identityHashCode(proxy);
            } else if (method.getName().equals("unwrap")) {
                if (((Class) args[0]).isInstance(proxy)) {
                    return proxy;
                }
            } else if (method.getName().equals("isWrapperFor")) {
                if (((Class) args[0]).isInstance(proxy)) {
                    return true;
                }
            } else if (method.getName().equals("close")) {
                return null;
            } else if (method.getName().equals("isClosed")) {
                return false;
            } else if (method.getName().equals("getTargetConnection")) {
                return this.target;
            }
            try {
                return method.invoke(this.target, args);
            } catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }
    }
}


