package proclient.ui;

import java.io.IOException;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.EaglercraftVersion;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.profile.GuiScreenEditProfile;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class MainMenu extends GuiScreen {
    
    @Override
    public void initGui() {
        
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 - 60, "Multiplayer"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + 0, "Settings"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 - 30, "Discord Server"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 + 30, "Edit Profile"));
	this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 2 + 30, "Nothing Here Yet"));


        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("dragon/background.png"));
        this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2, 2, 2);
        this.drawCenteredString(mc.fontRendererObj, "DragonX V2", this.width / 2 - 170, this.height / 2 - 120, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.id == 0) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if(button.id == 1) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }
        if(button.id == 2) {
            EagRuntime.openLink(EaglercraftVersion.projectForkURL);
        }
        if(button.id == 3) {
			this.mc.displayGuiScreen(new GuiScreenEditProfile(this));
        }
        super.actionPerformed(button);
    }
}

