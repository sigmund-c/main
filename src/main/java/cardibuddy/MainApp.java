package cardibuddy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import cardibuddy.commons.core.Config;
import cardibuddy.commons.core.LogsCenter;
import cardibuddy.commons.core.Version;
import cardibuddy.commons.exceptions.DataConversionException;
import cardibuddy.commons.util.ConfigUtil;
import cardibuddy.commons.util.StringUtil;
import cardibuddy.logic.Logic;
import cardibuddy.logic.LogicManager;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.ReadOnlyUserPrefs;
import cardibuddy.model.UserPrefs;
import cardibuddy.model.util.SampleDataUtil;
import cardibuddy.storage.CardiBuddyStorage;
import cardibuddy.storage.JsonCardiBuddyStorage;
import cardibuddy.storage.JsonUserPrefsStorage;
import cardibuddy.storage.Storage;
import cardibuddy.storage.StorageManager;
import cardibuddy.storage.UserPrefsStorage;
import cardibuddy.ui.Ui;
import cardibuddy.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected LogicToUiManager logicToUiManager;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing CardiBuddy ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        CardiBuddyStorage cardiBuddyStorage = new JsonCardiBuddyStorage(userPrefs.getCardiBuddyFilePath());
        storage = new StorageManager(cardiBuddyStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);

        logicToUiManager = new LogicToUiManager((UiManager) ui);

        logic.setLogicToUiManager(logicToUiManager);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s cardibuddy book and {@code userPrefs}. <br>
     * The data from the sample cardibuddy book will be used instead if {@code storage}'s cardibuddy book is not found,
     * or an empty cardibuddy book will be used instead if errors occur when reading {@code storage}'s cardibuddy book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyCardiBuddy> cardiBuddyOptional;
        ReadOnlyCardiBuddy initialData;
        try {
            cardiBuddyOptional = storage.readCardiBuddy();
            if (!cardiBuddyOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample CardiBuddy");
            }
            initialData = cardiBuddyOptional.orElseGet(SampleDataUtil::getSampleCardiBuddy);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty CardiBuddy");
            initialData = new CardiBuddy();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty CardiBuddy");
            initialData = new CardiBuddy();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty CardiBuddy");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting CardiBuddy " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Cardi Buddy ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
