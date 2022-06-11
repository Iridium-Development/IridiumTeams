package com.iridium.iridiumteams.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.iridium.iridiumteams.commands.Command;
import com.iridium.testplugin.TestPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandManagerTest {

    private ServerMock serverMock;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }


    @Test
    public void commandManagerCommands__HasNoArgsConstructor() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Command<?, ?> command : TestPlugin.getInstance().getCommandManager().getCommands()) {
            Optional<Constructor<?>> constructorOptional = Arrays.stream(command.getClass().getConstructors()).filter(constructor -> constructor.getParameterCount() == 0).findFirst();
            assertTrue(constructorOptional.isPresent(), command.getClass().getSimpleName() + " Does not have default constructor");
            constructorOptional.get().newInstance();
        }
    }
}