package com.idreesinc.celeste.utilities;
//From https://gamedev.stackexchange.com/a/162987

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedRandomBag<T extends Object> {

    private class Entry {
        double accumulatedWeight;
        T object;
    }

    public List<Entry> entries = new ArrayList<Entry>();
    private double accumulatedWeight;
    private Random rand = new Random();

    public void addEntry(T object, double weight) {
        accumulatedWeight += weight;
        Entry e = new Entry();
        e.object = object;
        e.accumulatedWeight = accumulatedWeight;
        entries.add(e);
    }

    public T getRandom() {
        double r = rand.nextDouble() * accumulatedWeight;
        for (Entry entry: entries) {
            if (entry.accumulatedWeight >= r) {
                return entry.object;
            }
        }
        // Should only happen when there are no entries
        return null;
    }
}