package net.minecraft.nbt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NBTTagCompound extends NBTBase {

    private static final Logger logger = LoggerFactory.getLogger(NBTTagCompound.class);
    /** The key-value pairs for the tag. Each key is a UTF string, each value is a tag. */
    private final Map<String, NBTBase> tagMap = new HashMap<>();

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput output) throws IOException {
        for (String s : this.tagMap.keySet()) {
            NBTBase nbtbase = this.tagMap.get(s);
            write(s, nbtbase, output);
        }

        output.writeByte(0);
    }

    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        if (depth > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        } else {
            this.tagMap.clear();
            byte tagType;

            while ((tagType = readByte(input, sizeTracker)) != 0) {
                String tagName = readUtf(input, sizeTracker);
                NBTSizeTracker.readUTF(sizeTracker, tagName); // Forge: Correctly read String length including header.
                NBTBase tag = read(tagType, tagName, input, depth + 1, sizeTracker);
                this.tagMap.put(tagName, tag);
            }
        }
    }

    public Set<String> keySet() {
        return this.tagMap.keySet();
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getType() {
        return (byte) 10;
    }

    /**
     * Stores the given tag into the map with the given string key. This is mostly used to store tag lists.
     */
    public void setTag(String key, NBTBase value) {
        this.tagMap.put(key, value);
    }

    /**
     * Stores a new NBTTagByte with the given byte value into the map with the given string key.
     */
    public void setByte(String key, byte value) {
        this.tagMap.put(key, new NBTTagByte(value));
    }

    /**
     * Stores a new NBTTagShort with the given short value into the map with the given string key.
     */
    public void setShort(String key, short value) {
        this.tagMap.put(key, new NBTTagShort(value));
    }

    /**
     * Stores a new NBTTagInt with the given integer value into the map with the given string key.
     */
    public void setInteger(String key, int value) {
        this.tagMap.put(key, new NBTTagInt(value));
    }

    /**
     * Stores a new NBTTagLong with the given long value into the map with the given string key.
     */
    public void setLong(String key, long value) {
        this.tagMap.put(key, new NBTTagLong(value));
    }

    /**
     * Stores a new NBTTagFloat with the given float value into the map with the given string key.
     */
    public void setFloat(String key, float value) {
        this.tagMap.put(key, new NBTTagFloat(value));
    }

    /**
     * Stores a new NBTTagDouble with the given double value into the map with the given string key.
     */
    public void setDouble(String key, double value) {
        this.tagMap.put(key, new NBTTagDouble(value));
    }

    /**
     * Stores a new NBTTagString with the given string value into the map with the given string key.
     */
    public void setString(String key, String value) {
        this.tagMap.put(key, new NBTTagString(value));
    }

    /**
     * Stores a new NBTTagByteArray with the given array as data into the map with the given string key.
     */
    public void setByteArray(String key, byte[] value) {
        this.tagMap.put(key, new NBTTagByteArray(value));
    }

    /**
     * Stores a new NBTTagIntArray with the given array as data into the map with the given string key.
     */
    public void setIntArray(String key, int[] value) {
        this.tagMap.put(key, new NBTTagIntArray(value));
    }

    /**
     * Stores the given boolean value as a NBTTagByte, storing 1 for true and 0 for false, using the given string key.
     */
    public void setBoolean(String key, boolean value) {
        this.setByte(key, (byte) (value ? 1 : 0));
    }

    /**
     * gets a generic tag with the specified name
     */
    public NBTBase getTag(String key) {
        return (NBTBase) this.tagMap.get(key);
    }

    public byte getTagType(String key) {
        NBTBase nbtbase = this.tagMap.get(key);
        return nbtbase != null ? nbtbase.getType() : 0;
    }

    /**
     * Returns whether the given string has been previously stored as a key in the map.
     */
    public boolean hasKey(String key) {
        return this.tagMap.containsKey(key);
    }

    public boolean hasKey(String key, int type) {
        byte b0 = this.getTagType(key);
        return b0 == type || (type == 99 && (b0 == 1 || b0 == 2 || b0 == 3 || b0 == 4 || b0 == 5 || b0 == 6));
    }

    /**
     * Retrieves a byte value using the specified key, or 0 if no such key was stored.
     */
    public byte getByte(String key) {
        try {
            return !this.tagMap.containsKey(key) ? 0 : ((NBTBase.NBTPrimitive) this.tagMap.get(key)).toByte();
        } catch (ClassCastException classcastexception) {
            return (byte) 0;
        }
    }

    /**
     * Retrieves a short value using the specified key, or 0 if no such key was stored.
     */
    public short getShort(String key) {
        try {
            return !this.tagMap.containsKey(key) ? 0 : ((NBTBase.NBTPrimitive) this.tagMap.get(key)).toShort();
        } catch (ClassCastException classcastexception) {
            return (short) 0;
        }
    }

    /**
     * Retrieves an integer value using the specified key, or 0 if no such key was stored.
     */
    public int getInteger(String key) {
        try {
            return !this.tagMap.containsKey(key) ? 0 : ((NBTBase.NBTPrimitive) this.tagMap.get(key)).toInt();
        } catch (ClassCastException classcastexception) {
            return 0;
        }
    }

    /**
     * Retrieves a long value using the specified key, or 0 if no such key was stored.
     */
    public long getLong(String key) {
        try {
            return !this.tagMap.containsKey(key) ? 0L : ((NBTBase.NBTPrimitive) this.tagMap.get(key)).toLong();
        } catch (ClassCastException classcastexception) {
            return 0L;
        }
    }

    /**
     * Retrieves a float value using the specified key, or 0 if no such key was stored.
     */
    public float getFloat(String key) {
        try {
            return !this.tagMap.containsKey(key) ? 0.0F : ((NBTBase.NBTPrimitive) this.tagMap.get(key)).toFloat();
        } catch (ClassCastException classcastexception) {
            return 0.0F;
        }
    }

    /**
     * Retrieves a double value using the specified key, or 0 if no such key was stored.
     */
    public double getDouble(String key) {
        try {
            return !this.tagMap.containsKey(key) ? 0.0D : ((NBTBase.NBTPrimitive) this.tagMap.get(key)).toDouble();
        } catch (ClassCastException classcastexception) {
            return 0.0D;
        }
    }

    /**
     * Retrieves a string value using the specified key, or an empty string if no such key was stored.
     */
    public String getString(String key) {
        try {
            return !this.tagMap.containsKey(key) ? "" : ((NBTBase) this.tagMap.get(key)).toStringValue();
        } catch (ClassCastException classcastexception) {
            return "";
        }
    }

    /**
     * Retrieves a byte array using the specified key, or a zero-length array if no such key was stored.
     */
    public byte[] getByteArray(String key) {
        try {
            return !this.tagMap.containsKey(key)
                ? new byte[0]
                : ((NBTTagByteArray) this.tagMap.get(key)).getByteArray();
        } catch (ClassCastException classcastexception) {
            throw new RuntimeException(classcastexception);
        }
    }

    /**
     * Retrieves an int array using the specified key, or a zero-length array if no such key was stored.
     */
    public int[] getIntArray(String key) {
        try {
            return !this.tagMap.containsKey(key) ? new int[0] : ((NBTTagIntArray) this.tagMap.get(key)).getIntArray();
        } catch (ClassCastException classcastexception) {
            throw new RuntimeException(classcastexception);
        }
    }

    /**
     * Retrieves a NBTTagCompound subtag matching the specified key, or a new empty NBTTagCompound if no such key was
     * stored.
     */
    public NBTTagCompound getCompoundTag(String key) {
        try {
            return !this.tagMap.containsKey(key) ? new NBTTagCompound() : (NBTTagCompound) this.tagMap.get(key);
        } catch (ClassCastException classcastexception) {
            throw new RuntimeException(classcastexception);
        }
    }

    /**
     * Gets the NBTTagList object with the given name. Args: name, NBTBase type
     */
    public NBTTagList getTagList(String key, int type) {
        try {
            if (this.getTagType(key) != 9) {
                return new NBTTagList();
            } else {
                NBTTagList nbttaglist = (NBTTagList) this.tagMap.get(key);
                return nbttaglist.tagCount() > 0 && nbttaglist.getTagType() != type ? new NBTTagList() : nbttaglist;
            }
        } catch (ClassCastException classcastexception) {
            throw new RuntimeException(classcastexception);
        }
    }

    /**
     * Retrieves a boolean value using the specified key, or false if no such key was stored. This uses the getByte
     * method.
     */
    public boolean getBoolean(String key) {
        return this.getByte(key) != 0;
    }

    /**
     * Remove the specified tag.
     */
    public void removeTag(String key) {
        this.tagMap.remove(key);
    }

    public String toString() {
        String s = "{";
        String s1;

        for (Iterator<String> iterator = this.tagMap.keySet().iterator(); iterator.hasNext(); s = s
            + s1
            + ':'
            + this.tagMap.get(s1)
            + ',') {
            s1 = iterator.next();
        }

        return s + "}";
    }

    /**
     * Return whether this compound has no tags.
     */
    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        for (String s : this.tagMap.keySet()) {
            nbtTagCompound.setTag(s, this.tagMap.get(s).copy());
        }
        return nbtTagCompound;
    }

    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagCompound nbttagcompound = (NBTTagCompound) other;
            return this.tagMap.entrySet().equals(nbttagcompound.tagMap.entrySet());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.tagMap.hashCode();
    }

    private static void write(String name, NBTBase data, DataOutput output) throws IOException {
        output.writeByte(data.getType());

        if (data.getType() != 0) {
            output.writeUTF(name);
            data.write(output);
        }
    }

    private static byte readByte(DataInput input, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.accumulateSize(8);
        return input.readByte();
    }

    private static String readUtf(DataInput input, NBTSizeTracker sizeTracker) throws IOException {
        return input.readUTF();
    }

    static NBTBase read(byte type, String key, DataInput input, int depth, NBTSizeTracker sizeTracker) {
        sizeTracker.accumulateSize(32); //Forge: 4 extra bytes for the object allocation.
        NBTBase nbtbase = NBTBase.createDefaultByTypeUnchecked(type);

        try {
            nbtbase.read(input, depth, sizeTracker);
            return nbtbase;
        } catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
        }
    }
}
