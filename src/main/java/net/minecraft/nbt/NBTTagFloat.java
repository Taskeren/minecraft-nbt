package net.minecraft.nbt;

import net.minecraft.util.MathHelper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase.NBTPrimitive {

    /** The float value for the tag. */
    private float data;

    NBTTagFloat() {}

    public NBTTagFloat(float data) {
        this.data = data;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput output) throws IOException {
        output.writeFloat(this.data);
    }

    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(32L);
        this.data = input.readFloat();
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getType() {
        return (byte) 5;
    }

    public String toString() {
        return this.data + "f";
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy() {
        return new NBTTagFloat(this.data);
    }

    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagFloat nbttagfloat = (NBTTagFloat) other;
            return this.data == nbttagfloat.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }

    public long toLong() {
        return (long) this.data;
    }

    public int toInt() {
        return MathHelper.floor_float(this.data);
    }

    public short toShort() {
        return (short) (MathHelper.floor_float(this.data) & 65535);
    }

    public byte toByte() {
        return (byte) (MathHelper.floor_float(this.data) & 255);
    }

    public double toDouble() {
        return (double) this.data;
    }

    public float toFloat() {
        return this.data;
    }
}
