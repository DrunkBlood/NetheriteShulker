package drunkblood.nethershulker;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetheriteShulkerMenu extends AbstractContainerMenu {
    private static final Logger LOGGER = LogManager.getLogger();
    private final NetheriteShulkerBlockEntity blockEntity;
    private final Player player;
    private final IItemHandler playerInventory;

    public NetheriteShulkerMenu(int windowId, BlockPos pos, Inventory inv, Player player) {
        super(NetheriteShulker.NETHERITE_SHULKER_CONTAINER.get(), windowId);
        blockEntity = (NetheriteShulkerBlockEntity) player.getCommandSenderWorld().getBlockEntity(pos);
        this.player = player;
        playerInventory = new InvWrapper(inv);

        if(blockEntity != null){
            if(!player.level.isClientSide()) blockEntity.startOpen(player);
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h ->
                    addSlotBox(h, 0, 8, 18, 9, 18, 3, 18));
        }
        layoutPlayerInventorySlots(8,
                18 + //top
                3*18 + // 3 rows
                12 // player container separator
                );
    }

    @Override
    public boolean stillValid(Player playerEntity) {
        return stillValid(ContainerLevelAccess.create(
                blockEntity.getLevel(),
                blockEntity.getBlockPos()),
                playerEntity,
                NetheriteShulker.NETHERITE_SHULKER.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 27) {
                if (!this.moveItemStackTo(itemstack1, 27, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 27, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    // onClose startOpen from ShulkerBoxBlockEntity
    @Override
    public void removed(Player player) {
        blockEntity.stopOpen(player);
        super.removed(player);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

}
