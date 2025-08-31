package net.minecraft.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NBTTagList extends NBTBase {

    /** The array list containing the tags encapsulated in this list. */
    private List<NBTBase> tagList = new ArrayList<>();
    /** The type byte for the tags in the list - they must all be of the same type. */
    private byte tagType = 0;

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    @Override
    void write(DataOutput output) throws IOException {
        if (!this.tagList.isEmpty()) {
            this.tagType = this.tagList.getFirst().getType();
        } else {
            this.tagType = 0;
        }

        output.writeByte(this.tagType);
        output.writeInt(this.tagList.size());

        for (NBTBase nbtBase : this.tagList) {
            nbtBase.write(output);
        }
    }

    @Override
    void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        if (depth > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        } else {
            sizeTracker.accumulateSize(8L);
            this.tagType = input.readByte();
            sizeTracker.accumulateSize(32); //Forge: Count the length as well
            int j = input.readInt();
            this.tagList = new ArrayList<>();

            for (int k = 0; k < j; ++k) {
                sizeTracker.accumulateSize(32); //Forge: 4 extra bytes for the object allocation.
                NBTBase nbtbase = NBTBase.Type.byId(this.tagType).newInstance();
                nbtbase.read(input, depth + 1, sizeTracker);
                this.tagList.add(nbtbase);
            }
        }
    }

    /**
     * Gets the type byte for the tag.
     */
    @Override
    public byte getType() {
        return (byte) 9;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        int i = 0;

        for (Iterator<NBTBase> iterator = this.tagList.iterator(); iterator.hasNext(); ++i) {
            NBTBase nbtbase = iterator.next();
            s.append(i).append(':').append(nbtbase).append(',');
        }

        return s + "]";
    }

    /**
     * Adds the provided tag to the end of the list. There is no check to verify this tag is of the same type as any
     * previous tag.
     */
    public void appendTag(NBTBase tag) {
        if (this.tagType == 0) {
            this.tagType = tag.getType();
        } else if (this.tagType != tag.getType()) {
            System.err.println("WARNING: Adding mismatching tag types to tag list");
            return;
        }

        this.tagList.add(tag);
    }

    public void setTag(int i, NBTBase tag) {
        if (i >= 0 && i < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = tag.getType();
            } else if (this.tagType != tag.getType()) {
                System.err.println("WARNING: Adding mismatching tag types to tag list");
                return;
            }

            this.tagList.set(i, tag);
        } else {
            System.err.println("WARNING: index out of bounds to set tag in tag list");
        }
    }

    /**
     * Removes a tag at the given index.
     */
    public NBTBase removeTag(int i) {
        return this.tagList.remove(i);
    }

    private NBTBase getAtOrDefault(int i, Type type) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbt = this.tagList.get(i);
            if (nbt.getType() == type.getId()) {
                return nbt;
            }
        }
        return type.newInstance();
    }

    public @Nullable NBTBase getTag(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            return this.tagList.get(i);
        }
        return null;
    }

    public byte getByte(int i) {
        return ((NBTTagByte) getAtOrDefault(i, Type.BYTE)).toByte();
    }

    public short getShort(int i) {
        return ((NBTTagShort) getAtOrDefault(i, Type.SHORT)).toShort();
    }

    public int getInteger(int i) {
        return ((NBTTagInt) getAtOrDefault(i, Type.INT)).toInt();
    }

    public long getLong(int i) {
        return ((NBTTagLong) getAtOrDefault(i, Type.LONG)).toLong();
    }

    public float getFloat(int i) {
        return ((NBTTagFloat) getAtOrDefault(i, Type.FLOAT)).toFloat();
    }

    public double getDouble(int i) {
        return ((NBTTagDouble) getAtOrDefault(i, Type.DOUBLE)).toDouble();
    }

    public byte @NotNull [] getByteArray(int i) {
        return ((NBTTagByteArray) getAtOrDefault(i, Type.BYTE_ARRAY)).getByteArray();
    }

    public @NotNull String getString(int i) {
        return ((NBTTagString) getAtOrDefault(i, Type.STRING)).toStringValue();
    }

    public @NotNull NBTTagList getList(int i) {
        return (NBTTagList) getAtOrDefault(i, Type.LIST);
    }

    public @NotNull NBTTagCompound getCompound(int i) {
        return (NBTTagCompound) getAtOrDefault(i, Type.COMPOUND);
    }

    public int @NotNull [] getIntArray(int i) {
        return ((NBTTagIntArray) getAtOrDefault(i, Type.INT_ARRAY)).getIntArray();
    }

    /**
     * Returns the number of tags in the list.
     */
    public int tagCount() {
        return this.tagList.size();
    }

    /**
     * Creates a clone of the tag.
     */
    @Override
    public NBTBase copy() {
        NBTTagList nbttaglist = new NBTTagList();
        nbttaglist.tagType = this.tagType;

        for (NBTBase nbtbase : this.tagList) {
            NBTBase copy = nbtbase.copy();
            nbttaglist.tagList.add(copy);
        }

        return nbttaglist;
    }

    public boolean equals(Object other) {
        if (super.equals(other)) {
            NBTTagList nbttaglist = (NBTTagList) other;

            if (this.tagType == nbttaglist.tagType) {
                return this.tagList.equals(nbttaglist.tagList);
            }
        }

        return false;
    }

    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }

    public int getTagType() {
        return this.tagType;
    }
}
