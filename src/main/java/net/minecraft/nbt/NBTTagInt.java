package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase.NBTPrimitive {

    /** The integer value for the tag. */
    private int data;

    NBTTagInt() {}

    public NBTTagInt(int data) {
        this.data = data;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput output) throws IOException {
        output.writeInt(this.data);
    }

    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(32L);
        this.data = input.readInt();
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getType() {
        return (byte) 3;
    }

    public String toString() {
        return "" + this.data;
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy() {
        return new NBTTagInt(this.data);
    }

    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagInt nbttagint = (NBTTagInt) other;
            return this.data == nbttagint.data;
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
        return (short) (this.data & 65535);
    }

    public byte toByte() {
        return (byte) (this.data & 255);
    }

    public double toDouble() {
        return this.data;
    }

    public float toFloat() {
        return (float) this.data;
    }
}
