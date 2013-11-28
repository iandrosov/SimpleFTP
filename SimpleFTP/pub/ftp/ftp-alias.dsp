<HTML>

<HEAD>
<TITLE>B2B Integration Server -- Users</TITLE>
%include ../../../WmRoot/pub/b2bStyle.css%
</HEAD>

<BODY>
<TABLE width="100%"><TR><TD>
<TABLE width="100%">

%ifvar falias%

%switch falias%

%case 'nnnn'%

	<TR><TH class="title" colspan=2>New FTP Alias</TH></TR>

<FORM method="POST" name="aliasform" action="ftp-alias.dsp">

<SCRIPT LANGUAGE="JavaScript">
	function onSubmit() {
		var ftpalias = document.aliasform.ftpalias.value;
		if (ftpalias == null || ftpalias == "") {
			alert ("Specify an alias name.");
		} else {
			document.aliasform.submit();
			return true;
		}
		return false;
	}
</SCRIPT>

	<INPUT type="hidden" name="action" value="add">

	<TR><TD class="action" colspan=2>
		<INPUT type="button" value="Submit" onclick="onSubmit();">
		<INPUT type="button" value="Reset" onclick="document.aliasform.reset();">
		<INPUT type="button" value="Cancel" onclick="document.location='ftp-alias.dsp'">
		</TD></TR>

	<TR class="heading"><TH colspan=2>FTP Alias Configuration Details</TH></TR>
	
	<!-- LEFT TABLE -->
        <td valign=top>
        <table width="100%">

	<TR class="heading"><TH colspan=2>FTP Server Details</TH></TR>

	<TR>
		<TH class="rowlabel" width="35%">Alias</TH>
		<TD class="rowdata"><INPUT name="ftpalias" size=30></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="35%">FTP Server Host</TH>
		<TD class="rowdata"><INPUT name="ftphost" size=30></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="35%">FTP Server Port</TH>
		<TD class="rowdata"><INPUT name="serverport" size=30></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="35%">FTP User</TH>
		<TD class="rowdata"><INPUT name="user" size=30></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="35%">FTP Password</TH>
		<TD class="rowdata"><INPUT type="password" name="password" size=30></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="35%">File Search mask</TH>
		<TD class="rowdata"><INPUT name="searchmask" size=30></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="35%">Local Working Directory</TH>
		<TD class="rowdata"><INPUT name="targetdir" size=30></INPUT></TD>
		</TR>
	<TR>
		<TH class="rowlabel" width="35%">Local Archive Directory</TH>
		<TD class="rowdata"><INPUT name="archivedir" value="%value archivedir%" size=30></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="35%">Remote Working Directory</TH>
		<TD class="rowdata"><INPUT name="directory" size=30></INPUT></TD>
		</TR>
		
	<TR>
		<TH class="rowlabel" width="35%">Remote Archive Directory</TH>
		<TD class="rowdata"><INPUT name="remotearchivedir" value="%value remotearchivedir%" size=30></INPUT></TD>
		</TR>

	<TR><TH class="oddcol" width=28%>Transfer Type</TH>
	<TD class="oddrow-l">
		<SELECT name="transfertype">						
			<OPTION value="active">active</OPTION>
			<OPTION value="passive">passive</OPTION>
		</SELECT>
	</TD>
	</TR>
	
     </table>
     </td>
     <!-- END OF LEFT TABLE -->

     <!-- RIGHT TABLE -->
     <td valign=top >
     <table width=100% >

	<TR class="heading"><TH colspan=2>FTP Server Details</TH></TR>
	
	<TR><TH class="oddcol" width=28%>Transfer Mode</TH>
	<TD class="oddrow-l">
		<SELECT name="transfermode">						
			<OPTION value="ascii">ascii</OPTION>
			<OPTION value="binary">binary</OPTION>
		</SELECT>
	</TD>
	</TR>

	<TR><TH class="rowlabel" width="28%">Archive Files?</TD>
		<TD class="rowdata">
			<INPUT type="radio" name="archiveflag" value="true"> Yes</INPUT><BR>
			<INPUT type="radio" name="archiveflag" value="false" CHECKED> No</INPUT><BR>
		</TD>
	</TR>
	
	<TR><TH class="rowlabel" width="28%">Remove File From Source?</TD>
		<TD class="rowdata">
			<INPUT type="radio" name="rmsource" value="Y"> Yes</INPUT><BR>
			<INPUT type="radio" name="rmsource" value="N" CHECKED> No</INPUT><BR>
		</TD>
	</TR>
		
	<TR><TH class="rowlabel" width="28%">Enable This Alias?</TD>
		<TD class="rowdata">
			<INPUT type="radio" name="status" value="Y"> Yes</INPUT><BR>
			<INPUT type="radio" name="status" value="N" CHECKED> No</INPUT><BR>
		</TD>
	</TR>
	
	
	<TR class="heading"><TH colspan=2>Security</TH></TR>

        <tr>
          <th class="oddcol" width=28%>Run Service As User</th>
          <td class="oddrow-l">

          %invoke wm.server.access:userList%
          <SELECT NAME="runAsUser">
            %loop users%
            <OPTION value="%value name%" %ifvar name equals('Administrator')% selected %endif%>%value name%</OPTION>
            %endloop%
          </SELECT>
          %endinvoke%
          </td>
        </tr>

     
	<TR class="heading"><TH colspan=2>File Processing Service</TH></TR>

	<TR>
		<TH class="rowlabel" width="35%">Processing Service</TH>
		<TD class="rowdata"><INPUT name="procservice" value="%value procservice%" size=30></INPUT></TD>
	</TR>

	<TR>
		<TH class="rowlabel" width="35%">Invoke</TH>
		<TD class="rowdata">
		%ifvar invoketype equals('true')%
			<INPUT type="radio" name="invoketype" value="false"> Synchronous</INPUT><BR>
			<INPUT type="radio" name="invoketype" value="true" CHECKED> Asynchronous</INPUT><BR>
		%else%
			<INPUT type="radio" name="invoketype" value="false" CHECKED> Synchronous</INPUT><BR>
			<INPUT type="radio" name="invoketype" value="true"> Asynchronous</INPUT><BR>		
		%endif%
		</TD>
	</TR>
	<TR class="heading"><TH colspan=2>Information!</TH></TR>
	<TR>
	<TD class="rowdata" colspan=2>NOTE: Processing service can only be used with inbound FTP process. For outbound it will be ignored. 
	Exercise caution when using processing service inside FTP transfer process. 
	It is advisable to run processing services as separate task. 
	FTP transfer can be a long running process. Therefore execution of processing service may be effected. 
	Processing service is not guarnteed to execute at exact schedule intervals! 
	Synchronous execution of a processing service will effect overall FTP process.</TD>
    	</TR>
     </table>
     </td>
     <!-- END OF RIGHT TABLE -->
     
     

</FORM>


%case%

	<TR><TH class="title" colspan=2>Edit FTP Alias Information</TH></TR>

%invoke SimpleFTP.ftp:ftp_alias_get%

%ifvar ftpalias vequals('/ftpalias')%

<FORM method="POST" name="aliasform" action="ftp-alias.dsp">

	<INPUT type="hidden" name="action" value="change">
	<INPUT type="hidden" name="ftpalias" value="%value ftpalias%">

	<TR><TD class="action" colspan=2>
		<INPUT type="button" value="Submit" onclick="document.aliasform.submit();">
		<INPUT type="button" value="Reset" onclick="document.aliasform.reset();">
		<INPUT type="button" value="Cancel" onclick="document.location='ftp-alias.dsp'">
		</TD></TR>


	<TR class="heading"><TH colspan=2>FTP Alias Configuration Details</TH></TR>
	
	<!-- LEFT TABLE -->
        <td valign=top>
        <table width="100%">

	<TR class="heading"><TH colspan=2>FTP Server Details</TH></TR>

	<TR>
		<TH class="rowlabel" width="28%">Alias</TH>
		<TD class="rowdata">%value ftpalias%</TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="28%">FTP Server Host</TH>
		<TD class="rowdata"><INPUT name="ftphost" size=43 value="%value ftphost%"></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="28%">FTP Server Port</TH>
		<TD class="rowdata"><INPUT name="serverport" size=43 value="%value serverport%"></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="28%">FTP User</TH>
		<TD class="rowdata"><INPUT name="user" size=43 value="%value user%"></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="28%">FTP Password</TH>
		<TD class="rowdata"><INPUT type="password" name="password" value="%value password%" size=43></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="28%">File Search Mask</TH>
		<TD class="rowdata"><INPUT name="searchmask" value="%value searchmask%" size=43></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="28%">Local Working Directory</TH>
		<TD class="rowdata"><INPUT name="targetdir" value="%value targetdir%" size=43></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="28%">Local Archive Directory</TH>
		<TD class="rowdata"><INPUT name="archivedir" value="%value archivedir%" size=43></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="28%">Remote Working Directory</TH>
		<TD class="rowdata"><INPUT name="directory" value="%value directory%" size=43></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="28%">Remote Archive Directory</TH>
		<TD class="rowdata"><INPUT name="remotearchivedir" value="%value remotearchivedir%" size=43></INPUT></TD>
		</TR>

	<TR><TH class="oddcol" width=28%>Transfer Type</TH>
	<TD class="oddrow-l">
		<SELECT name="transfertype">						
			<OPTION value="active">active</OPTION>
			<OPTION value="passive">passive</OPTION>
		</SELECT>
	</TD>
	</TR>
	<TR><TH class="oddcol" width=28%>Transfer Mode</TH>
	<TD class="oddrow-l">
		<SELECT name="transfermode">						
			<OPTION value="ascii">ascii</OPTION>
			<OPTION value="binary">binary</OPTION>
		</SELECT>
	</TD>
	</TR>

	<TR><TH class="rowlabel" width="28%">Archive Files?</TD>
	%ifvar archiveflag equals('true')%
		<TD class="rowdata">
			<INPUT type="radio" name="archiveflag" value="true" CHECKED> Yes</INPUT><BR>
			<INPUT type="radio" name="archiveflag" value="false"> No</INPUT><BR>
		</TD>
	%else%
		<TD class="rowdata">
			<INPUT type="radio" name="archiveflag" value="true"> Yes</INPUT><BR>
			<INPUT type="radio" name="archiveflag" value="false" CHECKED> No</INPUT><BR>
		</TD>	
	%endif%
	</TR>

	<TR><TH class="rowlabel" width="28%">Remove File From Source?</TD>
	%ifvar rmsource equals('Y')%
		<TD class="rowdata">
			<INPUT type="radio" name="rmsource" value="Y" CHECKED> Yes</INPUT><BR>
			<INPUT type="radio" name="rmsource" value="N"> No</INPUT><BR>
		</TD>
	%else%
		<TD class="rowdata">
			<INPUT type="radio" name="rmsource" value="Y"> Yes</INPUT><BR>
			<INPUT type="radio" name="rmsource" value="N" CHECKED> No</INPUT><BR>
		</TD>	
	%endif%
	</TR>

	<TR><TH class="rowlabel" width="28%">Enable This Alias?</TD>
	%ifvar status equals('Y')%
		<TD class="rowdata">
			<INPUT type="radio" name="status" value="Y" CHECKED> Yes</INPUT><BR>
			<INPUT type="radio" name="status" value="N"> No</INPUT><BR>
		</TD>
	%else%
		<TD class="rowdata">
			<INPUT type="radio" name="status" value="Y"> Yes</INPUT><BR>
			<INPUT type="radio" name="status" value="N" CHECKED> No</INPUT><BR>
		</TD>	
	%endif%
	</TR>

     </table>
     </td>
     <!-- END OF LEFT TABLE -->

     <!-- RIGHT TABLE -->
     <td valign=top >
     <table width=100% >

	<TR class="heading"><TH colspan=2>Security</TH></TR>

        <tr>
          <th class="oddcol" width=28%>Run Service As User</th>
          <td class="oddrow-l">

          %invoke wm.server.access:userList%
          <SELECT NAME="runAsUser">
            %loop users%
            <OPTION value="%value name%" %ifvar name vequals(../runAsUser)% selected %endif%>%value name%</OPTION>
            %endloop%
          </SELECT>
          %endinvoke%
          </td>
        </tr>

     
	<TR class="heading"><TH colspan=2>File Processing Service</TH></TR>

	<TR>
		<TH class="rowlabel" width="35%">Processing Service</TH>
		<TD class="rowdata"><INPUT name="procservice" value="%value procservice%" size=30></INPUT></TD>
		</TR>

	<TR>
		<TH class="rowlabel" width="35%">Invoke</TH>
		<TD class="rowdata">
		%ifvar invoketype equals('true')%
			<INPUT type="radio" name="invoketype" value="false"> Synchronous</INPUT><BR>
			<INPUT type="radio" name="invoketype" value="true" CHECKED> Asynchronous</INPUT><BR>
		%else%
			<INPUT type="radio" name="invoketype" value="false" CHECKED> Synchronous</INPUT><BR>
			<INPUT type="radio" name="invoketype" value="true"> Asynchronous</INPUT><BR>		
		%endif%
		</TD>
	</TR>
	<TR class="heading"><TH colspan=2>Information!</TH></TR>
	<TR>
	<TD class="rowdata" colspan=2>NOTE: Processing service can only be used with inbound FTP process. For outbound it will be ignored.
	Exercise caution when using processing service inside 
	FTP transfer process. It is advisable to run processing services as separate task. 
	FTP transfer can be a long running process. Therefore execution of processing service may be 
	effected. Processing service is not guarnteed to execute at exact schedule intervals! 
	Synchronous execution of a processing service will effect overall FTP process.</TD>
    	</TR>
	    
     </table>
     </td>
     <!-- END OF LEFT TABLE -->


%endif%


%endinvoke%


</FORM>


%endswitch%


%else%

<SCRIPT LANGUAGE="JavaScript">

	function getSelectedAlias  (action) {
		var opt = document.aliasform.ftpalias;
		var alias = null;
		
		if (opt.selectedIndex == -1) {
			alert ("Select an alias to "+action+".");
		} else {
			alias = opt.options[opt.selectedIndex].value;
			return alias;
		}
		
		return alias;
	}

	function editAlias () {
		var alias = getSelectedAlias ("edit");
		
		if (alias != null) {
			document.aliasform.ftpalias.value = alias;
			document.aliasform.submit();
			return true;
		}
		return false;
	}

	function deleteAlias () {
		var ftpalias = getSelectedAlias ("delete");
		if (ftpalias != null) {
			if (confirm("OK to delete alias "+ftpalias+"?")) {
				document.deleteform.ftpalias.value = ftpalias;
				document.deleteform.submit();
				return true;
			}
		}
		return false;
	}

	function connectAlias () {
		var alias = getSelectedAlias ("connect to");
		if (alias != null) {
			document.connectform.ftpalias.value = alias;
			document.connectform.submit();
		}
		return false;
	}

</SCRIPT>

	<TR><TH class="title">FTP Alias Management</TH></TR>

<FORM method="POST" name="aliasform" action="ftp-alias.dsp">

<INPUT type="hidden" name="falias" value="">

	<TR>
		<TD class="action">
			<INPUT class="data" type="button" name="add" value="Add" onclick="document.location='ftp-alias.dsp?falias=nnnn'"></INPUT>
			<INPUT class="data" type="button" value="Edit" onclick="editAlias();"></INPUT>
			<INPUT class="data" type="button" value="Delete" onclick="deleteAlias();"></INPUT>
			<INPUT class="data" type="button" value="Connect" onclick="connectAlias();"></INPUT>
		</TD></TR>

%switch action%
%case 'add'%
	%invoke SimpleFTP.ftp:ftp_alias_add%
	<TR><TH id="message" colspan=2>%value message%</TH></TR>
	%endinvoke%
%case 'change'%
	%invoke SimpleFTP.ftp:ftp_alias_edit%
	<TR><TH id="message" colspan=2>%value message%</TH></TR>
	%endinvoke%
%case 'delete'%
	%invoke SimpleFTP.ftp:ftp_alias_delete%
	<TR><TH id="message" colspan=2>%value message%</TH></TR>
	%endinvoke%
%endswitch%

	<TR class="heading"><TH>Current FTP Data Sources</TH></TR>

	<TR>
		<TD class="action">
			<SELECT NAME="ftpalias" size=6 width=400>
		%invoke SimpleFTP.ftp:ftp_alias_list_get%
		%loop DataSourceAlias%
			<OPTION value="%value ftpalias%">%value ftpalias%</OPTION>
		%endloop%
		%endinvoke%
			</SELECT>
		</TD></TR>

</FORM>

<FORM method="POST" name="deleteform" action="ftp-alias.dsp">
	<INPUT type="hidden" name="action" value="delete">
	<INPUT type="hidden" name="ftpalias" value="xxx">
</FORM>

<FORM method="POST" name="connectform" action="ftp-connect.dsp">
	<INPUT type="hidden" name="ftpalias" value=%value ftpalias%>
</FORM>


%endif%


</TABLE>
</TD></TR></TABLE>
</BODY>

</HTML>
