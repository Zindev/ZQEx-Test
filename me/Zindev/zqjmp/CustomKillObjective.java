package me.Zindev.zqjmp;

import java.util.ArrayList;
import java.util.Arrays;

import me.Zindev.zquest.Gerekli;
import me.Zindev.zquest.chestlib.ChestField;
import me.Zindev.zquest.objects.QuestObjective;
import me.Zindev.zquest.objects.extension.QuestObjectiveMark;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

//This annotation and objectiveID part is required !,if you make hideSuccess true,it will hide the
//setting of success message from configuration of this objective.
//Since we gonna use the success message,i'll make it false.
@QuestObjectiveMark(objectiveID = "testObjective",hideSuccess = false)
//Extend your class into QuestObjective
public class CustomKillObjective extends QuestObjective{
	private static final long serialVersionUID = 1L;
	
	
	//Create a list from EntityType enum names.We will use this to create a special list chest field.
	private static ArrayList<String> getMobList(){
		return new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				for(EntityType e : EntityType.values()){
					if(e.isAlive()){
						add(e.name());
					}
				}
		}};

	}
	private int amt;
	private String entity;
	private String name;
	//Chest configuration fields for variables.Check JumpAction class for more info.
	private transient final static ArrayList<Object> chestFields = new ArrayList<Object>(Arrays.asList(
			new ChestField( 
					Gerekli.yapEsya(new ItemStack(Material.DIAMOND), "&5&lMob Type", 
							new ArrayList<String>(Arrays.asList(
									"&dWhat should player kill ?",
									"&5&lCurrently:&d<value>"
									))
							, (short)0)
					
					, null, null, "entity", "&dMob Type", 0, 0,getMobList()),
					//If you write an ArrayList<String> after min max,chest field will use it as a special list.
			new ChestField( 
					Gerekli.yapEsya(new ItemStack(Material.GOLD_INGOT), "&4&lRequired Kill", 
							new ArrayList<String>(Arrays.asList(
									"&cHow many times player",
									"&cshould kill this mob?",
									"&4&lCurrently:&c<value>"
									))
							, (short)0)
					
					, null, null, "amt", "&cReqiured Kill", 1, 999999),
			new ChestField( 
					Gerekli.yapEsya(new ItemStack(Material.IRON_INGOT), "&3&lOptional Name", 
							new ArrayList<String>(Arrays.asList(
									"&bShould mob have a name ?",
									"&bwrite '#EMPTY' to ignore this.",
									"&bColor codes can be used.",
									"&3&lCurrently:&b<value>"
									))
							, (short)0)
					
					, null, null, "name", "&dOptional Mob Name", 0, 0)
					
				));
	
	
	public CustomKillObjective(){
		setVariables(new String[2]);//Define how much variables in this objective
		setVariable("<amount>", "remaining kill", 0);//Define the first one
		setVariable("<mob>", "pure name of mob", 1);//Define the second one
		setCompleteMessage("&cYou completed my custom kill objective");//Set the default kill complete.
		setSuccessMessage(
				"&cYou succes my custom kill objective:<amount>");//Set the default kill succes with variable.
		setDisplayName("&cKill <mob>s <amount> times");//Display name of the objective.(used in current objectives)
		this.amt = 5; //Define the default amount
		entity = EntityType.PIG.name();//Define the default mob name
		name = "#EMPTY";//Define the default optional name as #EMPTY.
	}
	
	
	//Use this to make your code more  organized.
	@Override
	public void success() {
		amt--;
	}

	
	//Put the complete condition into this method.
	//If this returns true zquest will accept this objective as completed.
	@Override
	public boolean check() {
		//Check if player killed amount of mobs and if not,say this objective is not completed yet.
		if(amt > 0)return false;
		return true;
	}
	
	//PS:We listen and trigger checkIn in MyListener class.
	public void checkIn(EntityDeathEvent e,Player p){
		//Use a boolean to determinate the entitiy is a player or non-player living entity.
		boolean pl = (getEntityType().equals(EntityType.PLAYER) && e.getEntity() instanceof Player);
		//Check the entity's type name is equal to our entity variable or directly accept it if its a player.
		if(e.getEntity().getType().name().equals(entity) || pl){
			//Check that if objective requires an optional name.
			if(!name.equalsIgnoreCase("#EMPTY")){
				//If entity is a player
				if(pl){
					//Cast it into player
					Player en = (Player) e.getEntity();
					//If player's name is null return.
					if(en.getName() == null)return;
					//Gerekli.cevc is a color code translate method.
					//If player's name is not equal to our colored name variable return.
					if(!en.getName().equals(Gerekli.cevc(name)))return;
				}
				else{
					//Define the event's entity into en variable.
					LivingEntity en = e.getEntity();
					//If entity's name is null return.
					if(en.getCustomName() == null)return;
					//Gerekli.cevc is a color code translate method.
					//If entity's name is not equal to our colored name variable return.
					if(!en.getCustomName().equals(Gerekli.cevc(name)))return;
				}

			}
			//Always check objective's conditions before success.
			if(!checkConditions(p))return;
			//Success the objective
			success();
			//Send the success message
			Gerekli.yollaMesaj(p, getSuccessMessage());//Colored message sender,you can use p.sendMessage too.
		}
	}
	//This method appears in the lore when condition is created.
	//Its main purpose is show condition's variables without configuring it. 
	@Override
	public String buildString() {
		return "(amount:"+amt+",type:"+entity+")";
	}

	//Required for Chest GUIs always use Wrapped Objects.
	public String getEntity() {
		return entity;
	}
	//Required for Chest GUIs always use Wrapped Objects.
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	
	//Since getEntity and setEntity methods are used by chest GUIs,writing same methods with EntityType
	//can be quite handy.
	public EntityType getEntityType(){
		return EntityType.valueOf(entity);
	}
	//Since getEntity and setEntity methods are used by chest GUIs,writing same methods with EntityType
	//can be quite handy.
	public void setEntityType(EntityType e){
		entity = e.name();
	}
	
	
	//Required for Chest GUIs always use Wrapped Objects.
	public Integer getAmt() {
		return amt;
	}
	//Required for Chest GUIs always use Wrapped Objects.
	public void setAmt(Integer amt) {
		this.amt = amt;
	}
	
	
	//Required for Chest GUIs always use Wrapped Objects.
	public String getName() {
		return name;
	}
	//Required for Chest GUIs always use Wrapped Objects.
	public void setName(String name) {
		this.name = name;
	}
	
	
	//Override the getDisplayName method,and replace the variable strings with variables.
	@Override
	public String getDisplayName() {
		return super.getDisplayName().replaceAll("<amount>", ""+amt).replaceAll("<mob>", entity);
	}
	
	
	//Override the getSuccessMessage method,and replace the variable strings with variables.
	//And if the player killed the last entity(which will make the amt 0) show complete message.
	@Override
	public String getSuccessMessage() {
		if(amt == 0){
			return super.getCompleteMessage();
		}
		return super.getSuccessMessage().replaceAll("<amount>", ""+amt).replaceAll("<mob>", entity);
	}
	
	
	//Override the getDisplayName method,and replace the variable strings with variables.
	@Override
	public String getCompleteMessage() {
		return super.getCompleteMessage().replaceAll("<amount>", ""+amt).replaceAll("<mob>", entity);
	}
	
	
	//Return the ArrayList<Object> chestFields with getFields(ArrayList<Object>) method.
	//Never return it directly like return chestFields;
	@Override
	public ArrayList<Object> getFields() {
		return getFields(chestFields);
	}
	

	// This method will return the name of the objective to admin in ObjectiveMaker GUI.
	@Override
	public String getWikiName() {
		return "&4"+getID();
	}
	
	
	// This method will return the material type of the objective to admin in ObjectiveMaker GUI.
	@Override
	public Material getWikiMaterial() {
		// TODO Auto-generated method stub
		return Material.DIAMOND_BARDING;
	}
	
	
	// This method will return the lore of the objective to admin in ObjectiveMaker GUI.
	@Override
	public ArrayList<String> getWikiDesc() {
		return new ArrayList<String>(Arrays.asList(
				"&cPlayer needs to kill",
				"&camount of given entites."
				));

	}

}
