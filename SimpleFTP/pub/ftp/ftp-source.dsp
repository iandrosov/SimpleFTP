<HTML>
<HEAD>
<TITLE>FTP Data Source Status</TITLE>
%include ../../../WmRoot/pub/b2bStyle.css%
<SCRIPT LANGUAGE="JavaScript">


	function confirmEnable (pkg) {
		var s1 = "OK to change this value?\n\n";
		return confirm (s1);
	}

	function confirmDisable (pkg) {
		var s1 = "OK to change this value?\n\n";
		return confirm (s1);
	}


</SCRIPT>
</HEAD>
<BODY>


<div class="position">
<TABLE WIDTH="100%">
	
%ifvar action%
%switch action%
%case 'enable'%
	%invoke SimpleFTP.ftp:ftp_alias_edit%
	<TR><TH id="message" colspan=8>%value message%</TH></TR>
	%endinvoke%		
%case 'disable'%
	%invoke SimpleFTP.ftp:ftp_alias_edit%
	<TR><TH id="message" colspan=8>%value message%</TH></TR>
	%endinvoke%
%endswitch%
%endif action%

%invoke SimpleFTP.ftp:ftp_alias_list_get%

<TR><TH class="title" colspan=5>FTP Alias List</TH></TR>

	<TR class="heading">
		<TH width=20%>Data Source</TH>
		<TH width=20%>Server Host</TH>
		<TH width=25%>Local Path</TH>
		<TH width=25%>Remote Path</TH>
		<TH width=10%>Enabled</TH>
		</TR>

	%loop DataSourceAlias%
	<TR>
		<TD class="rowdata">
			&nbsp;<A HREF="ftp-alias.dsp?ftpalias=%value ftpalias%">%value ftpalias%</A>
		</TD>
		<TD class="rowdata">
			&nbsp;%value ftphost%
		</TD>
		<TD class="rowdata">
			&nbsp;%value targetdir%
		</TD>
		<TD class="rowdata">
			&nbsp;%value directory%
		</TD>		
		
		<TD class="rowdata">
			%ifvar status equals('Y')%
				<A HREF="ftp-source.dsp?action=disable&ftpalias=%value ftpalias%&ftphost=%value ftphost%&status=N&serverport=%value serverport%&user=%value user%&password=%value password%&directory=%value directory%&searchmask=%value searchmask%&targetdir=%value targetdir%&archivedir=%value archivedir%&type=%value type%&rmsource=%value rmsource%"
					ONCLICK="return confirmDisable('%value name%');">	
				<IMG class="alone" SRC="/WmRoot/icons/green-ball.gif" border="no" alt="[Enable]"></A>
			%else%
				<A HREF="ftp-source.dsp?action=enable&ftpalias=%value ftpalias%&ftphost=%value ftphost%&status=Y&serverport=%value serverport%&user=%value user%&password=%value password%&directory=%value directory%&searchmask=%value searchmask%&targetdir=%value targetdir%&archivedir=%value archivedir%&type=%value type%&rmsource=%value rmsource%"
					ONCLICK="return confirmEnable('%value name%');">
				<IMG class="alone" SRC="/WmRoot/icons/red-ball.gif" border="no" alt="[Disable]"></A>
			%endif%
			</TD>

		</TR>
	%endloop%

</TABLE>
%onerror%
	<P> Error occured</P>
%endinvoke%

</div></BODY>

</HTML>

