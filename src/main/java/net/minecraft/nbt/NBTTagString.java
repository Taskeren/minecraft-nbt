package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {

    /** The string value for the tag (cannot be empty). */
    private String data;

    public NBTTagString() {
        this.data = "";
    }

    public NBTTagString(String data) {
        this.data = data;

        if (data == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    @Override
    void write(DataOutput output) throws IOException {
        output.writeUTF(this.data);
    }

    @Override
    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        this.data = input.readUTF();
        NBTSizeTracker.readUTF(sizeTracker, data); // Forge: Correctly read String length including header.
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public byte getType() {
        return (byte) 8;
    }

    @Override
    public String toString() {
        return "\"" + this.data + "\"";
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public NBTBase copy() {
        return new NBTTagString(this.data);
    }

    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        } else {
            NBTTagString nbttagstring = (NBTTagString) other;
            return this.data == null && nbttagstring.data == null
                || this.data != null && this.data.equals(nbttagstring.data);
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }

    @Override
    public String toStringValue() {
        return this.data;
    }
}
