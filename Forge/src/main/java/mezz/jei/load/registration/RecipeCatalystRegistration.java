package mezz.jei.load.registration;

import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.ingredients.RegisteredIngredients;
import mezz.jei.ingredients.TypedIngredient;
import net.minecraft.resources.ResourceLocation;

import com.google.common.collect.ImmutableListMultimap;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.core.collect.ListMultiMap;
import mezz.jei.util.ErrorUtil;

public class RecipeCatalystRegistration implements IRecipeCatalystRegistration {
	private final ListMultiMap<ResourceLocation, ITypedIngredient<?>> recipeCatalysts = new ListMultiMap<>();
	private final RegisteredIngredients registeredIngredients;

	public RecipeCatalystRegistration(RegisteredIngredients registeredIngredients) {
		this.registeredIngredients = registeredIngredients;
	}

	@Override
	public <T> void addRecipeCatalyst(IIngredientType<T> ingredientType, T ingredient, RecipeType<?>... recipeTypes) {
		ErrorUtil.checkNotEmpty(recipeTypes, "recipeTypes");
		ErrorUtil.checkNotNull(ingredientType, "ingredientType");
		ErrorUtil.checkNotNull(ingredient, "ingredient");

		for (RecipeType<?> recipeType : recipeTypes) {
			ErrorUtil.checkNotNull(recipeType, "recipeType");
			ITypedIngredient<T> typedIngredient = TypedIngredient.createTyped(this.registeredIngredients, ingredientType, ingredient)
				.orElseThrow(() -> new IllegalArgumentException("Recipe catalyst must not be empty"));
			this.recipeCatalysts.put(recipeType.getUid(), typedIngredient);
		}
	}

	@SuppressWarnings("removal")
	@Override
	public <T> void addRecipeCatalyst(IIngredientType<T> ingredientType, T catalystIngredient, ResourceLocation... recipeCategoryUids) {
		ErrorUtil.checkNotNull(ingredientType, "ingredientType");
		ErrorUtil.checkNotNull(catalystIngredient, "catalystIngredient");
		ErrorUtil.checkNotEmpty(recipeCategoryUids, "recipeCategoryUids");

		for (ResourceLocation recipeCategoryUid : recipeCategoryUids) {
			ErrorUtil.checkNotNull(recipeCategoryUid, "recipeCategoryUid");
			ITypedIngredient<T> typedIngredient = TypedIngredient.createTyped(this.registeredIngredients, ingredientType, catalystIngredient)
				.orElseThrow(() -> new IllegalArgumentException("Recipe catalyst must not be empty"));
			this.recipeCatalysts.put(recipeCategoryUid, typedIngredient);
		}
	}

	public ImmutableListMultimap<ResourceLocation, ITypedIngredient<?>> getRecipeCatalysts() {
		return recipeCatalysts.toImmutable();
	}
}
