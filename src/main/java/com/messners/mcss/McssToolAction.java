package com.messners.mcss;

import java.util.Random;

import com.messners.bsh.BshAction;

/**
 * This Action provides Struts 2 access to the BeanShell Interpreter but with a
 * specialization for evaluating algorithms for solving the Maximal Contiguous
 * Subsequent Sum Problem. It provides methods for evaluating script based Java code
 * and for generating Java code of a populated int array (int[] a). The Struts 2
 * Framework will automatically populate this action with with the following request
 * parameters:
 * 
 * script - The Java code (BeanShell) script to execute.
 * scriptArray - The Java code containing an int array definition (int[] a = {...}).
 * arraySize - Will be > 0 if the request is for generation of the input array (int[] a)
 * loadScriptName - The name of one of the canned MCSS algorithms, must be one of the following:
 * 
 * cubic
 * linear
 * quadratic
 * 
 * The Java code for the specified algorithm will be loaded into the web app for later user
 * initiated evaluation.
 * 
 * 
 * Upon completion the following items will be available on the ValueStack:
 * 
 * scriptOutput - the standard output from the script - a String
 * scriptErrorOutput - the error output from the script - a String
 * resultObject - the resultant object of the script (may be null) - an Object
 * scriptException - will be non-null if the script threw an exception - an Exception
 * executionTime - the number of nano seconds that the script took to execute - a long
 * 
 * 
 * WARNING: Scripts executed in this environment have full access to system resources!
 *
 */
public class McssToolAction extends BshAction {

    private String scriptArray;
    private String loadScriptName;
    private int arraySize;

    /**
     * This is the normal Struts 2 action entry point. Will evaluate the script that was
     * provided by the <code>script</code> parameter. This method delegates to the
     * <code>eval(String script)</code> method in BshAction after the script has had
     * the input int[] prepended.
     *
     * @return a Struts2 results status string. Will either be "success" or "error".
     */
    public String execute() {

        if (getLoadScriptName() != null) {
            return ("success");
        }

        String script = getScript();
        if (script == null) {
            return ("success");
        }


        String array = getScriptArray();
        if (array != null) {
            script = array + "\n" + script;
        }

        return (eval(script));
    }

    /**
     * This action method will generate a String containing the Java code for an inr array.
     * The size of the array is determined by the <code>arraySize</code> parameter.
     * The generated text will contain the code for a fully populated array with values from
     * -99 to 99 randomly generated.
     *
     * @return a Struts2 results status string. Will always be "success".
     */
    public String setsize() {

        setScriptArray(generateArray(getArraySize()));
        return ("success");
    }

    /**
     * Generates a String containing a populated int array.
     *
     * @return a String containing the script array
     */
    private String generateArray(int size) {

        if (size < 1) {
            return ("int[] a[] = {};");
        }

        Random randomGenerator = new Random();
        int maxAbsoluteValue = 99;
        int limit = 2 * maxAbsoluteValue + 1;

        StringBuilder arrayBuffer = new StringBuilder(size * 4);
        int value = randomGenerator.nextInt(limit) - maxAbsoluteValue;
        arrayBuffer.append("int[] a = {").append(value);

        for (int i = 1; i < size; i++) {
            value = randomGenerator.nextInt(limit) - maxAbsoluteValue;
            arrayBuffer.append(", ").append(value);
        }

        arrayBuffer.append("};");
        return (arrayBuffer.toString());
    }

    /**
     * Sets the Java code containing the <code>int[] a = {#, #, ...};</code>. Usually provided automatically by the
     * Struts2 framework. This is prepended to the actual script.
     * 
     * @param scriptArray a String containing the script array
     */
    public void setScriptArray(String scriptArray) {
        this.scriptArray = scriptArray;
    }

    /**
     * Gets a String containing the Java code containing the <code>int[] a = {#, #, ...};</code>.
     * Usually provided automatically by the Struts2 framework. This is prepended to the
     * actual script.
     * 
     * @return a String containing the script array
     */
    public String getScriptArray() {
        return (scriptArray);
    }

    /**
     * Sets the size of the array to generate.
     *
     * @param arraySize > 0 if the request is for generation of the input array (int[] a)
     */
    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }

    /**
     * Gets the size of the array to generate.
     *
     * @return the size of the array to generate, will be > 0 if the request is for
     * generation of the input array (int[] a)
     */
    public int getArraySize() {
        return (arraySize);
    }

    /**
     * Sets the name of the canned MCSS algorithm, must be one of the following:
     * 
     * cubic
     * linear
     * quadratic
     * 
     * The Java code for the specified algorithm will be loaded into the web app for
     * later user initiated evaluation.
     * 
     * @param loadScriptName the name of one of the canned MCSS algorithms
     */
    public void setLoadScriptName(String loadScriptName) {

        if ("Load Cubic Algorithm".equals(loadScriptName)) {
            loadScriptName = "cubic";
        } else if ("Load Linear Algorithm".equals(loadScriptName)) {
            loadScriptName = "linear";
        } else if ("Load Quadratic Algorithm".equals(loadScriptName)) {
            loadScriptName = "quadratic";
        }

        this.loadScriptName = loadScriptName;
    }

    /**
     * Gets the name of the canned MCSS algorithm that was specified in a request.
     * 
     * @return the name of one of the canned MCSS algorithms to be loaded
     */
    public String getLoadScriptName() {
        return (loadScriptName);
    }
}
