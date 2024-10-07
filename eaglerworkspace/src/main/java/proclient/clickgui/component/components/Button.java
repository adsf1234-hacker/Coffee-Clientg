package proclient.clickgui.component.components;

import java.awt.Color;
import java.util.ArrayList;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import proclient.Dragon;
import proclient.clickgui.ClickGui;
import proclient.clickgui.component.Component;
import proclient.clickgui.component.Frame;
import proclient.clickgui.component.components.sub.Checkbox;
import proclient.clickgui.component.components.sub.Keybind;
import proclient.clickgui.component.components.sub.ModeButton;
import proclient.clickgui.component.components.sub.Slider;
import proclient.clickgui.component.components.sub.VisibleButton;
import proclient.settings.Setting;
import proclient.module.Module;

public class Button extends Component {

    public Module mod;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    private ArrayList<Component> subcomponents;
    public boolean open;
    private int height;

    public Button(Module mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        height = 12;
        int opY = offset + 12;
        if(Dragon.setmgr.getSettingsByMod(mod) != null) {
            for(Setting s : Dragon.setmgr.getSettingsByMod(mod)){
                if(s.isCombo()){
                    this.subcomponents.add(new ModeButton(s, this, mod, opY));
                    opY += 12;
                }
                if(s.isSlider()){
                    this.subcomponents.add(new Slider(s, this, opY));
                    opY += 12;
                }
                if(s.isCheck()){
                    this.subcomponents.add(new Checkbox(s, this, opY));
                    opY += 12;
                }
            }
        }
        this.subcomponents.add(new Keybind(this, opY));
        this.subcomponents.add(new VisibleButton(this, mod, opY));
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
        int opY = offset + 12;
        for(Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(
            parent.getX(),
            this.parent.getY() + this.offset,
            parent.getX() + parent.getWidth(),
            this.parent.getY() + 12 + this.offset,
            this.isHovered 
                ? (this.mod.isToggled() ? 0xFF6F4E37 : 0xFFB17D5B)  // Coffee colors for hover
                : (this.mod.isToggled() ? 0xFF6F4E37 : 0xFFB17D5B)  // Coffee colors for normal state
        );
        
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        
        // Coffee color for the module name
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
            this.mod.getName(),
            (parent.getX() + 2) * 2,
            (parent.getY() + offset + 2) * 2 + 4,
            this.mod.isToggled() ? 0xFF6F4E37 : 0xFFB17D5B
        );
        
        if (this.subcomponents.size() > 2)
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
                this.open ? "-" : "+",
                (parent.getX() + parent.getWidth() - 10) * 2,
                (parent.getY() + offset + 2) * 2 + 4,
                0xFFB17D5B // Coffee color for the expand/collapse icon
            );
        
        GlStateManager.popMatrix();

        // Render subcomponents if the button is open
        if (this.open) {
            if (!this.subcomponents.isEmpty()) {
                for (Component comp : this.subcomponents) {
                    comp.renderComponent();
                }
                // Left vertical bar with coffee color
                Gui.drawRect(
                    parent.getX() + 2,
                    parent.getY() + this.offset + 12,
                    parent.getX() + 3,
                    parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12),
                    0xFFB17D5B
                );
            }
        }
    }

    @Override
    public int getHeight() {
        if(this.open) {
            return (12 * (this.subcomponents.size() + 1));
        }
        return 12;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = isMouseOnButton(mouseX, mouseY);
        if(!this.subcomponents.isEmpty()) {
            for(Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }
        if(isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for(Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for(Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for(Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
            return true;
        }
        return false;
    }
}
