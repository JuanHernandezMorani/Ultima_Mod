package com.techmdq.Ultima.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public class statScreen <T extends AbstractContainerMenu> extends Screen implements GuiEventListener, ContainerEventHandler{
    private EditBox editBox;
    private final int[] stats = new int[9];

    public statScreen(T menu, Inventory playerInventory, Component title) {
        super(title);

    }
    @Override
    protected void init() {
        super.init();
        int screenWidth = this.width;
        int screenHeight = this.height;
        int editBoxWidth = 20;
        int editBoxHeight = 20;
        int x = (screenWidth - editBoxWidth) / 2;
        int y = (screenHeight - editBoxHeight) / 2;

        this.editBox = new EditBox(font, x, y, editBoxWidth, editBoxHeight, Component.literal("stats"));
        this.addRenderableWidget(this.editBox);

        int buttonWidth = 50;
        int buttonHeight = 20;
        int buttonX = (screenWidth - buttonWidth) / 2;
        int buttonY = y + editBoxHeight + 10;

        for (int i = 0; i < stats.length; i++) {
            int statIndex = i;

            CustomButton button = new CustomButton(buttonX, buttonY, buttonWidth, buttonHeight,Component.literal("+"), (buttonWidget) -> stats[statIndex]++);

            this.addRenderableWidget(button);

            buttonY += buttonHeight + 5;
        }

    }
    @Override
    public void tick() {
        super.tick();
        this.editBox.tick();
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        return true;
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);
        return true;
    }
    @Override
    public void onClose() {
        super.onClose();
    }
    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }
    @Override
    public boolean charTyped(char charTyped, int keyCode) {
        return false;
    }
    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);

        int level = 1;
        String levelText = "Level: " + level;
        int levelX = (width - font.width(levelText)) / 2;
        int levelY = 50;
        font.draw(poseStack, levelText, levelX, levelY, 0xFFFFFF);

        int experience = 0;
        String expText = "Exp to level up: " + (100 - experience);
        int expX = (width - font.width(expText)) / 2;
        int expY = levelY + 20;
        font.draw(poseStack, expText, expX, expY, 0xFFFFFF);

        int statX = 50;
        int statY = expY + 40;
        for (int i = 0; i < stats.length; i++) {
            String statText = "Stat " + (i + 1) + ": " + stats[i];
            font.draw(poseStack, statText, statX, statY, 0xFFFFFF);
            statY += 20;
        }
    }
}
