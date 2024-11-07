package tests;

import test.annotations.Before;

import static java.lang.System.getProperties;

public class FileManipulationTest
{
    @Before
    public void initialize()
    {

        System.out.println(getProperties());
    }

    public static void main(String[] args)
    {
        String os = System.getProperty("os.name");
        System.out.println(os);
    }
}
