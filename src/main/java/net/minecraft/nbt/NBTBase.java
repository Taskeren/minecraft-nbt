package net.minecraft.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {

    public static final String[] NBTTypes = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE",
        "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    abstract void write(DataOutput output) throws IOException;

    abstract void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException;

    public abstract String toString();

    /**
     * Gets the type byte for the tag.
     */
    public abstract byte getType();

    protected static @NotNull NBTBase createDefaultByType(@Range(from = 0, to = 11) byte id) {
        return switch (id) {
            case 0 -> new NBTTagEnd();
            case 1 -> new NBTTagByte();
            case 2 -> new NBTTagShort();
            case 3 -> new NBTTagInt();
            case 4 -> new NBTTagLong();
            case 5 -> new NBTTagFloat();
            case 6 -> new NBTTagDouble();
            case 7 -> new NBTTagByteArray();
            case 8 -> new NBTTagString();
            case 9 -> new NBTTagList();
            case 10 -> new NBTTagCompound();
            case 11 -> new NBTTagIntArray();
            default -> null;
        };
    }

    protected static @NotNull NBTBase createDefaultByTypeUnchecked(byte id) {
        if (id < 0 || id > 11) throw new IllegalArgumentException("Invalid type, must be between 0 and 11");
        return createDefaultByType(id);
    }

    /**
     * Creates a clone of the tag.
     */
    public abstract NBTBase copy();

    public boolean equals(Object other) {
        if (!(other instanceof NBTBase nbtbase)) {
            return false;
        } else {
            return this.getType() == nbtbase.getType();
        }
    }

    public int hashCode() {
        return this.getType();
    }

    protected String toStringValue() {
        return this.toString();
    }

    public abstract static class NBTPrimitive extends NBTBase {

        public abstract long toLong();

        public abstract int toInt();

        public abstract short toShort();

        public abstract byte toByte();

        public abstract double toDouble();

        public abstract float toFloat();
    }
}
