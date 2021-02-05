package com.detrav.gui;

import cpw.mods.fml.client.GuiScrollingList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

class OresList extends GuiScrollingList {
    private final HashMap<String, Integer> ores;
    private final List<String> keys;
    private final GuiScreen parent;
    private final Consumer<String> onSelected;

    private int selected = -1;

    public OresList(GuiScreen parent, int width, int height, int top, int bottom, int left, int entryHeight, HashMap<String, Integer> aOres, Consumer<String> onSelected) {
        super(parent.mc, width, height, top, bottom, left, entryHeight);
        this.parent = parent;
        this.onSelected = onSelected;
        ores = aOres;
        keys = new ArrayList<>(ores.keySet());
        Collections.sort(keys);
        if(keys.size() > 1) keys.add(0, "All");
        selected = 0;
    }

    @Override
    protected int getSize() {
        return keys.size();
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {
        selected = index;
        if(onSelected != null) onSelected.accept(keys.get(index));
    }

    @Override
    protected boolean isSelected(int index) {
        return selected == index;
    }

    @Override
    protected void drawBackground() {}

    @Override
    protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
        parent.drawString(
                parent.mc.fontRenderer,
                parent.mc.fontRenderer.trimStringToWidth(keys.get(slotIdx), listWidth - 10),
                this.left + 3,
                slotTop - 1,
                ores.getOrDefault(keys.get(slotIdx), 0x7d7b76)
        );
    }
}