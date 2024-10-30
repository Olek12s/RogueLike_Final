package test.annotations;

import test.annotations.Before;
import test.annotations.After;
import test.annotations.Test;

import java.lang.reflect.Method;

public class TestRunner
{

    public static void runTests(Class<?> testClass)
    {
        Object testInstance;
        try
        {
            testInstance = testClass.getDeclaredConstructor().newInstance();

            for (Method method : testClass.getDeclaredMethods())
            {
                if (method.isAnnotationPresent(Before.class))
                {
                    method.invoke(testInstance);
                }
            }

            for (Method method : testClass.getDeclaredMethods())
            {
                if (method.isAnnotationPresent(Test.class))
                {
                    try
                    {
                        method.invoke(testInstance);
                        System.out.println(method.getName() + " passed.");
                    }
                    catch (Throwable t)
                    {
                        System.out.println(method.getName() + " failed: " + t.getCause());
                    }
                }
            }

            for (Method method : testClass.getDeclaredMethods())
            {
                if (method.isAnnotationPresent(After.class))
                {
                    method.invoke(testInstance);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
