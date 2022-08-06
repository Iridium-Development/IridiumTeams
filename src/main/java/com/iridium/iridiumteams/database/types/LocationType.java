package com.iridium.iridiumteams.database.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.SQLException;

public class LocationType extends StringType {

    private static final LocationType instance = new LocationType();

    public static LocationType getSingleton() {
        return instance;
    }

    protected LocationType() {
        super(SqlType.STRING, new Class<?>[] { Location.class });
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        String value = (String) super.sqlArgToJava(fieldType, sqlArg, columnPos);
        String[] params = value.split(",");
        World world = Bukkit.getWorld(params[0]);
        return new Location(world, Double.parseDouble(params[1]), Double.parseDouble(params[2]), Double.parseDouble(params[3]),
                Float.parseFloat(params[5]), Float.parseFloat(params[4]));
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) throws SQLException {
        Location location = (Location) object;
        return super.javaToSqlArg(fieldType, location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getPitch() +
                "," + location.getYaw());
    }
    
}
