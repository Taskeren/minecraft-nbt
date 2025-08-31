package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {

    /** The byte array stored in the tag. */
    private byte[] byteArray;

    NBTTagByteArray() {}

    public NBTTagByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    @Override
    void write(DataOutput output) throws IOException {
        output.writeInt(this.byteArray.length);
        output.write(this.byteArray);
    }

    @Override
    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(32); //Forge: Count the length as well
        int j = input.readInt();
        sizeTracker.accumulateSize(8L * j);
        this.byteArray = new byte[j];
        input.readFully(this.byteArray);
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public byte getType() {
        return (byte) 7;
    }

    @Override
    public String toString() {
        return "[" + this.byteArray.length + " bytes]";
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public NBTBase copy() {
        byte[] copiedArray = new byte[this.byteArray.length];
        System.arraycopy(this.byteArray, 0, copiedArray, 0, this.byteArray.length);
        return new NBTTagByteArray(copiedArray);
    }

    public boolean equals(Object other) {
        return super.equals(other) && Arrays.equals(this.byteArray, ((NBTTagByteArray) other).byteArray);
    }

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.byteArray);
    }

    public byte[] getByteArray() {
        return this.byteArray;
    }
}
