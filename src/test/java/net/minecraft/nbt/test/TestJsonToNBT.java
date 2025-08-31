package net.minecraft.nbt.test;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestJsonToNBT {

    private static final Logger log = LoggerFactory.getLogger(TestJsonToNBT.class);
    private static String nbtTxt;

    @BeforeAll
    public static void getNBTText() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(TestJsonToNBT.class.getClassLoader()
            .getResourceAsStream("nbt.txt"))))) {
            nbtTxt = br.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testJsonToNBT() {
        log.info("Raw text: {}", nbtTxt);
        NBTBase nbt = Assertions.assertDoesNotThrow(
            () -> JsonToNBT.func_150315_a(nbtTxt),
            "Failed to deserialize the nbt");
        log.info("Deserialized: {}", nbt);

        Assertions.assertInstanceOf(NBTTagCompound.class, nbt, "The deserialized object was not NBTTagCompound");
        NBTTagCompound compound = (NBTTagCompound) nbt;

        Assertions.assertTrue(compound.hasKey("map"), "'map' was not found in the deserialized nbt; " + compound);
        Assertions.assertInstanceOf(
            NBTTagCompound.class,
            compound.getCompoundTag("map"),
            "'map' was not NBTTagCompound in the deserialized nbt");

        NBTTagCompound map = compound.getCompoundTag("map");

        NBTTagList list = compound.getTagList("list", NBTBase.Type.INT.getId());
        Assertions.assertInstanceOf(NBTTagList.class, list);
        for (int i = 0; i < list.tagCount(); i++) {
            Assertions.assertEquals(i + 1, list.getInteger(i));
        }

        Assertions.assertEquals(
            "I'm a String!",
            map.getString("string"),
            "'string' was not equals to the proper value in the 'map' object");
        Assertions.assertTrue(map.getBoolean("bool"), "'bool' was not equals to the proper value in the 'map' object");
        Assertions.assertEquals(
            3.0F,
            map.getFloat("float"),
            "'float' was not equals to the proper value in the 'map' object");
        Assertions.assertEquals(
            1.5,
            map.getDouble("double"),
            "'double' was not equals to the proper value in the 'map' object");
    }

    @Test
    public void testNBTToJson() {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagCompound map = new NBTTagCompound();
        compound.setTag("map", map);

        NBTTagIntArray list = new NBTTagIntArray(IntStream.range(1, 6).toArray());
        map.setTag("list", list);
        map.setString("string", "I'm a String!");
        map.setBoolean("bool", true);
        map.setFloat("float", 3.0F);
        map.setDouble("double", 1.5);

        String serialized = compound.toString();
        log.info("Serialized: {}", serialized);
        Assertions.assertDoesNotThrow(() -> JsonToNBT.func_150315_a(serialized), "Failed to re-deserialize the nbt");
    }

}
