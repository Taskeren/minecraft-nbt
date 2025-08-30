package net.minecraft.nbt;

import net.minecraft.util.MathHelper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase.NBTPrimitive {

    /** The double value for the tag. */
    private double data;

    NBTTagDouble() {}

    public NBTTagDouble(double data) {
        this.data = data;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput output) throws IOException {
        output.writeDouble(this.data);
    }

    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(64L);
        this.data = input.readDouble();
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getType() {
        return (byte) 6;
    }

    public String toString() {
        return this.data + "d";
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy() {
        return new NBTTagDouble(this.data);
    }

    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagDouble nbttagdouble = (NBTTagDouble) other;
            return this.data == nbttagdouble.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ Double.hashCode(this.data);
    }

    public long toLong() {
        return (long) Math.floor(this.data);
    }

    public int toInt() {
        return MathHelper.floor_double(this.data);
    }

    public short toShort() {
        return (short) (MathHelper.floor_double(this.data) & 65535);
    }

    public byte toByte() {
        return (byte) (MathHelper.floor_double(this.data) & 255);
    }

    public double toDouble() {
        return this.data;
    }

    public float toFloat() {
        return (float) this.data;
    }
}
