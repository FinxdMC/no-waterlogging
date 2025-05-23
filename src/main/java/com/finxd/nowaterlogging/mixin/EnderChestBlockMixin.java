package com.finxd.nowaterlogging.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderChestBlock.class)

public class EnderChestBlockMixin implements SimpleWaterloggedBlock {

    //this removes the water from the block, doesn't remove the waterlogged property
    //the block will have it's waterlogged property set to true when placing it in water, but it still looks and behaves like it never had water
    @Inject(method = "getFluidState", at = @At("HEAD"), cancellable = true)
    public void getFluidState(BlockState pState, CallbackInfoReturnable<FluidState> cir) {
        cir.setReturnValue(Fluids.EMPTY.defaultFluidState());
    }

    //buckets no longer try to fill the block
    @Override
    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return false;
    }

    //buckets no longer try to empty the block, if the block happens to have waterlogged set to true
    @Override
    public @NotNull ItemStack pickupBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        return ItemStack.EMPTY;
    }
}
