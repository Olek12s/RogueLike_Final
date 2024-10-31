package test.runner;

import test.annotations.*;

import java.lang.reflect.Method;

public class TestRunner
{
    private static int globalTotalTests = 0;
    private static int globalPassedTests = 0;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";

    public static void runTests(Class<?> testClass)
    {
        Assertions.resetCounts(); // Reset assertion counts for the test class
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

                    // Execute the test method
                    testMethod.invoke(testInstance);    // @Test

                    // Collect error messages from assertions
                    if (!Assertions.getErrorMessages().isEmpty()) {
                        for (String error : Assertions.getErrorMessages()) {
                            classErrors.append(testMethod.getName() + " failed: " + error).append("\n");
                        }
                    } else {
                        localPassedTests++;
                        globalPassedTests++;
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
        System.out.println(String.format(ANSI_GREEN + "\n✔ Total tests passed: %d of %d" + ANSI_RESET, globalPassedTests, globalTotalTests));
        if (globalTotalTests != globalPassedTests)
        {
            System.out.println(String.format(ANSI_RED + "✖ Total tests failed: %d" + ANSI_RESET, globalTotalTests - globalPassedTests));
        }
    }
}
