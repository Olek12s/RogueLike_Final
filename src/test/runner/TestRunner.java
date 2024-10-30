package test.runner;

import test.annotations.*;

import java.lang.reflect.Method;

public class TestRunner
{
    private static int totalTests = 0;
    private static int passedTests = 0;

    public static void runTests(Class<?> testClass)
    {
        for (Method testMethod : testClass.getDeclaredMethods())
        {
            if (testMethod.isAnnotationPresent(Test.class)) {
                totalTests++;
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
                        passedTests++;
                        //System.out.println(testMethod.getName() + " passed.");
                    }
                    catch (Throwable t)
                    {
                        System.err.println(testMethod.getName() + " failed: " + t.getCause());
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
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[34m";
        System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
        System.out.println(String.format("Tests passed: %d of %d", passedTests, totalTests));
    }
}