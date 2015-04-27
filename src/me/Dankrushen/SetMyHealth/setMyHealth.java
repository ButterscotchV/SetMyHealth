package me.Dankrushen.SetMyHealth;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class setMyHealth extends JavaPlugin{

	public final Logger logger = Logger.getLogger("Minecraft");
	public static setMyHealth plugin;
	
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Disabled.");
	}

	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("sethealth")){
			if(args.length == 0){
				player.sendMessage(ChatColor.DARK_RED + "Not enough arguments!");
				return false;
			}
			else if(args.length == 1){
				double amount = Double.parseDouble(args[0]);
				if(amount*2 <= player.getMaxHealth()){
					player.setHealth(amount*2);
					player.sendMessage("You have set your health to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts.");
				}
				else player.sendMessage(ChatColor.DARK_RED + "That number is too high! Your max health is " + ChatColor.DARK_GREEN + player.getMaxHealth()/2);
			}
			else if(args.length == 2){
				@SuppressWarnings("deprecation")
				Player target = player.getServer().getPlayer(args[0]);
				if(target != null){
					double amount = Double.parseDouble(args[1]);
					if(amount*2 <= target.getMaxHealth()){
						target.setHealth(amount*2);
						target.sendMessage("Your health has been set to " + ChatColor.GREEN + amount + ChatColor.RESET + " hearts.");
					}
					else player.sendMessage(ChatColor.DARK_RED + "That number is too high! " + ChatColor.DARK_GREEN + args[0] + "'s" + ChatColor.DARK_RED + " max health is " + ChatColor.DARK_GREEN + target.getMaxHealth()/2);	
				}
				else player.sendMessage(ChatColor.DARK_RED + "Could not find player \"" + ChatColor.DARK_GREEN + args[0] + ChatColor.DARK_RED + "\"");
			}
			else if(args.length > 2){
				player.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
				return false;
			}
		}
		return true;
	}
}
