package proclient.module;

import java.util.ArrayList;

import proclient.module.movement.*;
import proclient.module.player.*;
import proclient.module.hud.*;
import proclient.Dragon;
import proclient.command.CommandManager;
import proclient.event.Event;
import proclient.event.events.EventChat;
import proclient.module.combat.*;
import proclient.module.misc.*;
import proclient.module.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ModuleManager {

    public static ArrayList<Module> mods;

    public ModuleManager() {
        mods = new ArrayList<Module>();
        //COMBAT
        newMod(new Criticals());
        newMod(new AutoClicker());
        newMod(new ZenticAura());
        newMod(new KillAura());
        newMod(new FastBow());
        newMod(new AntiCobweb());
        newMod(new AutoWtap());
        newMod(new NoHurtCam());
        newMod(new Aimbot());


        //MOVEMENT
        newMod(new AutoSprint());
        newMod(new NoFall());
        newMod(new Speed());
        newMod(new Glide());
        newMod(new StepHight());
        newMod(new JetPack());
        newMod(new Parkour());
        newMod(new Spider());
        newMod(new Dolphin());
        newMod(new AutoWalk());
        newMod(new Bhop());
        newMod(new InvWalk());
        newMod(new Scaffold());
        newMod(new SafeWalk());

        //PLAYER
        newMod(new AllowFly());
        newMod(new GodMode());
        newMod(new Phase());
        newMod(new FastPlace());
        newMod(new FreeCam());
        newMod(new ShrekScript());

        //RENDER
        newMod(new Fullbright());
        newMod(new Xray());
        newMod(new StorageEsp());
        newMod(new PlayerEsp());
        newMod(new MobEsp());
        newMod(new ClickGui());
        newMod(new ItemEsp());
        newMod(new Nametags());
        newMod(new ArmorStats());
        newMod(new Trails());
        newMod(new Keystrokes());
        newMod(new Fps());
        newMod(new TargetHud());
        newMod(new NoWeather());
        newMod(new VapeOverlay());
        newMod(new Drag());
        newMod(new proclient.ui.ArrayList());
        newMod(new Radar());
        newMod(new CPS());
        newMod(new PotionHUD());
        newMod(new Info());
        newMod(new ComboCounter());


        //MISC
        newMod(new SpeedMine());
        newMod(new AutoRespawn());
        newMod(new ServerCrasher());
        newMod(new FastEat());
        newMod(new Dupe());
        newMod(new PingSpoofer());
        newMod(new Timer());


	//NEWHACKS
	newMod(new intabreack());
	newMod(new regen());
    }

    public static void newMod(Module m) {
        mods.add(m);
    }

    public static ArrayList<Module> getModules() {
        return mods;
    }

    public static void onUpdate() {
        for(Module m : mods) {
            m.onUpdate();
        }
    }

    public static void onRender() {
        for(Module m : mods) {
            m.onRender();
        }
    }

    public static void onKey(int k) {
        for(Module m : mods) {
            if(m.getKey() == k) {
                m.toggle();
            }
        }
    }

    public ArrayList<Module> modsInCategory(Category c){
        ArrayList<Module> inCategory = new ArrayList<>();
        for(Module m : this.mods){
            if(m.category == c)
                inCategory.add(m);
        }
        return inCategory;
    }

	public void addChatMessage(String message) {
		message = "\2479" + Dragon.name + "\2477: " + message;
		
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
	}

    public static void onEvent(Event e) {
        if(e instanceof EventChat) {
            CommandManager.handleChat((EventChat)e);
        }
        for(Module m : mods) {
            if(!m.toggled)
                continue;

            m.onEvent(e);
        }
    }

    
    
}
