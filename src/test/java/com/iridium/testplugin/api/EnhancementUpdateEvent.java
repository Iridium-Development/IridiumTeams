package com.iridium.testplugin.api;

import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EnhancementUpdateEvent implements Listener {

    public static boolean called = false;

    @EventHandler
    public void onEnhancementUpdate(com.iridium.iridiumteams.api.EnhancementUpdateEvent<TestTeam, User> event){
        called = true;
    }

}
