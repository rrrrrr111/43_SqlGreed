package ru.roman.bim.service.config;

import ru.roman.bim.gui.pane.settings.SettingsViewModel;

/** @author Roman 26.01.13 0:28
 *
 * Работа с файлом конфигурации
 *
 */
public interface ConfigService {

    <T> T loadConfig(String fileName, Class<T> clazz);

    <T> T loadEncryptedConfig(String fileName, Class<T> clazz);

    <T> void saveConfig(T model, String fileName);

    <T> void saveEncryptedConfig(T model, String fileName);

    /**
     * Загрузить натсройки из файла
     *
     * @return
     */
    SettingsViewModel loadSettingsConfig();

    /**
     * Сохранить натсройки в файл
     *
     * @param model
     */
    void saveSettingsConfig(SettingsViewModel model);
}
