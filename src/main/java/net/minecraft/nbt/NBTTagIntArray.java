package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {

    /** The array of saved integers */
    private int[] intArray;

    NBTTagIntArray() {}

    public NBTTagIntArray(int[] intArray) {
        this.intArray = intArray;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    @Override
    void write(DataOutput output) throws IOException {
        output.writeInt(this.intArray.length);

        for (int j : this.intArray) {
            output.writeInt(j);
        }
    }

    @Override
    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(32); //Forge: Count the length as well
        int j = input.readInt();
        sizeTracker.accumulateSize(32L * j);
        this.intArray = new int[j];

        for (int k = 0; k < j; ++k) {
            this.intArray[k] = input.readInt();
        }
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public byte getType() {
        return (byte) 11;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        int[] intArray = this.intArray;

        for (int k : intArray) {
            s.append(k).append(",");
        }

        return s + "]";
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public NBTBase copy() {
        int[] copiedArray = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, copiedArray, 0, this.intArray.length);
        return new NBTTagIntArray(copiedArray);
    }

    public boolean equals(Object other) {
        return super.equals(other) && Arrays.equals(this.intArray, ((NBTTagIntArray) other).intArray);
    }

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }

    public int[] getIntArray() {
        return this.intArray;
    }
}
