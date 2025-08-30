package net.minecraft.nbt.test;

import net.minecraft.nbt.JsonToNBT;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestJsonToNBT {

    @Test
    public void testJsonToNBT() throws Exception {
        String s;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(TestJsonToNBT.class.getClassLoader()
            .getResourceAsStream("nbt.json"))))) {
            s = br.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(JsonToNBT.func_150315_a(s));
    }

}
