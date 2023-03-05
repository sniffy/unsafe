using NUnit.Framework;
using System;
using System.Diagnostics;

namespace ikvm_test
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void Test1()
        {
            java.net.URL url = new java.net.URL("file:../../unsafe-impl/build/libs/unsafe-impl.jar");
            // Create an array of all URLS
            java.net.URL[] urls = { url };
            // Create a ClassLoader
            java.net.URLClassLoader loader = new java.net.URLClassLoader(urls);
            try
            {
                // load the Class
                java.lang.Class cl = java.lang.Class.forName("tools.unsafe.vm.UnsafeVirtualMachine", true, loader);

                // Create a Object via Java reflection
                java.lang.reflect.Method method = cl.getMethod("getJavaVersion");

                method.invoke(null, null);

                java.lang.Number javaVersion = method.invoke(null, null) as java.lang.Integer;
                Console.WriteLine(javaVersion.intValue());
                Debug.WriteLine(javaVersion.intValue());


            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                Console.WriteLine(ex.StackTrace);
                throw ex;
            }
            Assert.Pass();
        }
    }
}