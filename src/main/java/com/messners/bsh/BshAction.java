package com.messners.bsh;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import bsh.Interpreter;

/**
 * This Action provides Struts 2 access to the BeanShell Interpreter. The Struts 2
 * Framework will automatically populate this action with with the following request
 * parameters:
 * 
 * script - The BeanShell script to execute.
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
public class BshAction {

    private String script;
    private String scriptOutput;
    private String scriptErrorOutput;
    private Object resultObject;
    private Exception scriptException;
    private long executionTime;

    /**
     * This is the normal Struts 2 action entry point. Will evaluate the script that was
     * provided by the <code>script</code> parameter. This method delegates to the
     * <code>eval(String script)</code> method.
     * 
     * @return a Struts2 results status string. Will either be "success" or "error".
     */
    public String execute() {
        return (eval(getScript()));
    }

    /**
     * This method does the actual evaluation of the provided script.
     * 
     * @param script a String containing the script to execute.
     * @return a Struts2 results status string. Will either be "success" or "error".
     */
    protected String eval(String script) {

        /*
         * Make sure we have something to execute
         */
        if (script == null) {
            return ("success");
        }

        /*
         * Create a wrapped ByteArrayOutputStream to capture script output
         */
        ByteArrayOutputStream outCapture = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outCapture);

        /*
         * Create a wrapped ByteArrayOutputStream to capture script error output
         */
        ByteArrayOutputStream errCapture = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errCapture);

        /*
         * Create an Interpreter instance to eval the provided script. This is
         * configured with no InputStream and PrintStreams to capture both
         * output and error output (err).
         */
        Interpreter bsh = new Interpreter(null, out, err, false);

        /*
         * Save the state of System.out and System.err, we reset them later.
         */
        PrintStream systemOut = System.out;
        PrintStream systemErr = System.err;

        try {

            long startTime, stopTime;
            startTime = System.nanoTime();
            
            if (script.contains("System.exit(")) {
                throw (new RuntimeException("System.exit() is a forbidden method!"));
            }

            bsh.eval(script);

            stopTime = System.nanoTime();
            setScriptExecutionTime(stopTime - startTime);

        } catch (Exception e) {
            setScriptException(e);
        }

        /*
         * Reset System.out and System.err.
         */
        System.setOut(systemOut);
        System.setErr(systemErr);

        if (getScriptException() != null) {
            return ("error");
        }

        /*
         * Flush and capture the script output
         */
        out.flush();
        setScriptOutput(outCapture.toString());

        err.flush();
        setScriptErrorOutput(errCapture.toString());

        return ("success");
    }

    /**
     * Sets the script to be evaluated. Usually provided automatically by the
     * Struts2 framework.
     * 
     * @param script a String containing the script to be evaluated
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * Gets the String containing the script to be evaluated.
     * 
     * @return a String containing the script to be evaluated
     */
    public String getScript() {
        return (script);
    }

    /**
     * Sets the Object that was the result of the script evaluation.
     * 
     * @param resultObject the Object that was the result of the script evaluation
     */
    protected void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }

    /**
     * Gets the Object that was the result of the script evaluation.
     * 
     * @return the Object that was the result of the script evaluation.
     * Can be null even on success and will be null prior to script evaluation.
     */
    public Object getResultObject() {
        return (resultObject);
    }

    /**
     * Sets the String that contains any standard output resulting from script evaluation.
     * 
     * @param scriptOutput the String that contains any standard output resulting from
     * script evaluation
     */
    protected void setScriptOutput(String scriptOutput) {
        this.scriptOutput = scriptOutput;
    }

    /**
     * Gets the String that contains any standard output resulting from script evaluation.
     * 
     * @return a String that contains any standard output resulting from
     * script evaluation. Will be null prior to script evaluation.
     */
    public String getScriptOutput() {
        return (scriptOutput);
    }

    /**
     * Sets the String that contains any error output resulting from script evaluation.
     * 
     * @rparam scriptErrorOutput String that contains any error output resulting from
     * script evaluation
     */
    protected void setScriptErrorOutput(String scriptErrorOutput) {
        this.scriptErrorOutput = scriptErrorOutput;
    }

    /**
     * Gets the String that contains any error output resulting from script evaluation.
     * 
     * @return a String that contains any error output resulting from
     * script evaluation. Will be null prior to script evaluation and may be null
     * after evaluation.
     */
    public String getScriptErrorOutput() {
        return (scriptErrorOutput);
    }

    /**
     * Sets the Exception caused by an error in the provided script. This is set by
     * the <code>eval()</code> method.
     * 
     * @param exception he Exception caused by an error in the provided script. This is
     * set by the <code>eval()</code> method.
     */
    protected void setScriptException(Exception exception) {
        this.scriptException = exception;
    }

    /**
     * Gets the Exception caused by an error in the provided script. Will be null with
     * normal evaluation.
     * 
     * @return the Exception caused by an error in the provided script. Will be null with
     * normal evaluation.
     */
    public Exception getScriptException() {
        return (scriptException);
    }

    /**
     * Sets the number of nano seconds that the script took to execute. This is set
     * by the <code>eval()</code> method.
     * 
     * @param executionTime the number of nano seconds that the script took to execute.
     */
    protected void setScriptExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * Gets the number of nano seconds that the script took to execute.
     * 
     * @return the number of nano seconds that the script took to execute. Will be 0
     * prior to script evaluation
     */
    public long getScriptExecutionTime() {
        return (executionTime);
    }
}
