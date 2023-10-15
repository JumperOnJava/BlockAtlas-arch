package io.github.jumperonjava.blockatlas.mixin;

import io.github.jumperonjava.blockatlas.BlockAtlasInit;
import io.github.jumperonjava.blockatlas.gui.backport.ButtonWidgetBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Supplier;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @ModifyArg(method = "initWidgets",index = 2,at = @At(value = "INVOKE",ordinal = 8,target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V"))
    int changeexitbuttonsize(int i) {
        if(!this.client.isInSingleplayer())
        return 98;
        else return i;
    }
    @Inject(method = "initWidgets", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE",ordinal = 8,shift = At.Shift.BEFORE,target = "Lnet/minecraft/client/gui/screen/GameMenuScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;"))
    void addServersButton(CallbackInfo ci){
        if(!this.client.isInSingleplayer())
        addDrawableChild(new ButtonWidgetBuilder(Text.translatable("blockatlas.switch"),(b)-> {
            //new MultiplayerScreen(new TitleScreen());
            client.setScreen(new MultiplayerScreen(this));
            }).position(this.width / 2 + 4, this.height / 4 + 96 + -16).width(98).build());
    }
}
