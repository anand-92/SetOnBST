import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Nik Anand and Hudson Arledge
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }


    /*
     * standard test case for Set<String> constructor
     */
    @Test
    public final void constructorTest1() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        assertEquals(sExpected, s);
    }
    
    /*
     * standard test case for add
     */
    @Test
    public final void addTest1() {
        Set<String> xExp = this.createFromArgsRef("one", "two");
        Set<String> x = this.createFromArgsRef("one");
        String addition = "two";
        x.add(addition);

        assertEquals(x, xExp);
    }

    /*
     * boundary test case for add (empty set)
     */
    @Test
    public final void addTest2() {
        Set<String> xExp = this.createFromArgsRef("one");
        Set<String> x = this.createFromArgsRef();
        String addition = "one";
        x.add(addition);

        assertEquals(x, xExp);
    }

    /*
     * standard test case for add (another set)
     */
    @Test
    public final void addTest3() {
        Set<String> xExp = this.createFromArgsRef("one", "two", "three");
        Set<String> x = this.createFromArgsRef("one");
        Set<String> x2 = this.createFromArgsRef("two", "three");

        x.add(x2);

        assertEquals(x, xExp);
    }

    /*
     * boundary test case for remove (1 element to empty set)
     */
    @Test
    public final void removeTest1() {
        Set<String> xExp = this.createFromArgsRef();
        Set<String> x = this.createFromArgsRef("one");

        x.remove("one");

        assertEquals(x, xExp);
    }

    /*
     * standard test case for remove (2+ element)
     */
    @Test
    public final void removeTest2() {
        Set<String> xExp = this.createFromArgsRef("one", "two");
        Set<String> x = this.createFromArgsRef("one", "two", "three");

        x.remove("three");

        assertEquals(x, xExp);
    }

    /*
     * standard test case for remove (remove subset)
     */
    @Test
    public final void removeTest3() {
        Set<String> xExp = this.createFromArgsRef("three");
        Set<String> x = this.createFromArgsRef("one", "two", "three");

        Set<String> xSubset = this.createFromArgsRef("one", "two");
        x.remove(xSubset);

        assertEquals(x, xExp);
    }

     /*
     * standard test case for Set<String> removeAny method
     */
    @Test
    public final void removeAnyTest1() {
        Set<String> s = this.createFromArgsTest("A", "C");
        Set<String> sExpected = this.createFromArgsRef("A", "C");
        String str = s.removeAny();
        assertEquals(true, sExpected.contains(str));
        sExpected.remove(str);
        assertEquals(sExpected, s);
    }


    /*
     * boundary test case for removeAny, 1 element to empty set
     */
    @Test
    public final void removeAnyTest2() {
       Set<String> s = this.createFromArgsTest("A");
        Set<String> sExpected = this.createFromArgsRef("A");
        String str = s.removeAny();
        assertEquals(true, sExpected.contains(str));
        sExpected.remove(str);
        assertEquals(sExpected, s);
    }

    /*
     * standard test case for contains, 1 element
     */
    @Test
    public final void containsTest1() {
        Set<String> x = this.createFromArgsRef("one");

        assertEquals(true, x.contains("one"));
    }

    /*
     * standard test case for contains, 2+ elements
     */
    @Test
    public final void containsTest2() {
        Set<String> x = this.createFromArgsRef("one", "two", "three");

        assertEquals(true, x.contains("two"));
    }

    /*
     * boundary test case for contains, empty set
     */
    @Test
    public final void containsTest3() {
        Set<String> x = this.createFromArgsRef();

        assertEquals(false, x.contains("two"));
    }

    /*
     * standard test case for contains, not in set
     */
    @Test
    public final void containsTest4() {
        Set<String> x = this.createFromArgsRef("one", "two", "three");

        assertEquals(false, x.contains("four"));
    }
    
     /*
     * standard test case for size, multiple entries
     */
    @Test
    public final void sizeTest1() {
        Set<String> x = this.createFromArgsRef("one", "two", "three");

        assertEquals(3, x.size());
    }

    /*
     * boundary test case for size, empty set
     */
    @Test
    public final void sizeTest2() {
        Set<String> x = this.createFromArgsRef();

        assertEquals(0, x.size());
    }
}
