package eu.chehowski.util;

public final class KahanAverage
{
    private long numElements;

    private double average;
    private double carry;

    public KahanAverage(final long numElements)
    {
        reset(numElements);
    }

    public void update(final float value)
    {
        final double f = (double)value / numElements;
        final double y = f - carry;
        final double t = average + y;

        carry = (t - average) - y;
        average = t;
    }

    public float get()
    {
        return (float)average;
    }

    public void reset()
    {
        reset(numElements);
    }

    public void reset(final long numElements)
    {
        this.numElements = numElements;

        this.average = 0.0;
        this.carry = 0.0;
    }
}
