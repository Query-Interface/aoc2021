package com.queryinterface.aoc;

import java.util.List;

public class Signal {
    List<String> inputs;
    List<String> outputs;

    public static Signal of(final String data) {
        var signal = new Signal();
        int separator = data.indexOf(" | ");
        signal.inputs = List.of(data.substring(0, separator).split(" "));
        signal.outputs = List.of(data.substring(separator+3).split(" "));
        return signal;
    }

    public List<String> getInputs() {
        return this.inputs;
    }

    public List<String> getOutputs() {
        return this.outputs;
    }
}
