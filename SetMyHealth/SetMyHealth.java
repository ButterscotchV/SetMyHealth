package me.Dankrushen.SetMyHealth;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SetMyHealth extends JavaPlugin{

	public final Logger logger = Logger.getLogger("Minecraft");
	public static SetMyHealth plugin;
	
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " Has Been Disabled.");
	}

	public void onEnable() {
		try {
	        MetricsLite metrics = new MetricsLite(this);
	        metrics.start();
	    } catch (IOException e) {
	        // Failed to submit the stats :-(
	    }
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " Has Been Enabled.");
		initialiseConfig();
		if(getConfig().getBoolean("Auto-Update") == true){
			@SuppressWarnings("unused")
			Updater updater = new Updater(this, 91493, this.getFile(), Updater.UpdateType.DEFAULT, true);
		}
		else this.logger.info("Auto-Updating is not enabled so SetMyHealth did not check for updates and stayed at version " + pdfFile.getVersion());
	}
	
	public void initialiseConfig(){
		
		final FileConfiguration config = this.getConfig();
		config.options().copyDefaults(true);
		this.saveDefaultConfig();
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
					if(args[0].matches("[0-9.]+") ){
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
					if(args[1].matches("[0-9.]+") ){
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
					if(args[0].matches("[0-9.]+") ){
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
					if(args[1].matches("[0-9.]+") ){
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
					if(args[0].matches("[0-9.]+") ){
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
					if(args[1].matches("[0-9.]+") ){
						@SuppressWarnings("deprecation")
						Player target = player.getServer().getPlayer(args[0]);
						if(target != null){
							double amount = Double.parseDouble(args[1]);
							if(amount <= getConfig().getDouble("MaxHealthLimitOthers")){
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
					if(args[0].matches("[0-9]+") ){
						int amount1 = Integer.parseInt(args[0]);
						int amount = (int) (amount1*19.6078431373);
						if(amount1 <= getConfig().getDouble("MaxAirLimit")){
							player.setMaximumAir(amount);
							player.sendMessage("You have set your max air to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds.");
						}
						else player.sendMessage(ChatColor.DARK_RED + "That number is too high! The maximum amount is " + ChatColor.DARK_GREEN + getConfig().getInt("MaxAirLimit") + "."); 
					}
					else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number! (No decimals!)");
				}
				else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
			}
			else if(args.length == 2){
				if(player.hasPermission("SetMyHealth.use.maxair.others")){
					if(args[1].matches("[0-9]+") ){
						@SuppressWarnings("deprecation")
						Player target = player.getServer().getPlayer(args[0]);
						if(target != null){
							int amount1 = Integer.parseInt(args[1]);
							int amount = (int) (amount1*19.6078431373);
							if(amount1 <= getConfig().getDouble("MaxAirLimitOthers")){
								target.setMaximumAir(amount);
								if(player != target){
									target.sendMessage("Your max air has been set to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds by " + ChatColor.DARK_GREEN + player.getDisplayName() + ChatColor.RESET + ".");
									player.sendMessage(ChatColor.DARK_GREEN + "" + target.getDisplayName() + "'s" + ChatColor.RESET + " max air has been set to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds.");
								}
								else player.sendMessage("You have set your max air to approximatly " + ChatColor.GREEN + amount1 + ChatColor.RESET + " seconds.");
							}
							else player.sendMessage(ChatColor.DARK_RED + "That number is too high! The maximum amount is " + ChatColor.DARK_GREEN + getConfig().getInt("MaxAirOthersLimit") + ".");
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
					if(args[0].matches("[0-9]+") ){
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
					if(args[1].matches("[0-9]+") ){
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
				PluginDescriptionFile pdfFile = this.getDescription();
				double version = Double.parseDouble(pdfFile.getVersion());
				String name = pdfFile.getName();
				if(args.length > 3 || args.length == 2){
					player.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
					return false;
				}
				else if(args.length == 0){
					if(player.hasPermission("SetMyHealth.use.info")){
						player.sendMessage(ChatColor.AQUA + "|||||||" + name + " v" + version + " made by Dankrushen|||||||");
						player.sendMessage(ChatColor.DARK_AQUA + "Permission for all commands: SetMyHealth.use.*");
						player.sendMessage(ChatColor.DARK_AQUA + "For permissions for using the command on other players just add \".others\" to the end except for \"SetMyHealth.use.info\", \"SetMyHealth.use.reload\", and \"SetMyHealth.use.set\".");
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
						player.sendMessage("");
						player.sendMessage(ChatColor.DARK_GREEN + "SetMyHealth Reload");
						player.sendMessage(ChatColor.GOLD + "Description: Reloads the plugins config");
						player.sendMessage(ChatColor.GREEN + "Usage: /SetMyHealth Reload");
						player.sendMessage(ChatColor.LIGHT_PURPLE + "Alias: /SMH R");
						player.sendMessage(ChatColor.DARK_AQUA + "Permission: SetMyHealth.use.reload");
						player.sendMessage("");
						player.sendMessage(ChatColor.DARK_GREEN + "SetMyHealth Set");
						player.sendMessage(ChatColor.GOLD + "Description: Sets an option in the plugins config");
						player.sendMessage(ChatColor.GREEN + "Usage: /SetMyHealth Set <options name> <amount>");
						player.sendMessage(ChatColor.LIGHT_PURPLE + "Alias: /SMH S");
						player.sendMessage(ChatColor.DARK_AQUA + "Permission: SetMyHealth.use.set");
					}
					else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
				}
				else if(args.length == 1){
					if(args[0].equalsIgnoreCase("reload")){
						if(player.hasPermission("SetMyHealth.use.reload")){
							reloadConfig();
							player.sendMessage(ChatColor.AQUA + name + " v" + version + "'s config has been reloaded.");
						}
						else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
					}
					else if(args[0].equalsIgnoreCase("r")){
						if(player.hasPermission("SetMyHealth.use.reload")){
							reloadConfig();
							player.sendMessage(ChatColor.AQUA + name + " v" + version + "'s config has been reloaded.");
						}
						else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
					}
					else if(args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("set")){
						player.sendMessage(ChatColor.DARK_RED + "Not enough arguments!");
						return false;
					}
					else player.sendMessage(ChatColor.DARK_RED + "Unknown argument!"); return false;
				}
				else if(args.length == 3){
					if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("s")){
						if(player.hasPermission("SetMyHealth.use.set")){
							final FileConfiguration config = this.getConfig();
							if(args[1].equalsIgnoreCase("maxhealthlimit") || args[1].equalsIgnoreCase("mhl") || args[1].equalsIgnoreCase("maxhealthlimitothers") || args[1].equalsIgnoreCase("mhlo") || args[1].equalsIgnoreCase("maxairlimit") || args[1].equalsIgnoreCase("mal") || args[1].equalsIgnoreCase("maxairlimitothers") || args[1].equalsIgnoreCase("malo")){
								if(args[2].matches("[0-9.]+") ){
									double amount = Double.parseDouble(args[2]);
									if(args[1].equalsIgnoreCase("maxhealthlimit") || args[1].equalsIgnoreCase("mhl")){
										config.set("MaxHealthLimit", amount);
										saveConfig();
										player.sendMessage(ChatColor.AQUA + "Option \"MaxHealthLimit\" in the config has been set to " + ChatColor.GREEN + amount + ChatColor.AQUA + ".");
									}
									else if(args[1].equalsIgnoreCase("maxhealthlimitothers") || args[1].equalsIgnoreCase("mhlo")){
										config.set("MaxHealthLimitOthers", amount);
										saveConfig();
										player.sendMessage(ChatColor.AQUA + "Option \"MaxHealthLimitOthers\" in the config has been set to " + ChatColor.GREEN + amount + ChatColor.AQUA + ".");
									}
									else if(args[1].equalsIgnoreCase("maxairlimit") || args[1].equalsIgnoreCase("mal")){
										config.set("MaxAirLimit", amount);
										saveConfig();
										player.sendMessage(ChatColor.AQUA + "Option \"MaxAirLimit\" in the config has been set to " + ChatColor.GREEN + amount + ChatColor.AQUA + ".");
									}
									else if(args[1].equalsIgnoreCase("maxairlimitothers") || args[1].equalsIgnoreCase("malo")){
										config.set("MaxAirLimitOthers", amount);
										saveConfig();
										player.sendMessage(ChatColor.AQUA + "Option \"MaxAirLimitOthers\" in the config has been set to " + ChatColor.GREEN + amount + ChatColor.AQUA + ".");
									}
								}
								else player.sendMessage(ChatColor.DARK_RED + "That is not a valid number!");
							}
							else if(args[1].equalsIgnoreCase("auto-update") || args[1].equalsIgnoreCase("au")){
								if(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")){
									boolean amount = Boolean.parseBoolean(args[2]);
									config.set("Auto-Update", amount);
									saveConfig();
									player.sendMessage(ChatColor.AQUA + "Option \"Auto-Update\" in the config has been set to " + ChatColor.GREEN + amount + ChatColor.AQUA + ".");
								}
								else player.sendMessage(ChatColor.DARK_RED + "That is not a valid answer! The two valid answers are \"true\" or \"false\".");
							}
						}
						else player.sendMessage(ChatColor.DARK_RED + "You have insufficiant permissions!");
					}
				}
		}
		return true;
	}
}
