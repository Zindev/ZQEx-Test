package me.Zindev.zqjmp;

import me.Zindev.zquest.objects.extension.ZQuestAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		//Register our extension object in the name of this plugin.
		//I used depend tag in plugin.yml so there is no need to check if zquest is loaded.
		//Also name tag in plugin.yml must start with 'ZQEx-'.
		ZQuestAPI.registerExtension(JumpAction.class,this);
		ZQuestAPI.registerExtension(CreativeCondition.class,this);
		ZQuestAPI.registerExtension(CustomKillObjective.class,this);
		//Register our listener class
		Bukkit.getPluginManager().registerEvents(new MyListener(), this);
	}
	@Override
	public void onDisable() {
		//Check if zquest enabled.
		if(Bukkit.getPluginManager().isPluginEnabled("ZQuest")){
			//If it is,unregister all extension objects belongs to this plugin.
			ZQuestAPI.unregisterAll(this);
		}
	}

}
