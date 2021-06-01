package com.railwayteam.railways.items;

import com.railwayteam.railways.ModSetup;
import com.railwayteam.railways.entities.conductor.ConductorEntity;
import com.railwayteam.railways.util.EntityItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static com.tterrag.registrate.providers.RegistrateLangProvider.toEnglishName;

public class ConductorItem extends EntityItem<ConductorEntity> {
    public static ConductorItem g() {
        return ModSetup.R_ITEM_CONDUCTOR.get();
    }

    public ItemStack create(ConductorEntity entity) {
        ItemStack stack = new ItemStack(ModSetup.R_ITEM_CONDUCTOR.get());;
        putEntityDataInItem(stack, entity);
        return stack;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if(hasEntity(stack)) {
            ConductorEntity entity = getEntityFromItem(stack, Minecraft.getInstance().world);
            return entity.hasCustomName() ? new StringTextComponent(entity.getCustomName().getString()).styled(style -> style.withItalic(true)) : nameWithColor(entity.getColor().getTranslationKey());
        }
        return nameWithColor(ConductorEntity.getDefaultColor().getTranslationKey());
    }

    public static ITextComponent nameWithColor(String color) {
        return new TranslationTextComponent("item.railways.conductor_" + color);
    }


    @Override
    public ConductorEntity spawnEntity(PlayerEntity plr, ItemStack stack, BlockPos pos) {
        return ConductorEntity.spawn(plr.world, pos, ConductorEntity.getDefaultColor());
    }

    public ConductorItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    public ActionResultType onMinecartRightClicked(PlayerEntity plr, ItemStack stack, Hand hand, MinecartEntity entity) {
        ConductorEntity conductor = spawn(stack, entity.getBlockPos(), plr, Direction.UP);
        return conductor.startRiding(entity) ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }
}
