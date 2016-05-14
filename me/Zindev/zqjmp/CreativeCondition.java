package me.Zindev.zqjmp;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import me.Zindev.zquest.objects.QuestCondition;
import me.Zindev.zquest.objects.extension.QuestConditionMark;
import me.Zindev.zquest.objects.land.LandEffect.LandCondition;

@QuestConditionMark(conditionID = "testCondition")//This annotation and conditionID part is required !
//Extend your class into QuestCondition and if you want this to be used in landEffects too,implement it to
//Land Condition
public class CreativeCondition extends QuestCondition implements LandCondition{
	private static final long serialVersionUID = 1L;

	//Same with JumpAction class.
	@Override
	public ArrayList<Object> getFields() {
		return new ArrayList<Object>();
	}
	// This method will return the name of the condition to the admin in ConditionMaker GUI.
	@Override
	public String getWikiName() {
		
		return "&4&lGamemode";
	}
	// This method will return the description of the condition to admin in ConditionMaker GUI.
	@Override
	public ArrayList<String> getWikiDesc() {
		
		return new ArrayList<String>();
	}
	//This method appears in the lore when condition is created.
	//Its main purpose is show condition's variables without configuring it. 
	@Override
	public String buildString() {
		
		return "()";
	}

	//This is the important part,if this returns true condition will be 'true' if not it will be 'false'.
	//For example with this condition you can send message to the player if player is creative(in quest maker.)
	@Override
	public boolean check(Player p) {
		return p.getGameMode().equals(GameMode.CREATIVE);
	}

}
