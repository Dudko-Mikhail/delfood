package by.dudko.webproject.model.pool;

import by.dudko.webproject.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final int CREATION_ATTEMPTS = 3;
    private static final String DRIVER;
    private static final String URL;
    private static final String USER_NAME;
    private static final String PASSWORD;
    private static final int POOL_SIZE;
    private static final ReentrantLock lock;
    private static final AtomicBoolean isCreated;
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> givenAwayConnections;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        USER_NAME = bundle.getString("db.userName");
        PASSWORD = bundle.getString("db.password");
        DRIVER = bundle.getString("db.driver");
        URL = bundle.getString("db.url");
        if (bundle.containsKey("db.poolSize")) {
            POOL_SIZE = Integer.parseInt(bundle.getString("db.poolSize"));
        }
        else {
            POOL_SIZE = DEFAULT_POOL_SIZE;
        }
        lock = new ReentrantLock();
        isCreated = new AtomicBoolean();
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            }
            finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        givenAwayConnections = new LinkedBlockingDeque<>(POOL_SIZE);

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            ExceptionInInitializerError error = new ExceptionInInitializerError(e);
            logger.error("Cannot register driver", e);
            throw error;
        }

        init();
        logger.info("Connection pool has been successfully initialized");
    }

    private void init() {
        int i = 0;
        while (POOL_SIZE != freeConnections.size() && i < CREATION_ATTEMPTS) {
            createConnections();
            i++;
        }
        if (freeConnections.size() < POOL_SIZE) {
            var error = new ExceptionInInitializerError(
                    String.format("Insufficient number of connections in the pool %d. Required number is %d",
                            freeConnections.size(), POOL_SIZE)
            );
            logger.fatal("Pool initialization error", error);
            throw error;
        }
    }

    private void createConnections() {
        logger.info("Attempt to create pool connections");
        for (int i = freeConnections.size(); i < POOL_SIZE; i++) {
            try {
                freeConnections.add(new ProxyConnection(DriverManager.getConnection(URL, USER_NAME, PASSWORD)));
            } catch (SQLException e) {
                logger.error("Problems creating a connection", e);
            }
        }
    }

    /**
     * Takes connection from the pool
     * @return a taken connection
     */
    public Connection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("Current thread has been interrupted", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Returns the connection to the pool
     * @param connection one of the pool connections
     * @throws PoolException if illegal connection has been detected
     */
    void releaseConnection(ProxyConnection connection) throws PoolException {
        if (!givenAwayConnections.remove(connection)) {
            throw new PoolException("Attempt to add an illegal connection to the pool");
        }
        freeConnections.offer(connection);
    }

    /**
     * Deregister all drivers and closes connections from the pool
     */
    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().finalClose();
            } catch (InterruptedException e) {
                logger.error("Current thread has been interrupted", e);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.warn("Connection closing issues", e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.drivers().forEach(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.warn(String.format("Cannot deregister driver %s", driver), e);
            }
        });
    }
}