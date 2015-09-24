package org.biins.objectbuilder.builder.generator;

/**
 * @author Martin Janys
 */
public class EmailGenerator implements Generator<String> {

    private Generator<String> nameGenerator;

    public EmailGenerator() {
        this.nameGenerator = new NameGenerator();
    }

    public EmailGenerator(Generator<String> nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    @Override
    public boolean isCyclic() {
        return true;
    }

    @Override
    public void reset() {
        nameGenerator.reset();
    }

    @Override
    public boolean hasNext() {
        return nameGenerator.hasNext();
    }

    @Override
    public String next() {
        return nameGenerator.next() + "@" + "mail.com";
    }
}
