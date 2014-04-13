package de.derluuc.serversigns.lang;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.derluuc.serversigns.ServerSigns;

public class LanguageManager {

	private Logger logger;
	
	private File langFile;
	private FileConfiguration locales;
	
	public LanguageManager(String languageId) {
		logger = ServerSigns.getInstance().getLogger();
		langFile = new File("plugins" + File.separator + "ServerSigns" + File.separator + languageId + ".yml");
		logger.log(Level.INFO, "Loading language file for language '" + languageId + "'.");
		if(!langFile.exists()) {
			logger.log(Level.INFO, "Could not load language '" + languageId + "', so using 'en' now.");
			langFile = new File("plugins" + File.separator + "ServerSigns" + File.separator + "en.yml");
		}
		
		locales = YamlConfiguration.loadConfiguration(langFile);		
	}
	
	public String getLocaleMessage(String node) {
		return locales.getString(node);
	}

}
