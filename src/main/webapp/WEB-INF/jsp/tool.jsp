<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Maximal Contiguous Subsequent Sum Problem - Interactive Tool</title>
</head>
<body style="font-face: calabri, arial;">
	<h2>Maximal Contiguous Subsequent Sum Problem - Interactive Tool</h2>

	<%-- We need to carry the script string in the submittal, so get the --%>
	<%-- value from the evalForm.script TEXTAREA in the onsubmit event.  --%>
	<%-- If we do not do this any script typed in will be lost.          --%>
	<s:form action="set-array-size" name="sizeForm"
		onsubmit="this.script.value=document.evalForm.script.value; return true;">
		<s:hidden name="script" />
		<strong>Array</strong><br />
		<TEXTAREA rows="4" cols="80" name="scriptArray"><s:property
				value="scriptArray" /></TEXTAREA>
		<br />

	Set array size to:&nbsp;&nbsp;
	<button type="submit" name="arraySize" value="5">&nbsp;8&nbsp;</button>
		<button type="submit" name="arraySize" value="32">&nbsp;32&nbsp;</button>
		<button type="submit" name="arraySize" value="256">&nbsp;256&nbsp;</button>
		<button type="submit" name="arraySize" value="1024">&nbsp;1024&nbsp;</button>
		<button type="submit" name="arraySize" value="4096">&nbsp;4096&nbsp;</button>
		<button type="submit" name="arraySize" value="4096">&nbsp;1000&nbsp;</button>
		<button type="submit" name="arraySize" value="4096">&nbsp;10000&nbsp;</button>
		<button type="submit" name="arraySize" value="4096">&nbsp;100000&nbsp;</button>
	</s:form>

	<%-- We need to carry the scriptArray string in this submittal, so get the --%>
	<%-- value from the sizeForm.scriptArray TEXTAREA in the onsubmit event.   --%>
	<%-- If we do not do this, any array definition typed in will be lost.     --%>
	<s:form action="eval" name="evalForm"
		onsubmit="this.scriptArray.value=document.sizeForm.scriptArray.value; return true;">
		<s:hidden name="scriptArray" />
		<strong>Java Code (bsh) to Evaluate</strong><br /> <s:if
				test='loadScriptName != null && loadScriptName != ""'>
				<TEXTAREA rows="22" cols="80" name="script"><jsp:include
						page="/WEB-INF/scripts/${loadScriptName}-mcss.bsh" /></TEXTAREA>
				<br />
			</s:if> <s:else>
				<TEXTAREA rows="22" cols="80" name="script"><s:property value="script" /></TEXTAREA>
				<br />
			</s:else>

			<button type="submit" name="eval" value="Submit">Eval Script</button>
			<button type="submit" name="loadScriptName" value="linear">Load Linear Algorithm O(n)</button>
			<button type="submit" name="loadScriptName" value="quadratic">Load Quadratic Algorithm O(n^2)</button>
			<button type="submit" name="loadScriptName" value="cubic">Load Cubic Algorithm O(n^3)</button>
	</s:form>

	<%-- Decide what to display in the results area dependent on whether there --%>
	<%-- was an error, a script array generation, a script load, or an eval.   --%>
	<s:if test="%{scriptException != null}">
		<h3>Error</h3>
		<pre>
	<s:property value="scriptException.message" />
	</pre>
	</s:if>
	<s:elseif test="arraySize <= 0 && loadScriptName == null">
		<h3>Results</h3>
		<pre>
Script execution time: <s:property value="scriptExecutionTime * .000000001" /> seconds

<s:property value="scriptOutput" />
	</pre>
	</s:elseif>

</body>
</html>
