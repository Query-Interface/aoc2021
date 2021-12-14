package com.queryinterface.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Polymerizr2 {
    private List<Rule> rules;
    private Map<Token, Long> tokens = new HashMap<>();

    public Polymerizr2(final String template, List<Rule> rules) {
        this.rules = rules;
        var pairs = getPairs(template);
        for (String pair : pairs) {
            addToken(this.tokens, createToken(pair), 1L);
        }
    }

    private void addToken(Map<Token, Long> map, final Token token, final Long amount) {
        if (map.containsKey(token)) {
            long count = map.get(token) + amount;
            map.put(token, count);
        } else {
            map.put(token, amount);
        }
    }

    private Token createToken(final String pair) {
        var rule = rules.stream().filter(r -> r.pair().equals(pair)).findFirst();
        return new Token(pair, rule.get().newElement());
    }

    public void apply() {
        Map<Token, Long> newTokens = new HashMap<>();
        for (var entry : tokens.entrySet()) {
            var pairs = entry.getKey().generate();
            for (String pair : pairs) {
                addToken(newTokens, createToken(pair), entry.getValue());
            }
        }

        this.tokens = newTokens;
    }

    public Map<Token, Long> getResult() {
        return this.tokens;
    }

    private List<String> getPairs(final String template) {
        List<String> pairs = new ArrayList<>();
        for (int i=0; i < template.length()-1; i++) {
            pairs.add(template.substring(i, i+2));
        }
        return pairs;
    }

}
