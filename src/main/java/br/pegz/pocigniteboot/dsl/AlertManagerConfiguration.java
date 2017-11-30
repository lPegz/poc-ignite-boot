package br.pegz.pocigniteboot.dsl;

import br.pegz.pocigniteboot.model.Alert;
import br.pegz.pocigniteboot.model.AlertConfig;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.*;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class AlertManagerConfiguration {

    @Value("${mail.service.url}")
    private String baseUrl;

    @Value("${mail.service.user}")
    private String user;

    @Value("${mail.service.pwd}")
    private String password;

    @Value("${enableFilePersistence}")
    private boolean enableFilePersistence;

    @Value("${ignite.connector.port}")
    private int ignitePort;

    @Value("${ignite.connector.portrange}")
    private String ignitePortRange;

    @Value("${ignite.filepath}")
    private String ignitePersistenceFilePath;

    IgniteConfiguration igniteConfiguration() {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(false);
        // durable file memory persistence

        if (enableFilePersistence) {

            PersistentStoreConfiguration persistentStoreConfiguration = new PersistentStoreConfiguration();

            persistentStoreConfiguration.setPersistentStorePath("./data/store");

            persistentStoreConfiguration.setWalArchivePath("./data/walArchive");

            persistentStoreConfiguration.setWalStorePath("./data/walStore");

            igniteConfiguration.setPersistentStoreConfiguration(persistentStoreConfiguration);

        }

        // connector configuration

        ConnectorConfiguration connectorConfiguration = new ConnectorConfiguration();

        connectorConfiguration.setPort(ignitePort);

        // common ignite configuration

        igniteConfiguration.setMetricsLogFrequency(0);

        igniteConfiguration.setQueryThreadPoolSize(2);

        igniteConfiguration.setDataStreamerThreadPoolSize(1);

        igniteConfiguration.setManagementThreadPoolSize(2);

        igniteConfiguration.setPublicThreadPoolSize(2);

        igniteConfiguration.setSystemThreadPoolSize(2);

        igniteConfiguration.setRebalanceThreadPoolSize(1);

        igniteConfiguration.setAsyncCallbackPoolSize(2);

        igniteConfiguration.setPeerClassLoadingEnabled(false);

        igniteConfiguration.setIgniteInstanceName("alertsGrid");

        BinaryConfiguration binaryConfiguration = new BinaryConfiguration();

        binaryConfiguration.setCompactFooter(false);

        igniteConfiguration.setBinaryConfiguration(binaryConfiguration);

        // cluster tcp configuration

        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder = new TcpDiscoveryVmIpFinder();

        // need to be changed when it come to real cluster

        tcpDiscoveryVmIpFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47509"));

        tcpDiscoverySpi.setIpFinder(tcpDiscoveryVmIpFinder);

        igniteConfiguration.setDiscoverySpi(new TcpDiscoverySpi());

        // cache configuration

        CacheConfiguration alerts = new CacheConfiguration();

        alerts.setCopyOnRead(false);

        // as we have one node for now

        alerts.setBackups(0);

        alerts.setAtomicityMode(CacheAtomicityMode.ATOMIC);

        alerts.setName("Alerts");

        alerts.setIndexedTypes(String.class, Alert.class);

        CacheConfiguration alertsConfig = new CacheConfiguration();

        alertsConfig.setCopyOnRead(false);

        // as we have one node for now

        alertsConfig.setBackups(0);

        alertsConfig.setAtomicityMode(CacheAtomicityMode.ATOMIC);

        alertsConfig.setName("AlertsConfig");

        alertsConfig.setIndexedTypes(String.class, AlertConfig.class);

        igniteConfiguration.setCacheConfiguration(alerts, alertsConfig);
        return igniteConfiguration;
    }
}
