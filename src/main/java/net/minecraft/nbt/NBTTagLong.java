package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase.NBTPrimitive {

    /** The long value for the tag. */
    private long data;

    NBTTagLong() {}

    public NBTTagLong(long data) {
        this.data = data;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    @Override
    void write(DataOutput output) throws IOException {
        output.writeLong(this.data);
    }

    @Override
    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(64L);
        this.data = input.readLong();
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public byte getType() {
        return (byte) 4;
    }

    @Override
    public String toString() {
        return this.data + "L";
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public NBTBase copy() {
        return new NBTTagLong(this.data);
    }

    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagLong nbttaglong = (NBTTagLong) other;
            return this.data == nbttaglong.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ Long.hashCode(this.data);
    }

    @Override
    public long toLong() {
        return this.data;
    }

    @Override
    public int toInt() {
        return (int) (this.data);
    }

    @Override
    public short toShort() {
        return (short) ((int) (this.data & 65535L));
    }

    @Override
    public byte toByte() {
        return (byte) ((int) (this.data & 255L));
    }

    @Override
    public double toDouble() {
        return (double) this.data;
    }

    @Override
    public float toFloat() {
        return (float) this.data;
    }
}
