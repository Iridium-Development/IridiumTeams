package com.iridium.iridiumteams;

import com.cryptomorin.xseries.XSound;
import com.iridium.iridiumcore.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class Reward {

    public Item item;
    public List<String> commands;
    public double money;
    public Map<String, Double> bankRewards;
    public int experience;
    public int teamExperience;
    public XSound sound;

}
