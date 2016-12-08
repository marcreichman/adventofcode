package com.pfi.advent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(Files.readAllBytes(Paths.get("input.txt")));

        // phase 1
        long total = getNumberTotals(root);
        System.out.println(total);

        // phase 2
        long total2 = sumTree(root, n -> hasValue(n, "red"));
        System.out.println(total2);
     }

    private static long getNumberTotals(JsonNode root) {
        long sum = 0;
        for (JsonNode node : root) {
            if (node.isValueNode() && node.isNumber()) {
                sum += node.asLong();
            } else if (node.isArray() || node.isObject()) {
                sum += getNumberTotals(node);
            } else {
                sum += 0;
            }
        }

        return sum;
    }

    public static int sumTree(JsonNode node, Function<JsonNode, Boolean> filter) {
        if(node.isInt()) {
            return node.asInt();
        }
        if(filter.apply(node)) {
            return 0;
        }
        final AtomicInteger sum = new AtomicInteger(0);

        node.forEach(n -> sum.addAndGet(sumTree(n, filter)));

        return sum.get();
    }

    public static boolean hasValue(JsonNode node, String value) {
        Iterator<JsonNode> iter = node.iterator();

        if(!node.isObject()) {
            return false;
        }
        while(iter.hasNext()) {
            if(value.equals(iter.next().asText())) {
                return true;
            }
        }

        return false;
    }

    private static Long getNumberTotalsExclusion(JsonNode root, String exclusion) {
        long sum = 0;
        for (JsonNode node : root) {
            if (node.isTextual() && node.textValue().equals(exclusion)) {
                return null;
            }

            if (node.isObject()) {
                Long thisSum = getNumberTotalsExclusion(node, exclusion);
                if (thisSum == null) {
                    continue;
                }

                sum += thisSum;
            } else if (node.isArray()) {
                for (JsonNode arrayMember : node) {
                    if (arrayMember.isObject()) {
                        Long thisSum = getNumberTotalsExclusion(arrayMember, exclusion);
                        if (thisSum == null) {
                            continue;
                        }

                        sum += thisSum;
                    } else {
                        if (arrayMember.isNumber()) {
                            sum += arrayMember.longValue();
                        }
                    }
                }
            } else if (node.isNumber()) {
                sum += node.longValue();
            }
        }

        return sum;
    }
}
