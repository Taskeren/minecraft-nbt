package net.minecraft.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.function.Supplier;

public abstract class NBTBase {

    public static final String[] NBTTypes = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE",
        "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };

    public enum Type {
        END(NBTTagEnd::new),
        BYTE(NBTTagByte::new),
        SHORT(NBTTagShort::new),
        INT(NBTTagInt::new),
        LONG(NBTTagLong::new),
        FLOAT(NBTTagFloat::new),
        DOUBLE(NBTTagDouble::new),
        BYTE_ARRAY(NBTTagByteArray::new),
        STRING(NBTTagString::new),
        LIST(NBTTagList::new),
        COMPOUND(NBTTagCompound::new),
        INT_ARRAY(NBTTagIntArray::new),
        ;

        private final Supplier<NBTBase> emptyConstructor;

        Type(Supplier<NBTBase> emptyConstructor) {
            this.emptyConstructor = emptyConstructor;
        }

        public byte getId() {
            return (byte) ordinal();
        }

        public NBTBase newInstance() {
            return emptyConstructor.get();
        }

        public static Type byId(int id) {
            if(id >= 0 && id < values().length) {
                return values()[id];
            } else {
                throw new IllegalArgumentException("Invalid id " + id);
            }
        }
    }

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
        return Type.byId(id).newInstance();
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
