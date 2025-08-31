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
    @Override
    void write(DataOutput output) throws IOException {
        output.writeFloat(this.data);
    }

    @Override
    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(32L);
        this.data = input.readFloat();
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public byte getType() {
        return (byte) 5;
    }

    @Override
    public String toString() {
        return this.data + "f";
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
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

    @Override
    public long toLong() {
        return (long) this.data;
    }

    @Override
    public int toInt() {
        return MathHelper.floor_float(this.data);
    }

    @Override
    public short toShort() {
        return (short) (MathHelper.floor_float(this.data) & 65535);
    }

    @Override
    public byte toByte() {
        return (byte) (MathHelper.floor_float(this.data) & 255);
    }

    @Override
    public double toDouble() {
        return (double) this.data;
    }

    @Override
    public float toFloat() {
        return this.data;
    }
}
