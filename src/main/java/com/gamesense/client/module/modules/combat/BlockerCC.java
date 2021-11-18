package com.gamesense.client.module.modules.combat;

import net.minecraft.util.math.BlockPos;
import com.gamesense.client.GameSense;
import com.gamesense.client.module.Module;
import com.gamesense.api.util.misc.Timer;
import com.gamesense.api.util.world.BlockUtil;

@Module.Declaration(name = "BlockerCC", category = Category.Combat)
public class BlockerCC extends Module {


    BlockPos position;
    int time;
    BlockPos pos;
    int stages;
    int delay, pdelay,stage,jumpdelay,toggledelay;
    boolean jump;
    Timer timer = new Timer();

    @Override public void onEnable(){
        position = new BlockPos(mc.player.getPositionVector());
    }
    @Override public void onToggle(){

        time = 0;
        pos = null;
        stages = 0;

        pdelay = 0;
        stage = 1;
        toggledelay = 0;
        jumpdelay = 0;
        timer.reset();
        jump = false;
        Main.TICK_TIMER = 1;
        position = null;
        delay = 0;
    }

    @Override public void onTick() {
        if (stage == 1) {
            delay++;
            (mc.gameSettings.keyBindJump).setPressed(true);
            Main.TICK_TIMER = 30;
            if (delay >= 42) {
                stage = 2;
                delay = 0;
                Main.TICK_TIMER = 1;
                (mc.gameSettings.keyBindJump).setPressed(false);
            }
        }
        if (stage == 2){
            Main.TICK_TIMER = 1;
            if (mc.player.onGround) (mc.gameSettings.keyBindJump).setPressed(true);;
            BlockUtil.INSTANCE.placeBlock(position);
            pdelay++;
            if (pdelay >= 30){
                stage = 3;
                pdelay = 0;
                (mc.gameSettings.keyBindJump).setPressed(false);
                Main.TICK_TIMER = 1;

            }
        }
        if (stage == 3){
            toggledelay++;
            Main.TICK_TIMER = 30;
            (mc.gameSettings.keyBindJump).setPressed(true);
            if (toggledelay >= 25) {
                mc.player.motionY -= 0.4;
                Main.TICK_TIMER = 1;
                (mc.gameSettings.keyBindJump).setPressed(false);
                setToggled(false);
            }
        }
    }
}
