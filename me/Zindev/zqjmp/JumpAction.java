package me.Zindev.zqjmp;
import java.util.ArrayList;
import java.util.Arrays;

import me.Zindev.zquest.Gerekli;
import me.Zindev.zquest.chestlib.ChestField;
import me.Zindev.zquest.objects.QuestAction;
import me.Zindev.zquest.objects.extension.QuestActionMark;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

@QuestActionMark(actionID ="JumpAction")//This annotation and actionID part is required !
//Extends your class into QuestAction
public class JumpAction extends QuestAction{
	private static final long serialVersionUID = 1L;
	private Double amount;//We will use this to determinate jump speed of the action.
	//All variables thats gonna be used in chestGUI's must be Wrapped and Serializable.
	//For example if you try to use int instead of Integer you will get an error.
	//Also all of them must have getters and setters.
	public JumpAction() {
		//Set a default value on creation of the action.Always set your variables by default.
		amount = 2.0;
	}
	@Override
	public ArrayList<String> getWikiDesc() {
		// This method will return the description of the action to the admin in ActionMaker GUI.
		return new ArrayList<String>(Arrays.asList("&5&lJUMP JUMP JUMP !"));
	}

	//This method will return the name of the action to the admin in ActionMaker GUI.
	@Override
	public String getWikiName() {
		return "&4&l"+getID();
	}
	//This method appeas in the lore when action is created.
	//Its main purpose is show action's variables without configuring it. 
	@Override
	public String buildString() {
		return "(amount:"+amount+")";
	}

	@Override
	public void execute(Player p) {
		//This method is where you do the action you want to the player.
		p.setVelocity(new Vector(0, amount, 0));
		p.sendMessage("Oi ! You're flying mate !");
		
	}

	//This is the hard part.You use getFields(ArrayList<Object>) method to tell ChestGUI's that
	//your variable can be configured by admin.(with clicking on it ofcourse).
	//the ArrayList must have only ChestField objects.
	//First you have to give an ItemStack for the GUI so that it can show it to the admins.
	//You can use my Gerekli.yapEsya() method to create this,or you can create it manually.
	//In both of them you can use '<value>' tag to show your variable.
	//The vl and ow must be null,zquest set them automaticly.
	//After that name part must be variable's name.This is not a custom name,it's the variable you want
	//to be configurable.For example if I defined a String called jumpDirection at the beggining of the class
	//I was gonna write "jumpDirection" to here.The name must be same as the variable's name.
	//Also the variable must have getters and setters.
	//After that setting part is a custom text where it appers in chat when player clicks to our itemstack.
	//It's like "You're now configuring "+setting
	//If your variable is somekind of Number(Integer,Short,Double etc.) min and max are limitations for them.
	//For example we dont want a -232 or 250 in a percent variable.You can use -99999 99999 to ignore this.
	//If you want another configuration object for your action than add another ChestField in to the arraylist.
	@Override
	public ArrayList<Object> getFields() {
		//new ChestField(is, vl, ow, name, setting, min, max)
		return getFields(new ArrayList<Object>(Arrays.asList(
				new ChestField(
						Gerekli.yapEsya(new ItemStack(Material.DIAMOND), "&4Velocity",
						new ArrayList<String>(Arrays.asList("&cCurrently:<value>")), (short)0)
						
						, null, null, "amount", "&4AMOUNT", 1, 100)
				
				)));
		
	}
	//Required for the chestGUI's
	public Double getAmount() {
		return amount;
	}
	//Required for the chestGUI's
	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
