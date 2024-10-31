package test.runner;

import test.annotations.*;

import java.lang.reflect.Method;

public class TestRunner
{
    //COLORS
    private static final String ANSI_RESET = "\u001B[0m";    // IDE'S standar color
    private static final String ANSI_BLACK = "\u001B[30m";   // BLACK
    private static final String ANSI_RED = "\u001B[31m";     // RED
    private static final String ANSI_GREEN = "\u001B[32m";   // GREEN
    private static final String ANSI_YELLOW = "\u001B[33m";  // YELLOW
    private static final String ANSI_BLUE = "\u001B[34m";    // BLUE
    private static final String ANSI_PURPLE = "\u001B[35m";  // PURPLE
    private static final String ANSI_CYAN = "\u001B[36m";    // CYAN
    private static final String ANSI_WHITE = "\u001B[37m";   // WHITE

    public static void runTests(Class<?> testClass)
    {
        Assertions.resetCounts();
        StringBuilder classErrors = new StringBuilder();

        for (Method testMethod : testClass.getDeclaredMethods())
        {
            if (testMethod.isAnnotationPresent(Test.class)) {
                try
                {
                    Object testInstance = testClass.getDeclaredConstructor().newInstance();
                    for (Method beforeMethod : testClass.getDeclaredMethods())  // @Before
                    {
                        if (beforeMethod.isAnnotationPresent(Before.class))
                        {
                            beforeMethod.invoke(testInstance);
                        }
                    }
                    testMethod.invoke(testInstance);    // execute @Test methods

                    if (!Assertions.getErrorMessages().isEmpty())
                    {
                        for (String error : Assertions.getErrorMessages())
                        {
                            classErrors.append(testMethod.getName() + " failed: " + error).append("\n");
                        }
                    }

                    for (Method afterMethod : testClass.getDeclaredMethods())   // @After
                    {
                        if (afterMethod.isAnnotationPresent(After.class))
                        {
                            afterMethod.invoke(testInstance);
                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        // For currently tested class
        System.out.println(ANSI_RED + classErrors.toString() + ANSI_RESET);
        System.out.println(String.format(ANSI_GREEN + "Class " + ANSI_CYAN +  "%s: " + ANSI_GREEN +  "%d of %d tests passed.\n" + ANSI_RESET,
                testClass.getSimpleName(), Assertions.getPassedCount(), Assertions.getTestCount()));
    }

    public static void printSummary()
    {
            System.out.println(String.format(ANSI_GREEN + "\n✔ Total tests passed: %d of %d" + ANSI_RESET, Assertions.getTotalAssertionsPassed(), Assertions.getTotalAssertions()));
            if (Assertions.getTotalAssertions() != Assertions.getTotalAssertionsPassed())
            {
                System.out.println(String.format(ANSI_RED + "✖ Total tests failed: %d" + ANSI_RESET, Assertions.getTotalAssertions() - Assertions.getTotalAssertionsPassed()));
            }
    }
}