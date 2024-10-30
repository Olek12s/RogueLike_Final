package test.runner;

import test.annotations.*;

import java.lang.reflect.Method;

public class TestRunner
{
    //COUNTERS
    private static int globalTotalTests = 0;
    private static int globalPassedTest = 0;

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
        int localTotalTests = 0;
        int localPassedTests = 0;
        StringBuilder classErrors = new StringBuilder();

        for (Method testMethod : testClass.getDeclaredMethods())
        {
            if (testMethod.isAnnotationPresent(Test.class)) {
                globalTotalTests++;
                localTotalTests++;
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

                    try
                    {
                        testMethod.invoke(testInstance);    // @Test
                        globalPassedTest++;
                        localPassedTests++;
                        //System.out.println(testMethod.getName() + " passed.");
                    }
                    catch (Throwable t)
                    {
                        String errorMessage = testMethod.getName() + " failed: " + t.getCause();
                        classErrors.append(errorMessage).append("\n");
                    }

                    for (Method afterMethod : testClass.getDeclaredMethods())   // @After
                    {
                        if (afterMethod.isAnnotationPresent(After.class)) {
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
                testClass.getSimpleName(), localPassedTests, localTotalTests));
    }

    public static void printSummary()
    {
            System.out.println(String.format(ANSI_GREEN + "\n✔ Total tests passed: %d of %d" + ANSI_RESET, globalPassedTest, globalTotalTests));
            if (globalTotalTests != globalPassedTest)
            {
                System.out.println(String.format(ANSI_RED + "✖ Total tests failed: %d" + ANSI_RESET, globalTotalTests - globalPassedTest));
            }
    }
}