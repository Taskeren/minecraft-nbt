package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools {

    /**
     * Load the gzipped compound from the InputStream.
     */
    public static NBTTagCompound readCompressed(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(
            inputStream)))) {
            return read(dataInputStream, NBTSizeTracker.UNLIMITED);
        }
    }

    /**
     * Write the compound, gzipped, to the OutputStream.
     */
    public static void writeCompressed(NBTTagCompound nbtTagCompound, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(
            outputStream)))) {
            write(nbtTagCompound, dataoutputstream);
        }
    }

    public static NBTTagCompound read(byte[] bytes, NBTSizeTracker nbtSizeTracker) throws IOException {
        try (DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(
            bytes))))) {
            return read(datainputstream, nbtSizeTracker);
        }
    }

    public static byte[] compress(NBTTagCompound nbtTagCompound) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(byteArrayOutputStream))) {
            write(nbtTagCompound, dataOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void safeWrite(NBTTagCompound nbtTagCompound, File file) throws IOException {
        File tempFile = new File(file.getAbsolutePath() + "_tmp");

        if (tempFile.exists()) {
            tempFile.delete();
        }

        write(nbtTagCompound, tempFile);

        if (file.exists()) {
            file.delete();
        }

        if (file.exists()) {
            throw new IOException("Failed to delete " + file);
        } else {
            tempFile.renameTo(file);
        }
    }

    /**
     * Reads from a CompressedStream.
     */
    public static NBTTagCompound read(DataInputStream dataInputStream) throws IOException {
        return read(dataInputStream, NBTSizeTracker.UNLIMITED);
    }

    public static NBTTagCompound read(DataInput dataInput, NBTSizeTracker nbtSizeTracker) throws IOException {
        NBTBase nbtbase = read(dataInput, 0, nbtSizeTracker);

        if (nbtbase instanceof NBTTagCompound) {
            return (NBTTagCompound) nbtbase;
        } else {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    private static void write(NBTBase nbtBase, DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(nbtBase.getType());

        if (nbtBase.getType() != 0) {
            dataOutput.writeUTF("");
            nbtBase.write(dataOutput);
        }
    }

    private static NBTBase read(DataInput dataInput, int i, NBTSizeTracker nbtSizeTracker) throws IOException {
        byte b = dataInput.readByte();
        nbtSizeTracker.accumulateSize(8); // Forge: Count everything!

        if (b == 0) {
            return new NBTTagEnd();
        } else {
            NBTSizeTracker.readUTF(nbtSizeTracker, dataInput.readUTF()); //Forge: Count this string.
            return NBTTagCompound.read(b, "", dataInput, i, nbtSizeTracker);
        }
    }

    public static void write(NBTTagCompound nbtTagCompound, File file) throws IOException {
        try (DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file))) {
            write(nbtTagCompound, dataoutputstream);
        }
    }

    public static NBTTagCompound read(File file) throws IOException {
        return read(file, NBTSizeTracker.UNLIMITED);
    }

    public static NBTTagCompound read(File file, NBTSizeTracker nbtSizeTracker) throws IOException {
        if (!file.exists()) {
            return null;
        } else {
            try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
                return read(dataInputStream, nbtSizeTracker);
            }
        }
    }
}
