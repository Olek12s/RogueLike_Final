package tests;

import test.annotations.Before;
import test.annotations.After;
import test.annotations.Test;
import test.annotations.TestRunner;

public class Main
{
    public static void main(String[] args)
    {
        TestRunner.runTests(MyMathTest.class);
    }
}
