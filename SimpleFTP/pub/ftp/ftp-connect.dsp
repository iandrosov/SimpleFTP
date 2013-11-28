<HTML>
<HEAD>
<TITLE>FTO Inbound Services Test</TITLE>
%include ../../../WmRoot/pub/b2bStyle.css%
</HEAD>
<BODY>
<TABLE WIDTH=100%><TR><TD>
<TABLE WIDTH=100%>
	<TR><TH class="title" colspan=6>Files on FTP server Alias - %value ftpalias%</TH></TR>

	<INPUT type="hidden" name="ftpalias" value="%value ftpalias%">

%switch action%
%case 'ftp'%
	%invoke SimpleFTP.ftp.test:ftpConnect%
	<TR><TH id="message" colspan=6>%value message% - %value file%</TH></TR>
	%endinvoke%
%endswitch%


%invoke SimpleFTP.ftp.test:ftpConnect%

	<TR class="heading"><TH colspan=6>Remote File/Directory List</TH></TR>
	<TR class="subheading">
		<TH>File Name</TH>
		<TH>FTP Sever</TH>		
		<TH>Source Directory</TH>
		<TH>Target Directory</TH>
		</TR>
%loop dirlist%
	<TR><TD class="rowdata"><LI><A>%value dirlist%</A></TD>
		<TD class="rowdata">%value ftphost%</TD>		
		<TD class="rowdata">%value directory%</TD>
		<TD class="rowdata">%value targetdir%</TD>
		</TR>
%endloop%
%ifvar $dbMessage%
	<TR><TH id="message" colspan=6>%value $dbMessage%</TH></TR>
%endif%
%onerror%
	<TR><TH id="message" colspan=6>Could not connect: %value errorMessage%</TH></TR>
%endinvoke%

</TABLE>
</TD></TR></TABLE>
</BODY>
</HTML>
