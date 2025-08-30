package net.minecraft.util;

public class MathHelper {

    /**
     * Returns the greatest integer less than or equal to the float argument
     */
    public static int floor_float(float p_76141_0_)
    {
        int i = (int)p_76141_0_;
        return p_76141_0_ < (float)i ? i - 1 : i;
    }

    /**
     * Returns the greatest integer less than or equal to the double argument
     */
    public static int floor_double(double p_76128_0_)
    {
        int i = (int)p_76128_0_;
        return p_76128_0_ < (double)i ? i - 1 : i;
    }

}
