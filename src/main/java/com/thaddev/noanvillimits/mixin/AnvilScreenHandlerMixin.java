package com.thaddev.noanvillimits.mixin;

import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.util.StringHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {
    @Inject(method = "sanitize", at = @At("HEAD"), cancellable = true)
    private static void sanitize(String text, CallbackInfoReturnable<String> cir) {
        //replace all instances of "\n" with the line feed character
        text = text.replace("\\n", "\n");

        String string = StringHelper.stripInvalidChars(text, true);
        if (string.length() <= 32767) {
            cir.setReturnValue(string);
            return;
        }
        cir.setReturnValue(null);
    }
}
