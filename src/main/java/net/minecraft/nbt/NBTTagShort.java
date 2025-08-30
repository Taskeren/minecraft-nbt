package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase.NBTPrimitive {

    /** The short value for the tag. */
    private short data;

    public NBTTagShort() {}

    public NBTTagShort(short p_i45135_1_) {
        this.data = p_i45135_1_;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput output) throws IOException {
        output.writeShort(this.data);
    }

    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(16L);
        this.data = input.readShort();
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getType() {
        return (byte) 2;
    }

    public String toString() {
        return this.data + "s";
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy() {
        return new NBTTagShort(this.data);
    }

    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagShort nbttagshort = (NBTTagShort) other;
            return this.data == nbttagshort.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.data;
    }

    public long toLong() {
        return this.data;
    }

    public int toInt() {
        return this.data;
    }

    public short toShort() {
        return this.data;
    }

    public byte toByte() {
        return (byte) (this.data & 255);
    }

    public double toDouble() {
        return this.data;
    }

    public float toFloat() {
        return this.data;
    }
}
