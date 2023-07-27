package im.limbo.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import im.limbo.Main;

public class ConfigManager{
	private FileConfiguration customConfig;
	private File customConfigFile;
	private String name;
	private final Main main;
	
	public ConfigManager(String name) {
		main = Main.getIntance();
		this.name = name;
		createConfig();
	}
	
	public FileConfiguration getConfig() {
		if(customConfig == null)
			reloadConfig();
		return this.customConfig;
	}
	
	public void reloadConfig() {
		this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
		InputStream defConfigStream = main.getResource(name + ".yml");
		if (defConfigStream == null)
			return;
		this.customConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, StandardCharsets.UTF_8)));
	}
	
	public void createConfig() {
		customConfigFile = new File(main.getDataFolder(), name + ".yml");
		if(!customConfigFile.exists()) {
			customConfigFile.getParentFile().mkdirs();
			main.saveResource(name + ".yml", false);
		}
		
		customConfig = new YamlConfiguration();
		try {
			customConfig.load(customConfigFile);
		}catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void saveConfig() {
	    try {
	      this.getConfig().save(this.customConfigFile);
	    } catch (IOException ex) {
	    } 
	}
}
