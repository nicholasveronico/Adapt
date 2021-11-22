package com.volmit.adapt.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class BukkitGson {
    public static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(World.class, (JsonSerializer<World>) (world, type, s) -> s.serialize(world.getName()))
        .registerTypeAdapter(World.class, (JsonDeserializer<World>) (j, type, d) -> Bukkit.getWorld(j.getAsString()))
        .registerTypeAdapter(BlockData.class, (JsonSerializer<BlockData>) (data, type, s) -> new JsonPrimitive(data.getAsString(true)))
        .registerTypeAdapter(BlockData.class, (JsonDeserializer<BlockData>) (j, type, d) -> Bukkit.createBlockData(j.getAsString()))
        .registerTypeAdapter(Location.class, (JsonSerializer<Location>) (data, type, s) -> {
            JsonArray a = new JsonArray();
            a.add(data.getWorld().getName());
            a.add(truncate(data.getX(), 1));
            a.add(truncate(data.getY(), 1));
            a.add(truncate(data.getZ(), 1));
            a.add((int)data.getYaw());
            a.add((int)data.getPitch());
            return a;
        })
        .registerTypeAdapter(Location.class, (JsonDeserializer<Location>) (j, type, d) -> {
            JsonArray a = j.getAsJsonArray();
            return new Location(Bukkit.getWorld(a.get(0).getAsString()), a.get(1).getAsDouble(), a.get(2).getAsDouble(), a.get(3).getAsDouble(), a.get(4).getAsFloat(), a.get(5).getAsFloat());
        })
        .registerTypeAdapter(Block.class, (JsonSerializer<Block>) (data, type, s) -> {
            JsonArray a = new JsonArray();
            a.add(data.getWorld().getName());
            a.add(data.getX());
            a.add(data.getY());
            a.add(data.getZ());
            return a;
        })
        .registerTypeAdapter(Block.class, (JsonDeserializer<Block>) (j, type, d) -> {
            JsonArray a = j.getAsJsonArray();
            return new Location(Bukkit.getWorld(a.get(0).getAsString()), a.get(1).getAsInt(), a.get(2).getAsInt(), a.get(3).getAsInt()).getBlock();
        })
        .registerTypeAdapter(BlockVector.class, (JsonSerializer<BlockVector>) (data, type, s) -> {
            JsonArray a = new JsonArray();
            a.add(data.getBlockX());
            a.add(data.getBlockY());
            a.add(data.getBlockZ());
            return a;
        })
        .registerTypeAdapter(BlockVector.class, (JsonDeserializer<BlockVector>) (j, type, d) -> {
            JsonArray a = j.getAsJsonArray();
            return new BlockVector(a.get(0).getAsInt(), a.get(1).getAsInt(), a.get(2).getAsInt());
        })
        .registerTypeAdapter(Vector.class, (JsonSerializer<Vector>) (data, type, s) -> {
            JsonArray a = new JsonArray();
            a.add(truncate(data.getX(), 1));
            a.add(truncate(data.getY(), 1));
            a.add(truncate(data.getZ(), 1));
            return a;
        })
        .registerTypeAdapter(Vector.class, (JsonDeserializer<Vector>) (j, type, d) -> {
            JsonArray a = j.getAsJsonArray();
            return new BlockVector(a.get(0).getAsDouble(), a.get(1).getAsDouble(), a.get(2).getAsDouble());
        })
        .create();

    private static double truncate(double d, int p) {
        if((int) d == d)
        {
            return d;
        }

        return Double.parseDouble(Form.f(d, p));
    }

    private static double truncate(float d, int p)
    {
        if((int) d == d)
        {
            return d;
        }

        return Float.parseFloat(Form.f(d, p));
    }
}
