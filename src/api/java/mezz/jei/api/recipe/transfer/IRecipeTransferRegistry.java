package mezz.jei.api.recipe.transfer;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeRegistry;
import mezz.jei.api.recipe.category.IRecipeCategory;

/**
 * Register recipe transfer handlers here to give JEI the information it needs to transfer recipes into the crafting area.
 * Get the instance from {@link IModRegistry#getRecipeTransferRegistry()}.
 *
 * To get registered recipe transfer handlers at runtime, see {@link IRecipeRegistry#getRecipeTransferHandler(Container, IRecipeCategory)}
 */
public interface IRecipeTransferRegistry {
	/**
	 * Basic method for adding a recipe transfer handler.
	 *
	 * @param containerClass     the class of the container that this recipe transfer handler is for
	 * @param recipeCategoryUid  the recipe categories that this container can use
	 * @param recipeSlotStart    the first slot for recipe inputs
	 * @param recipeSlotCount    the number of slots for recipe inputs
	 * @param inventorySlotStart the first slot of the available inventory (usually player inventory)
	 * @param inventorySlotCount the number of slots of the available inventory
	 */
	<C extends Container> void addRecipeTransferHandler(Class<C> containerClass, ResourceLocation recipeCategoryUid, int recipeSlotStart, int recipeSlotCount, int inventorySlotStart, int inventorySlotCount);

	/**
	 * Advanced method for adding a recipe transfer handler.
	 *
	 * Use this when recipe slots or inventory slots are spread out in different number ranges.
	 */
	<C extends Container> void addRecipeTransferHandler(IRecipeTransferInfo<C> recipeTransferInfo);

	/**
	 * Complete control over recipe transfer.
	 * Use this when the container has a non-standard inventory or crafting area.
	 */
	void addRecipeTransferHandler(IRecipeTransferHandler<?> recipeTransferHandler, ResourceLocation recipeCategoryUid);

	/**
	 * Add a universal handler that can handle any category of recipe.
	 * Useful for mods with recipe pattern encoding, for automated recipe systems.
	 */
	void addUniversalRecipeTransferHandler(IRecipeTransferHandler<?> recipeTransferHandler);
}
