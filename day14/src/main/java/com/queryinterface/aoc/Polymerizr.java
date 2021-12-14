package com.queryinterface.aoc;

import java.util.ArrayList;
import java.util.List;

public class Polymerizr {
    private String template;
    private List<Rule> rules;
    private String result;

    public Polymerizr(final String template, List<Rule> rules) {
        this.template = template;
        this.rules = rules;
    }

    public String apply() {
        StringBuilder builder = new StringBuilder();
        if (result == null) {
            result = template;
        }
        var pairs = getPairs(result);
        boolean isFirst = true;
        for (String pair : pairs) {
            var rule = rules.stream().filter(r -> r.pair().equals(pair)).findFirst();
            if (rule.isPresent()) {
                if (isFirst) {
                    builder.append(pair.charAt(0));
                    isFirst = false;
                }
                builder.append(rule.get().newElement());
                builder.append(pair.charAt(1));
            } else {
                isFirst = false;
                builder.append(pair);
            }
        }
        result = builder.toString();
        return result;
    }

    public String getResult() {
        return this.result;
    }

    private List<String> getPairs(final String template) {
        List<String> pairs = new ArrayList<>();
        for (int i=0; i < template.length()-1; i++) {
            pairs.add(template.substring(i, i+2));
        }
        return pairs;
    }
}
