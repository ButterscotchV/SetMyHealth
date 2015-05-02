package me.Dankrushen.SetMyHealth;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class setMyHealth extends JavaPlugin{

	public final Logger logger = Logger.getLogger("Minecraft");
	public static setMyHealth plugin;
	
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " Has Been Disabled.");
	}

	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " Has Been Enabled.");
		initialiseConfig();
	}
	
	public void initialiseConfig(){
		
		final FileConfiguration config = this.getConfig();
        config.options().header("Configuration For SetMyHealth");
 
        config.addDefault("Note", "Changes how high players can set their own maximum health (This is just information about the config setting below)");
        config.addDefault("MaxHealthLimit", 50);
        config.addDefault("Note2", "Changes how high players can set other players maximum health (This is just information about the config setting below)");
        config.addDefault("MaxHealthOthersLimit", 50);
        
        config.addDefault("Note3", "Changes how high players can set their own maximum air (This is just information about the config setting below)");
        config.addDefault("MaxAirLimit", 60);
        config.addDefault("Note4", "Changes how high players can set other players maximum air (This is just information about the config setting below)");
        config.addDefault("MaxAirOthersLimit", 60);
 
        config.options().copyDefaults(true);
        
        saveConfig();
		}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("sethealth") || commandLabel.equalsIgnoreCase("sh")){
			if(args.length == 0){
				player.sendMessage(ChatColor.DARK_RED + "Not enough arguments!");
				return false;
			}
			else if(args.length == 1){
				if(player.hasPermission("SetMyHealth.use.sethealth")){
					if ( args[0].matches("[0-9.]+") ){
						double amount = Double.parseDouble(args[0]);
						if(amount*2 <= player.getMaxHealth()){
							player.setHealth(amount*2);
							player.sendMessage("You have set your health to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts.");
						}
						else player.sendMessage(ChatColor.DARK_RED + "That number is too high! Your max health is " + ChatColor.DARK_GREEN + player.getMaxHealth()/2);
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number!");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length == 2){
				if(player.hasPermission("SetMyHealth.use.sethealth.others")){
					if ( args[1].matches("[0-9.]+") ){
						@SuppressWarnings("deprecation")
						Player target = player.getServer().getPlayer(args[0]);
						if(target != null){
							double amount = Double.parseDouble(args[1]);
							if(amount*2 <= target.getMaxHealth()){
								target.setHealth(amount*2);
								if(player != target){
									target.sendMessage("Your health has been set to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts by " + ChatColor.DARK_GREEN + player.getDisplayName() + ChatColor.RESET + ".");
									player.sendMessage(ChatColor.DARK_GREEN + "" + target.getDisplayName() + "'s" + ChatColor.RESET + " health has been set to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts.");
								}
								else player.sendMessage("You have set your health to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts.");
							}
							else player.sendMessage(ChatColor.DARK_RED + "That number is too high! " + ChatColor.DARK_GREEN + args[0] + "'s" + ChatColor.DARK_RED + " max health is " + ChatColor.DARK_GREEN + target.getMaxHealth()/2);	
						}
						else player.sendMessage(ChatColor.DARK_RED + "Could not find player \"" + ChatColor.DARK_GREEN + args[0] + ChatColor.DARK_RED + "\"");
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number!");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length > 2){
				player.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
				return false;
			}
		}
		if(commandLabel.equalsIgnoreCase("sethunger") || commandLabel.equalsIgnoreCase("shr")){
			if(args.length == 0){
				player.sendMessage(ChatColor.DARK_RED + "Not enough arguments!");
				return false;
			}
			else if(args.length == 1){
				if(player.hasPermission("SetMyHealth.use.sethunger")){
					if ( args[0].matches("[0-9.]+") ){
						double amount = Double.parseDouble(args[0]);
						Double amount2= amount*2;
						int amountfinal = amount2.intValue();
						if(amount <= 10){
							player.setSaturation(20);
							player.setFoodLevel(amountfinal);
							player.sendMessage("You have set your hunger to " + ChatColor.GREEN + amount + ChatColor.RESET + ".");
						}
						else player.sendMessage(ChatColor.DARK_RED + "That number is too high! Your max hunger is " + ChatColor.DARK_GREEN + "10" + ChatColor.DARK_RED + ".");
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number!");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length == 2){
				if(player.hasPermission("SetMyHealth.use.sethunger.others")){
					if ( args[1].matches("[0-9.]+") ){
						@SuppressWarnings("deprecation")
						Player target = player.getServer().getPlayer(args[0]);
						if(target != null){
							double amount = Double.parseDouble(args[1]);
							Double amount2= amount*2;
							int amountfinal = amount2.intValue();
							if(amount <= 10){
								target.setSaturation(20);
								target.setFoodLevel(amountfinal);
								if(player != target){
									target.sendMessage("Your hunger has been set to " + ChatColor.GREEN + amount + ChatColor.RESET + " by " + ChatColor.DARK_GREEN + player.getDisplayName() + ChatColor.RESET + ".");
									player.sendMessage(ChatColor.DARK_GREEN + "" + target.getDisplayName() + "'s" + ChatColor.RESET + " hunger has been set to " + ChatColor.GREEN + amount + ChatColor.RESET + ".");
								}
								else player.sendMessage("You have set your hunger to " + ChatColor.GREEN + amount + ChatColor.RESET + ".");
							}
							else player.sendMessage(ChatColor.DARK_RED + "That number is too high! " + ChatColor.DARK_GREEN + args[0] + "'s" + ChatColor.DARK_RED + " max hunger is " + ChatColor.DARK_GREEN + "10" + ChatColor.DARK_RED + ".");
						}
						else player.sendMessage(ChatColor.DARK_RED + "Could not find player \"" + ChatColor.DARK_GREEN + args[0] + ChatColor.DARK_RED + "\"");
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number!");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length > 2){
				player.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
				return false;
			}
		}
		if(commandLabel.equalsIgnoreCase("maxhealth") || commandLabel.equalsIgnoreCase("mh")){
			if(args.length == 0){
				player.sendMessage(ChatColor.DARK_RED + "Not enough arguments!");
				return false;
			}
			else if(args.length == 1){
				if(player.hasPermission("SetMyHealth.use.maxhealth")){
					if ( args[0].matches("[0-9.]+") ){
						double amount = Double.parseDouble(args[0]);
						if(amount <= getConfig().getDouble("MaxHealthLimit")){
							player.setMaxHealth(amount*2);
							player.sendMessage("You have set your max health to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts.");
						}
						else player.sendMessage(ChatColor.DARK_RED + "That number is too high! The maximum amount is " + ChatColor.DARK_GREEN + getConfig().getInt("MaxHealthLimit") + ".");
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number!");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length == 2){
				if(player.hasPermission("SetMyHealth.use.maxhealth.others")){
					if ( args[1].matches("[0-9.]+") ){
						@SuppressWarnings("deprecation")
						Player target = player.getServer().getPlayer(args[0]);
						if(target != null){
							double amount = Double.parseDouble(args[1]);
							if(amount <= getConfig().getDouble("MaxHealthOthersLimit")){
								target.setMaxHealth(amount*2);
								if(player != target){
									target.sendMessage("Your max health has been set to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts by " + ChatColor.DARK_GREEN + player.getDisplayName() + ChatColor.RESET + ".");
									player.sendMessage(ChatColor.DARK_GREEN + "" + target.getDisplayName() + "'s" + ChatColor.RESET + " max health has been set to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts.");
								}
								else player.sendMessage("You have set your max health to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts.");
							}
							else player.sendMessage(ChatColor.DARK_RED + "That number is too high! The maximum amount is " + ChatColor.DARK_GREEN + getConfig().getInt("MaxHealthOthersLimit") + ".");
						}
						else player.sendMessage(ChatColor.DARK_RED + "Could not find player \"" + ChatColor.DARK_GREEN + args[0] + ChatColor.DARK_RED + "\"");
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number!");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length > 2){
				player.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
				return false;
			}
		}
		if(commandLabel.equalsIgnoreCase("maxair") || commandLabel.equalsIgnoreCase("ma")){
			if(args.length == 0){
				player.sendMessage(ChatColor.DARK_RED + "Not enough arguments!");
				return false;
			}
			else if(args.length == 1){
				if(player.hasPermission("SetMyHealth.use.maxair")){
					if ( args[0].matches("[0-9]+") ){
						int amount1 = Integer.parseInt(args[0]);
						int amount = (int) (amount1*19.6078431373);
						if(amount1 <= getConfig().getDouble("MaxAirLimit")){
							player.setMaximumAir(amount);
							player.sendMessage("You have set your max air to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds.");
						}
						else player.sendMessage(ChatColor.DARK_RED + "That number is too high! The maximum amount is " + ChatColor.DARK_GREEN + getConfig().getInt("MaxAirLimit") + "."); //Remove "'60 (Will be in config).'" from this line when config is working
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number! (No decimals!)");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length == 2){
				if(player.hasPermission("SetMyHealth.use.maxair.others")){
					if ( args[1].matches("[0-9]+") ){
						@SuppressWarnings("deprecation")
						Player target = player.getServer().getPlayer(args[0]);
						if(target != null){
							int amount1 = Integer.parseInt(args[1]);
							int amount = (int) (amount1*19.6078431373);
							if(amount1 <= getConfig().getDouble("MaxAirOthersLimit")){
								target.setMaximumAir(amount);
								if(player != target){
									target.sendMessage("Your max air has been set to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds by " + ChatColor.DARK_GREEN + player.getDisplayName() + ChatColor.RESET + ".");
									player.sendMessage(ChatColor.DARK_GREEN + "" + target.getDisplayName() + "'s" + ChatColor.RESET + " max air has been set to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds.");
								}
								else player.sendMessage("You have set your max air to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds.");
							}
							else player.sendMessage(ChatColor.DARK_RED + "That number is too high! The maximum amount is " + ChatColor.DARK_GREEN + getConfig().getInt("MaxAirOthersLimit") + "."); //Remove "'60 (Will be in config).'" from this line when config is working
						}
						else player.sendMessage(ChatColor.DARK_RED + "Could not find player \"" + ChatColor.DARK_GREEN + args[0] + ChatColor.DARK_RED + "\"");
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number! (No decimals!)");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length > 2){
				player.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
				return false;
			}
		}
		if(commandLabel.equalsIgnoreCase("setair") || commandLabel.equalsIgnoreCase("sa")){
			if(args.length == 0){
				player.sendMessage(ChatColor.DARK_RED + "Not enough arguments!");
				return false;
			}
			else if(args.length == 1){
				if(player.hasPermission("SetMyHealth.use.setair")){
					if ( args[0].matches("[0-9]+") ){
						int amount1 = Integer.parseInt(args[0]);
						int amount = (int) (amount1*19.6078431373);
						if(amount <= player.getMaximumAir()){
							player.setRemainingAir(amount);
							player.sendMessage("You have set your air to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds.");
						}
						else player.sendMessage(ChatColor.DARK_RED + "That number is too high! Your maximum amount is " + ChatColor.DARK_GREEN + Math.ceil(player.getMaximumAir()/19.6078431373));
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number! (No decimals!)");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length == 2){
				if(player.hasPermission("SetMyHealth.use.setair.others")){
					if ( args[1].matches("[0-9]+") ){
						@SuppressWarnings("deprecation")
						Player target = player.getServer().getPlayer(args[0]);
						if(target != null){
							int amount1 = Integer.parseInt(args[1]);
							int amount = (int) (amount1*19.6078431373);
							if(amount1 <= Math.ceil(target.getMaximumAir()/19.6078431373)){
								target.setRemainingAir(amount);
								if(player != target){
									target.sendMessage("Your air has been set to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds by " + ChatColor.DARK_GREEN + player.getDisplayName() + ChatColor.RESET + ".");
									player.sendMessage(ChatColor.DARK_GREEN + "" + target.getDisplayName() + "'s" + ChatColor.RESET + " air has been set to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds.");
								}
								else player.sendMessage("You have set your air to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds.");
							}
							else player.sendMessage(ChatColor.DARK_RED + "That number is too high! " + ChatColor.DARK_GREEN + target + "'s" + ChatColor.RESET + " maximum amount is " + ChatColor.DARK_GREEN + Math.ceil(target.getMaximumAir()/19.6078431373));
						}
						else player.sendMessage(ChatColor.DARK_RED + "Could not find player \"" + ChatColor.DARK_GREEN + args[0] + ChatColor.DARK_RED + "\"");
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number! (No decimals!)");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length > 2){
				player.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
				return false;
			}
		}
		if(commandLabel.equalsIgnoreCase("setmyhealth") || commandLabel.equalsIgnoreCase("smh")){
			if(player.hasPermission("SetMyHealth.use.info")){
				PluginDescriptionFile pdfFile = this.getDescription();
				double version = Double.parseDouble(pdfFile.getVersion());
				String name = pdfFile.getName();
				if(args.length > 0){
					player.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
					return false;
				}
				player.sendMessage(ChatColor.AQUA + "|||||||" + name + " v" + version + " made by Dankrushen|||||||");
				player.sendMessage(ChatColor.DARK_AQUA + "Permission for all commands: SetMyHealth.use.*");
				player.sendMessage(ChatColor.DARK_AQUA + "For permissions for using the command on other players just add \".others\" to the end.");
				player.sendMessage("");
				player.sendMessage(ChatColor.DARK_GREEN + "Commands:");
				player.sendMessage("");
				player.sendMessage(ChatColor.DARK_GREEN + "SetHealth");
				player.sendMessage(ChatColor.GOLD + "Description: Sets a players health");
				player.sendMessage(ChatColor.GREEN + "Usage: /SetHealth [player] <amount>");
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Alias: /SH");
				player.sendMessage(ChatColor.DARK_AQUA + "Permission: SetMyHealth.use.sethealth");
				player.sendMessage("");
				player.sendMessage(ChatColor.DARK_GREEN + "SetHunger");
				player.sendMessage(ChatColor.GOLD + "Description: Sets a players hunger");
				player.sendMessage(ChatColor.GREEN + "Usage: /SetHunger [player] <amount>");
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Alias: /SHR");
				player.sendMessage(ChatColor.DARK_AQUA + "Permission: SetMyHealth.use.sethunger");
				player.sendMessage("");
				player.sendMessage(ChatColor.DARK_GREEN + "SetAir");
				player.sendMessage(ChatColor.GOLD + "Description: Sets a players air");
				player.sendMessage(ChatColor.GREEN + "Usage: /SetAir [player] <amount>");
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Alias: /SA");
				player.sendMessage(ChatColor.DARK_AQUA + "Permission: SetMyHealth.use.setair");
				player.sendMessage("");
				player.sendMessage(ChatColor.DARK_GREEN + "MaxHealth");
				player.sendMessage(ChatColor.GOLD + "Description: Sets a players max health");
				player.sendMessage(ChatColor.GREEN + "Usage: /MaxHealth [player] <amount>");
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Alias: /MH");
				player.sendMessage(ChatColor.DARK_AQUA + "Permission: SetMyHealth.use.maxhealth");
				player.sendMessage("");
				player.sendMessage(ChatColor.DARK_GREEN + "MaxAir");
				player.sendMessage(ChatColor.GOLD + "Description: Sets a players max air");
				player.sendMessage(ChatColor.GREEN + "Usage: /MaxAir [player] <amount>");
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Alias: /MA");
				player.sendMessage(ChatColor.DARK_AQUA + "Permission: SetMyHealth.use.maxair");
				player.sendMessage("");
				player.sendMessage(ChatColor.DARK_GREEN + "SetMyHealth");
				player.sendMessage(ChatColor.GOLD + "Description: Gives plugin information");
				player.sendMessage(ChatColor.GREEN + "Usage: /SetMyHealth");
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Alias: /SMH");
				player.sendMessage(ChatColor.DARK_AQUA + "Permission: SetMyHealth.use.info");
			}
			else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
		}
		return true;
	}
}
