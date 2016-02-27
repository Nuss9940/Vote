package de.nuss9940.vote;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;

public class Main extends JavaPlugin {


private ArrayList<String> msgs = new ArrayList<>();


@Override
public void onEnable() {
	loadConfig();
}


@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

	if (command.getName().equalsIgnoreCase("vote")) {

		if (args.length > 0 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl"))) {

			if (sender instanceof Player) {
				if (sender.hasPermission("vote.reload")) {
					loadConfig();
					sender.sendMessage(ChatColor.GREEN + "Konfiguration neugeladen!");
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "Du hast keine Berechtigung dazu!");
					return true;
				}
			} else {
				loadConfig();
				sender.sendMessage(ChatColor.GREEN + "Konfiguration neugeladen!");
				return true;
			}


		} else {

			for (String thisline : msgs) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', thisline));
			}
			//sender.sendMessage(ChatColor.GREEN + "Auf diesen Seiten kannst du für Eona Voten und du bekommst ein Geschenk :D");
			//sender.sendMessage(ChatColor.GREEN + "1.https://www.minecraft-serverlist.eu/server/120-Eona/vote");
			//sender.sendMessage(ChatColor.GREEN + "2.http://mcsl.name/11845");
			//sender.sendMessage(ChatColor.GREEN + "3.http://topg.org/Minecraft/in-371288");
			//sender.sendMessage(ChatColor.GREEN + "4.https://minestatus.net/123521-eona");
			//sender.sendMessage(ChatColor.GREEN + "5.http://mc-serverlist.com/vote.php?id=6868");
			return true;
		}
	} else {
		return false;
	}

}


private void loadConfig() {

	msgs.clear();

	if (getDataFolder().mkdirs()) {

		File config = new File(getDataFolder().getPath() + "config.yml");

		try {
			if (config.createNewFile()) {

				msgs.add("&4&nEdit this text in the configuration of this plugin.");

			} else {

				FileInputStream fstream = null;
				try {
					fstream = new FileInputStream(config);
				} catch (FileNotFoundException e) {
					error("Could not read config file", e);
				}

				BufferedReader br;
				if (fstream != null) {
					br = new BufferedReader(new InputStreamReader(fstream));

					String thisline;

					try {
						while ((thisline = br.readLine()) != null) {

							if (!thisline.isEmpty()) {
								msgs.add(thisline);
							}

						}
					} catch (Exception e) {
						error("Could not read line in config", e);
					}

					try {
						fstream.close();
					} catch (IOException e) {
						error("Could not close config file", e);
					}
					try {
						br.close();
					} catch (IOException e) {
						error("Could not close config file", e);
					}
				}

			}
		} catch (IOException e) {
			error("Could not create config file", e);
		}

	} else {
		//Could not create folders
		error("Could not create folders for config");
	}

}


private void error(String msg) {
	getLogger().warning("[Vote] " + msg);
}
private void error(String msg, Exception e) {
	getLogger().warning("[Vote] " + msg + "ERROR: " + e.getLocalizedMessage());
}


}
