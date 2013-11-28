<HTML>

<HEAD>
%include ../../../WmRoot/pub/b2bStyle.css%

<SCRIPT LANGUAGE="JavaScript">


	function onClick (action) {
		if (action == "reset") {
			document.location="add_new_ftp_process.dsp?action=reset";
		} else if (action == "submit") {
			document.changeform.submit();
		}
	}


</SCRIPT>
</HEAD>

<BODY>

<TABLE WIDTH=100%>


<TR><TD>
<FORM name="changeform" action="scheduler.dsp" method="POST">
<INPUT type="hidden" name="action" value="add">
<INPUT type="hidden" name="mode" value="useradd">
<TABLE WIDTH=100%>

	<TR><TH class="title" colspan=2>Add New FTP Process</TH></TR>

	<TR><TD class="action" colspan=2>
		<INPUT type="button" value="Submit" onclick="onClick('submit');"></INPUT>
		</TD></TR>

	<TR ><TH colspan=2 class="heading">Provide Paramters for FTP Process</TH></TR>

	<TR><TH class="oddcol" width=28%>Select Process Type</TH>
	<TD class="oddrow-l">
		<SELECT name="service_type">
			<OPTION value="INBOUND">INBOUND</OPTION>
			<OPTION value="OUTBOUND">OUTBOUND</OPTION>
		</SELECT>
	</TD>
	</TR>

	<TR><TH class="oddcol" width=28%>Select FTP Alias</TH>
		<TD class="oddrow-l">
	%invoke SimpleFTP.ftp:ftp_alias_list_get%
	%ifvar DataSourceAlias%
		<SELECT name="ftp_alias">
			%loop DataSourceAlias%
			 
				<OPTION value="%value ftpalias%">%value ftpalias%</OPTION>
			
			%endloop%
		</SELECT>
	%else%
		<B><I>Fatal Error. No Alias found!</I></B>
	%endif%
	
	%endinvoke%
	</TD>
	</TR>

	<TR><TH class="oddcol" width=28%>Select Package</TH>
		<TD class="oddrow-l">
	%invoke wm.server.packages:packageList%
	%ifvar packages%
			<SELECT name="package">
				%loop packages%
			%ifvar enabled equals('true')%  
				<OPTION value="%value name%">%value name%</OPTION>
			%endif%
				%endloop%
			</SELECT>
	%else%
		<B><I>Fatal Error. No packages!</I></B>
	%endif%
	%endinvoke%
	</TD>
	</TR>

	<TR><TH class="evencol" width=28%>Enter Folder/Interface</TH>
		<TD class="evenrow-l">
			<INPUT name="ifc" value="" size=70></INPUT>
	</TD>
	</TR>
		
	<TR><TH class="evencol" width=28%>Enter New Service Name</TH>
		<TD class="evenrow-l">
			<INPUT name="service_name" value="" size=70></INPUT>
	</TD>
	</TR>

</TABLE>
</TD></TR></TABLE>
</FORM>
</BODY>

</HTML>
