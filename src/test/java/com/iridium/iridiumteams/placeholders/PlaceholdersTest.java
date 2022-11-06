package com.iridium.iridiumteams.placeholders;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import com.iridium.testplugin.managers.TeamManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaceholdersTest {

    private ServerMock serverMock;
    private Placeholders<TestTeam, User> placeholders;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
        placeholders = new Placeholders<>(TestPlugin.getInstance());
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }


    @Test
    public void Placeholders__getPlaceholders__ReturnsSize() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        List<Placeholder> placeholderList = placeholders.getPlaceholders(playerMock);

        int playerPlaceholders = TestPlugin.getInstance().getUserPlaceholderBuilder().getPlaceholders(Optional.empty()).size();
        int teamPlaceholders = TestPlugin.getInstance().getUserPlaceholderBuilder().getPlaceholders(Optional.empty()).size();

        assertEquals(playerPlaceholders + (teamPlaceholders * 42), placeholderList.size());
    }


    @Test
    public void Placeholders__getPlaceholders__ReturnsPlayerPlaceholders() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        List<Placeholder> placeholderList = placeholders.getPlaceholders(playerMock);
        Optional<Placeholder> placeholder = placeholderList.stream().filter(p -> p.getKey().equals("%player_name%")).findFirst();

        assertTrue(placeholder.isPresent());
        assertEquals(playerMock.getName(), placeholder.get().getValue());

    }


    @Test
    public void Placeholders__getPlaceholders__ReturnsPlayerTeamPlaceholders() {
        TestTeam testTeam = new TeamBuilder("My Team").build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        List<Placeholder> placeholderList = placeholders.getPlaceholders(playerMock);
        Optional<Placeholder> placeholder = placeholderList.stream().filter(p -> p.getKey().equals("%team_name%")).findFirst();

        assertTrue(placeholder.isPresent());
        assertEquals(testTeam.getName(), placeholder.get().getValue());
    }


    @Test
    public void Placeholders__getPlaceholders__ReturnsCurrentTeamPlaceholders() {
        TestTeam testTeam = new TeamBuilder("My Team").build();
        TeamManager.teamViaLocation = Optional.of(testTeam);
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        List<Placeholder> placeholderList = placeholders.getPlaceholders(playerMock);
        Optional<Placeholder> placeholder = placeholderList.stream().filter(p -> p.getKey().equals("%current_team_name%")).findFirst();

        assertTrue(placeholder.isPresent());
        assertEquals(testTeam.getName(), placeholder.get().getValue());
    }


    @Test
    public void Placeholders__getPlaceholders__ReturnsTopValueTeamPlaceholders() {
        TestTeam testTeam = new TeamBuilder("My Team").build();

        List<Placeholder> placeholderList = placeholders.getPlaceholders(null);
        Optional<Placeholder> value1 = placeholderList.stream().filter(p -> p.getKey().equals("%top_value_1_team_name%")).findFirst();
        Optional<Placeholder> experience1 = placeholderList.stream().filter(p -> p.getKey().equals("%top_experience_1_team_name%")).findFirst();
        Optional<Placeholder> value2 = placeholderList.stream().filter(p -> p.getKey().equals("%top_value_2_team_name%")).findFirst();
        Optional<Placeholder> experience2 = placeholderList.stream().filter(p -> p.getKey().equals("%top_experience_2_team_name%")).findFirst();

        assertTrue(value1.isPresent());
        assertTrue(experience1.isPresent());
        assertTrue(value2.isPresent());
        assertTrue(experience2.isPresent());
        assertEquals(testTeam.getName(), value1.get().getValue());
        assertEquals(testTeam.getName(), experience1.get().getValue());
        assertEquals("N/A", value2.get().getValue());
        assertEquals("N/A", experience2.get().getValue());
    }

}