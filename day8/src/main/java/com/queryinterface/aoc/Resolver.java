package com.queryinterface.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Resolver {
    
    private Signal signal;
    private char mappingA;
    private char mappingB;
    private char mappingC;
    private char mappingD;
    private char mappingE;
    private char mappingF;
    private char mappingG;

    public Resolver(Signal signal) {
        this.signal =signal;
        resolve();
    }

    public void resolve() {
        var possibleMappingCF = resolveCF();
        mappingA = resolveA(possibleMappingCF);
        var possibleMappingD = resolvePossibleD(possibleMappingCF);
        mappingD = resolveD(possibleMappingD, mappingA, possibleMappingCF);
        mappingB = resolveB(mappingD, possibleMappingCF);
        mappingG = resolveG(mappingD, mappingB, mappingA, possibleMappingCF);
        mappingF = resolveF(mappingA, mappingB, mappingD, mappingG);
        mappingC = resolveC(possibleMappingCF, mappingF);
        mappingE = resolveE(mappingA, mappingB, mappingC, mappingD, mappingF, mappingG);

    }

    public long decode() {
        StringBuilder result = new StringBuilder();
        for (var value : signal.outputs) {
            StringBuilder builder = new StringBuilder();
            for (char c : value.toCharArray()) {
                if (c == mappingA) {
                    builder.append('a');
                }
                else if (c == mappingB) {
                    builder.append('b');;
                }
                else if (c == mappingC) {
                    builder.append('c');;
                }
                else if (c == mappingD) {
                    builder.append('d');;
                }
                else if (c ==  mappingE) {
                    builder.append('e');;
                }
                else if (c == mappingF) {
                    builder.append('f');;
                }
                else if (c == mappingG) {
                    builder.append('g');
                }
            }
            var d = Display.of(builder.toString());
            result.append(d.getValue());
        }
        return Long.parseLong(result.toString());
    }

    private char resolveE(char mappingA, char mappingB, char mappingC, char mappingD, char mappingF, char mappingG) {
        var result = signal.getInputs().stream().filter(s -> s.length() == 7).findFirst().get();
        Set<Character> toRemove = new HashSet<>(List.of(mappingA, mappingB, mappingC, mappingD, mappingF, mappingG));
        for (char c : result.toCharArray()) {
            if (!toRemove.contains(c)) {
                return c;
            }
        }
        return '0';
    }

    private char resolveC(char[] possibleMappingCF, char mappingF) {
        if (possibleMappingCF[0] == mappingF) {
            return possibleMappingCF[1];
        }
        return possibleMappingCF[0];
    }

    private char resolveF(char mappingA, char mappingB, char mappingD, char mappingG) {
        Set<Character> toRemove = new HashSet<>(List.of(mappingA, mappingB, mappingD, mappingG));
        var result = signal.getInputs().stream().filter(s -> s.length() == 5).map(s -> {
            List<Character> keep = new ArrayList<>();
            var characters = s.toCharArray();
            for (Character character : characters) {
                if (!toRemove.contains(character)) {
                    keep.add(character);
                }
            }
            return keep;
        }).filter(l -> l.size() == 1).findFirst().get().get(0);
        return result;
    }

    private char resolveG(char mappingD, char mappingB, char mappingA, char[] possibleMappingCF) {
        Set<Character> toRemove = new HashSet<>(List.of(mappingA, mappingB, mappingD, possibleMappingCF[0], possibleMappingCF[1]));
        var result = signal.getInputs().stream().filter(s -> s.length() == 5).map(s -> {
            List<Character> keep = new ArrayList<>();
            var characters = s.toCharArray();
            for (Character character : characters) {
                if (!toRemove.contains(character)) {
                    keep.add(character);
                }
            }
            return keep;
        }).toList();
        return result.get(0).stream().filter(result.get(1)::contains).filter(result.get(2)::contains).findFirst().get();
    }

    private char resolveB(char mappingD, char[] possibleMappingCF) {
        var result = signal.getInputs().stream().filter(s -> s.length() == 4).findFirst().get().toCharArray();
        var toRemove = List.of(mappingD, possibleMappingCF[0], possibleMappingCF[1]);
        for (char c : result) {
            if (!toRemove.contains(c)) {
                return c;
            }
        }
        return '0';
    }

    private char[] resolveCF() {
        var result = signal.getInputs().stream().filter(s -> s.length() == 2).findFirst().get();
        return result.toCharArray();
    }

    private char resolveA(char[] mappingCF) {
        var result = signal.getInputs().stream().filter(s -> s.length() == 3).findFirst().get().toCharArray();
        for (char c : result) {
            if (c != mappingCF[0] && c!=mappingCF[1]) {
                return c;
            }
        }
        return '0';
    }

    private char[] resolvePossibleD(char[] mappingCF) {
        var result = signal.getInputs().stream().filter(s -> s.length() == 4).findFirst().get().toCharArray();
        char mapping[] = new char[2];
        int i=0;
        for (char c : result) {
            if (c != mappingCF[0] && c!=mappingCF[1]) {
                mapping[i++] = c;
            }
        }
        return mapping;
    }

    private char resolveD(char[] mappingD, char mappingA, char[] mappingCF) {
        Set<Character> toRemove = new HashSet<>(List.of(mappingA, mappingCF[0], mappingCF[1]));
        var result = signal.getInputs().stream().filter(s -> s.length() == 5).map(s -> {
            List<Character> keep = new ArrayList<>();
            var characters = s.toCharArray();
            for (Character character : characters) {
                if (!toRemove.contains(character)) {
                    keep.add(character);
                }
            }
            return keep;
        }).toList();
        List<Character> possibleD = List.of(mappingD[0], mappingD[1]);
        return result.get(0).stream().filter(result.get(1)::contains).filter(result.get(2)::contains).filter(possibleD::contains).findFirst().get();
    }
}
