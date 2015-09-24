package org.biins.objectbuilder.builder.generator;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author Martin Janys
 */
public class NameGenerator extends CyclicValuesGenerator<String> {

    public NameGenerator(String... values) {
        super((String[]) ArrayUtils.addAll(
                new String[] {
                        "Sophia",
                        "Jackson",
                        "Emma",
                        "Aiden",
                        "Olivia",
                        "Liam",
                        "Ava",
                        "Lucas",
                        "Isabella",
                        "Noah",
                        "Mason",
                        "Mia",
                        "Ethan",
                        "Zoe",
                        "Caden",
                        "Lily",
                        "Jacob",
                        "Emily",
                        "Logan",
                        "Madelyn"
                },
                values
        ));
    }
}
