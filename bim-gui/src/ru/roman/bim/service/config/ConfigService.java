package ru.roman.bim.service.config;

/** @author Roman 26.01.13 0:28 */
public interface ConfigService {

    public <T> T loadConfig(String fileName, Class<T> clazz);

    public <T> T loadEncryptedConfig(String fileName, Class<T> clazz);

    public <T> void saveConfig(T model, String fileName);

    public <T> void saveEncryptedConfig(T model, String fileName);
}
