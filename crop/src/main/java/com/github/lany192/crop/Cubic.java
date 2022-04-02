package com.github.lany192.crop;


public class Cubic implements Easing {
    public Cubic() {
    }

    public double easeOut(double time, double start, double end, double duration) {
        return end * ((time = time / duration - 1.0D) * time * time + 1.0D) + start;
    }

    public double easeIn(double time, double start, double end, double duration) {
        return end * (time /= duration) * time * time + start;
    }

    public double easeInOut(double time, double start, double end, double duration) {
        return (time /= duration / 2.0D) < 1.0D ? end / 2.0D * time * time * time + start : end / 2.0D * ((time -= 2.0D) * time * time + 2.0D) + start;
    }
}