<HTML>

<HEAD>
%include ../../../WmRoot/pub/b2bStyle.css%

<SCRIPT LANGUAGE="JavaScript">


	function onClick (action) {
		if (action == "reset") {
			document.location="ftp_settings.dsp?action=reset";
		} else if (action == "submit") {
			document.changeform.submit();
		}
	}


</SCRIPT>
</HEAD>

<BODY>

<TABLE WIDTH=100%>

%ifvar action%
%switch action%
%case 'change'%
	%invoke SimpleFTP.util:setPropertyList%
	<TR><TH id="message" colspan=4>%value message%</TH></TR>
	%endinvoke%
%endswitch%
%endif action%

<TR><TD>
<FORM name="changeform" action="ftp_settings.dsp" method="POST">
<INPUT type="hidden" name="action" value="change">

%scope param(file_name='ftp_settings.cnf')%
%invoke SimpleFTP.util.flow:getAppProperty%

<INPUT type="hidden" name="file" value="%value file%">

<TABLE WIDTH=100%>

	<TR><TH class="title" colspan=2>FTP Configuration</TH></TR>

	<TR><TD class="action" colspan=2>
		<INPUT type="button" value="Submit" onclick="onClick('submit');"></INPUT>
		</TD></TR>
	
	<TR class="heading"><TH colspan=2>FTP Properties file - %value file_name%</TH></TR>
%ifvar value_list%
	%loop value_list%
		<TR><TH class="rowlabel" width=28%>%value propertyname%</TH>
		<TD class="rowdata">
		<INPUT name="%value propertyname%" size=43 value="%value propertyvalue%"></INPUT>
		</TD>
		</TR>
	%end loop%
%else%
	<TR><TH class="rowlabel" width=28%>Property Name</TH>
		<TD class="rowdata">
		No Properties found
		</TD>
		</TR>
%endif value_list%
%endinvoke%
</TABLE>
</TD></TR></TABLE>
</FORM>
<P>
NOTE: This page defines a configuration parameters to set SimpleFTP's property values. 
</P>
<P>
ftp.return.code.list - provides comma delimited list of
FTP server valid success returns, default value - 226. Some of the FTP server may use 
different return codes of success, in that case they need to be added to this list.
</P>
<P>
ftp.log - serves as a boolean switch to enable or disable SimpleFTP's logging. Possible values - true/false
</P>
<P>
ftp.log.dir - SimpleFTP's log destination. If it is empty defaults to [Integratoin Server]/logs directory location. 
</P>
</BODY>

</HTML>
