package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase.NBTPrimitive {

    /** The byte value for the tag. */
    private byte data;

    NBTTagByte() {}

    public NBTTagByte(byte data) {
        this.data = data;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    @Override
    void write(DataOutput output) throws IOException {
        output.writeByte(this.data);
    }

    @Override
    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(8L);
        this.data = input.readByte();
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public byte getType() {
        return (byte) 1;
    }

    @Override
    public String toString() {
        return this.data + "b";
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public NBTBase copy() {
        return new NBTTagByte(this.data);
    }

    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagByte nbttagbyte = (NBTTagByte) other;
            return this.data == nbttagbyte.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.data;
    }

    @Override
    public long toLong() {
        return this.data;
    }

    @Override
    public int toInt() {
        return this.data;
    }

    @Override
    public short toShort() {
        return this.data;
    }

    @Override
    public byte toByte() {
        return this.data;
    }

    @Override
    public double toDouble() {
        return this.data;
    }

    @Override
    public float toFloat() {
        return this.data;
    }
}
