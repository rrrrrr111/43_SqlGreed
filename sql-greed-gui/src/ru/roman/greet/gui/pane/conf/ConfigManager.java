package ru.roman.greet.gui.pane.conf;

import ru.roman.greet.service.ServiceHolder;
import ru.roman.greet.service.config.ConfigService;
import ru.roman.greet.service.config.dto.CommandsModel;
import ru.roman.greet.service.config.dto.ConnConfigModel;
import ru.roman.greet.service.config.reader.CommandsReader;
import ru.roman.greet.service.config.reader.ConnectionConfigReader;
import ru.roman.greet.service.config.reader.XmlReader;
import ru.roman.greet.service.config.writer.CommandsWriter;
import ru.roman.greet.service.config.writer.ConnectionConfigWriter;
import ru.roman.greet.service.config.writer.XmlWriter;
import ru.roman.greet.service.datasource.dto.ReconnectionInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ConfigManager {

    private static final String CONN_CONFIG_FILENAME = "config.conn";
    private static final String COMMANDS_CONFIG_FILENAME = "commands.xml";
    private static final XmlWriter<ConnConfigModel> CONN_WRITER = new ConnectionConfigWriter();
    private static final XmlReader<ConnConfigModel> CONN_READER = new ConnectionConfigReader();
    private static final XmlWriter<CommandsModel> COMMANDS_WRITER = new CommandsWriter();
    private static final XmlReader<CommandsModel> COMMANDS_READER = new CommandsReader();

    private static ConfigManager instance;
    private ConfigService configService = ServiceHolder.getConfigService();
    private ConnConfigModel connectionsModel;
    private CommandsModel commandsModel;

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private ConfigManager() {
    }


    public void loadConnections() {
        if (!new File(getConnConfigFilename()).exists()) {
            final List<ReconnectionInfo> configs = getDefaultConnConfig();
            ConnConfigModel model = new ConnConfigModel(configs, configs.get(0));
            configService.saveEncryptedConfig(model, CONN_CONFIG_FILENAME, CONN_WRITER);
        }
        connectionsModel = configService.loadEncryptedConfig(CONN_CONFIG_FILENAME, CONN_READER);
    }


    public void loadCommands() {
        if (!new File(getCommandsConfigFilename()).exists()) {
            CommandsModel model = new CommandsModel();
            model.setCommandsCount("50");
            model.setCommandList(Collections.EMPTY_LIST);
            configService.saveConfig(model, COMMANDS_CONFIG_FILENAME, COMMANDS_WRITER);
        }
        commandsModel = configService.loadConfig(COMMANDS_CONFIG_FILENAME, COMMANDS_READER);
    }


    private List<ReconnectionInfo> getDefaultConnConfig() {
        final List<ReconnectionInfo> configs = new ArrayList<ReconnectionInfo>();
        configs.add(new ReconnectionInfo("Derby", "jdbc:derby://localhost:1527/sample;create=true", "org.apache.derby.jdbc.ClientDriver", "app", "app", true));
        configs.add(new ReconnectionInfo("MS SQL", "jdbc:sqlserver://v_mhqsa016;databaseName=BSS_TREN", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "dba", "sql", true));
        configs.add(new ReconnectionInfo("ODBC", "jdbc:odbc:BSS_TEST3", "sun.jdbc.odbc.JdbcOdbcDriver", "dba", "sql", true));
        configs.add(new ReconnectionInfo("JBase", "jdbc:jbasejo:thin:@mhqsa063:3570:BNK", "com.jbase.jdbc.driver.JBaseJDBCDriver", "r6test1", "123456", true));
        configs.add(new ReconnectionInfo("Oracle", "jdbc:oracle:thin:@bank111:1521:bank111", "oracle.jdbc.OracleDriver", "gc", "gc", true));
        return configs;
    }

    public ConnConfigModel getConnectionsModel() {
        return connectionsModel;
    }

    public CommandsModel getCommandsModel() {
        return commandsModel;
    }

    public void saveConnections() {
        configService.saveEncryptedConfig(connectionsModel, CONN_CONFIG_FILENAME, CONN_WRITER);
    }

    public ReconnectionInfo getDefaultConnection() {
        return connectionsModel.getDefaultConnection();
    }

    public String getConnConfigFilename() {
        return configService.prepareConfig(CONN_CONFIG_FILENAME);
    }

    public String getCommandsConfigFilename() {
        return configService.prepareConfig(COMMANDS_CONFIG_FILENAME);
    }

}
