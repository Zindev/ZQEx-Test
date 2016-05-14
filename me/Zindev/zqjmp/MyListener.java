package me.Zindev.zqjmp;

import me.Zindev.zquest.objects.extension.ZQuestAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MyListener implements Listener{
	@EventHandler
	public void deathListen(EntityDeathEvent e){
		//If entity's killer is null return;
		if(e.getEntity().getKiller() == null) return;
		//Define the killer into p.
		Player p = e.getEntity().getKiller();
		//Check if player is making a quest right now.
		if(!ZQuestAPI.playerIsMakingQuest(p.getUniqueId()))return;
		//If player has a customKillObjective in his current objectives this variable will return it.
		//If player doesn't have the objective or the quest or if all customKillObjectives are done
		//will return null.
		
		CustomKillObjective ob = ZQuestAPI.playerHasObjective(p.getUniqueId(), CustomKillObjective.class,true);
		//If ob is not null,means that there is a customKillObjective that is not done.So we can trigger checkIn().
		if(ob != null)ob.checkIn(e, p);
			
	}

}
