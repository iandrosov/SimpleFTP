<html>
<head>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
<META HTTP-EQUIV="Expires" CONTENT="-1">


<title>IS Task Scheduler</title>
<LINK REL="stylesheet" TYPE="text/css" HREF="/WmRoot/webMethods.css">
<SCRIPT SRC="/WmRoot/webMethods.js.txt"></SCRIPT>
</head>


%ifvar action%
%switch action%
%case 'add'%
	%invoke SimpleFTP.util.flow:gen%
	<TR><TH id="message" colspan=4>%value message%</TH></TR>
	%endinvoke%
%endswitch%
%endif action%

%switch mode%


%case sys%
<body onLoad="setNavigation('scheduler.dsp', 'doc/OnlineHelp/WmRoot.htm#CS_Server_Scheduler_SystemTasks.htm', 'foo');">
<!-- =========================== sys =========================== -->

%invoke wm.server.query:getTaskList%

<!-- Server tasks table -->
<table width="100%">

<tr><td class="menusection-Server" colspan=4>
    Server &gt;
    Scheduler
    </td></tr>

<tr><td colspan=2>
    <ul>
        <li><a href="scheduler.dsp">Return to Scheduler</a></li>
    </ul>
</td></tr>

    <tr>
      <td><img src="/WmRoot/images/blank.gif" height=10 width=10 border=0></td>
      <td>
        <table class="tableView" width=100%>
            %ifvar tasks%
            <tr><td class="heading" colspan=4>Simple Interval Tasks</td></tr>
            <TR>
              <td class="oddcol-l">Name</td>
              <td class="oddcol-l" colspan=2>Next Run</td>
              <td class="oddcol-r">Interval</td>
            </tr>
            %endif%
                <script>resetRows();</script>
            %loop tasks%
            <tr>
                <script>writeTD("rowdata-l");</script>%value name%</td>
                <script>writeTD("rowdata");</script><nobr>%value nextRun%</nobr></td>
                <script>writeTD("rowdata-r");</script><nobr>%value msDelta decimal(-3,1)% sec</nobr></td>
                <script>writeTD("rowdata-r");</script><nobr>%value interval decimal(-3,0)% sec</nobr></td>
            </tr>
                <script>swapRows();</script>
            %endloop%

            <tr><td class="space" colspan="4">&nbsp;</td></tr>

            <tr><td class="heading" colspan=4>Complex Repeating Tasks</td></tr>
            <TR>
              <td class="oddcol-l">Name</td>
              <td class="oddcol-l" colspan=2>Last Run</td>
              <td class="oddcol-r">Interval</td>
            </tr>
            %ifvar -notempty extTasks%
                <script>resetRows();</script>
            %loop extTasks%
            <tr>
                <script>writeTD("rowdata-l");</script>%value name%</td>
                <script>writeTD("rowdata-l");</script><nobr>%value msDelta%</nobr></td>
                <script>writeTD("rowdata-r");</script><nobr>%value msDelta decimal(-3,1)% sec</nobr></td>
                <script>writeTD("rowdata-r");</script><nobr>%value interval decimal(-3,0)% sec</nobr></td>
            </tr>
                <script>swapRows();</script>
            %endloop%
            %else%
            <tr><td class="evenrow-l" colspan=4>No tasks are currently scheduled</td></tr>
            %endif%

            </table>
          </td>
        </tr>
      </table>
    %endinvoe%



%case useradd%
<body onLoad="setNavigation('scheduler.dsp', 'doc/OnlineHelp/WmRoot.htm#CS_Server_Scheduler_NewTask.htm', 'foo');">


<!-- =========================== useradd =========================== -->

<!-- User tasks table -->

<SCRIPT LANGUAGE="JavaScript">

  function onAdd() {

    var type = "";

    // (1) validate service name
    var svc = document.schedule.service.value;
    var idx = svc.lastIndexOf(":");
    if (svc == "" || idx < 0 || idx > svc.length-1) {
      alert (
        "Specify service name in the form:\n\n"+
        "          folder.subfolder:service\n"
      );
      return false;
    }

    // (3) determine type of service to be added
    var typeList = document.schedule.type;
    document.schedule.schaction.value='Add Task';
    for (var i=0; i<typeList.length; i++) {
      if (typeList[i].checked) type = typeList[i].value;
    }

    // (4) check inputs for that type

    // (4a) one-time tasks need a date and time
    if (type == "once") {
      var d = document.schedule.date.value;
      var t = document.schedule.time.value;
      if (d == "") {
        alert (
          "Specify date in the form:\n\n"+
          "          YYYY/MM/DD\n"
        );
        return false;
      }
      if (t == "") {
        alert (
          "Specify time in the form:\n\n"+
          "          HH:MM:SS\n"
        );
        return false;
      }
    }

    // (4b) repeating tasks need an interval
    else if (type == "repeat") {
      var i = document.schedule.interval.value;
      if (i == "") {
        alert ("Specify interval (in seconds)");
        return false;
      }
    }

    // (4c) complex tasks don't need anything specified -- if
    // nothing is specified, they'll be run every minute
    else {
    }

    document.schedule.submit();
  }

  function onUpdate() {
    document.schedule.schaction.value='Update Task';
    document.schedule.submit();
  }

</SCRIPT>

<table width="100%">

%ifvar smode%
<tr><td class="menusection-Server" colspan=7>
    Server &gt;
    Scheduler &gt;
    User Tasks &gt;
    Modify Task</td></tr>
%else%
<tr><td class="menusection-Server" colspan=7>
    Server &gt;
    Scheduler &gt;
    User Tasks &gt;
    New Task</td></tr>
%endif%

  <form name="schedule" action="scheduler.dsp" method="POST">
    <tr>
      <td colspan=2>
        <ul>
          <li><a href="scheduler.dsp">Return to Scheduler</a></li>
        </ul>
      </td>
    </tr>

    <tr>
      <td><img src="/WmRoot/images/blank.gif" height=10 width=10 border=0></td>
      <td>
        <table class="tableForm">

        %invoke wm.server.schedule:getUserTask%

        <script type="text/javascript">
          var runas = "%value custom/runAsUser%";

          // define function to scroll through selections on
          // document.schedule.runAsUser
          function updateSelector () {
            var sel = document.schedule.runAsUser;
            for (var i=0; i<sel.options.length; i++) {
              if (sel.options[i].value == runas) sel.selectedIndex = i;
            }
          }
        </script>

        <tr><td class="heading" colspan=7>Service Information</td></tr>
        <tr>
          <td class="oddrow" colspan=2>folder.subfolder:service</td>
          <td class="oddrow-l" colspan=5><input name="service" size=40 value="%value new_service%"></input></td>
        </tr>
        <tr>
          <td class="evenrow" colspan=2>Run As User</td>
          <td class="evenrow-l" colspan=5>

          %invoke wm.server.access:userList%
          <SELECT NAME="runAsUser">
            %loop users%
            <OPTION value="%value name%"
            %ifvar name equals('Administrator')% selected %endif%
            >%value name%</OPTION>
            %endloop%
          </SELECT>

          <SCRIPT LANGUAGE="JavaScript">updateSelector();</SCRIPT>
          %endinvoke%
          </td>
        </tr>

        <tr>
          <td class="oddrow" colspan=2>Persistence</td>
          <td class="oddrow-l" colspan=5>
          <input name="persistJob" type="checkbox" value="true"
                 %ifvar custom/persistJob equals(true)%checked%endif%
               %ifvar custom/persistJob%%else%checked%endif%>
          Persist after restart</input>
          </td>
        </tr>


        <tr>
          <td class="evenrow" colspan=2>Clustering</td>
          <td class="evenrow-l" colspan=5>


          %ifvar inCluster%
          <input name="runInCluster" type="checkbox" value="true"
                 %ifvar custom/runInCluster equals(true)%checked%endif%>
          Scheduled for cluster</input>
          %else%
          Not in cluster.
          %endif%
          </td>
        </tr>



        %ifvar smode%

          %switch type%

          %case 'once'%
<tr><td class="subHeading" colspan=7>One-Time Tasks</td></tr>
%ifvar type equals(once)%<input name="type" type="hidden" value="once" >%endif%
            <tr>
              <td class="oddrow" colspan=1>Date</td>
              <td class="oddrow-l" colspan=6>
              <input name="date" size=12 value="%value date%"> YYYY/MM/DD</input>
              </td>
            </tr>
            <tr>
              <td class="evenrow" colspan=1>Time</td>
              <td class="evenrow-l" colspan=6>
              <input name="time" size=12 value="%value time%"> HH:MM:SS </input>
              </td>
            </tr>
          %case 'repeat'%
            <tr><td class="subHeading" colspan=7>Repeating Tasks With a Simple Interval</td></tr>
%ifvar type equals(repeat)%<input name="type" type="hidden" value="repeat" >%endif%
            <tr>
              <td class="oddrow" colspan=1>Interval</td>
              <td class="oddrow-l" colspan=6>
              <input name="interval" size=12 value="%value interval decimal(-3,0)%"> seconds</input><BR>
              </td>
            </tr>
      <tr>
        <td class="evenrow" colspan=1>Repeating</td>
        <td class="evenrow-l" colspan=6><input type="checkbox" name="doNotOverlap" size=12 value="true" %ifvar doNotOverlap equals('true')%checked%endif%> Repeat from end of invocation</input>
        </td>
      </tr>

          %case 'complex'%
<tr><td class="subHeading" colspan=7>Repeating Tasks with Complex Schedules</td></tr>
%ifvar type equals(complex)%<input name="type" type="hidden" value="complex">%endif%
            <tr>
              <td class="oddrow" >Start Date</td>
              <td class="oddrow-l" colspan=5>
              <input name="start-date" value="%value start-date%" size=12 > YYYY/MM/DD <i>(optional)</i></input>
              </td>
            </tr>
            <tr>
              <td class="evenrow" >Start Time</td>
              <td class="evenrow-l" colspan=6>
              <input name="start-time" value="%value start-time%" size=12 > HH:MM:SS <i>(optional)</i></input>
              </td>
            </tr>
            <tr>
              <td class="oddrow" >End Date</td>
              <td class="oddrow-l" colspan=6>
              <input name="end-date" value="%value end-date%" size=12 > YYYY/MM/DD <i>(optional)</i></input>
              </td>
            </tr>
            <tr>
              <td class="evenrow" >End Time</td>
              <td class="evenrow-l" colspan=6>
              <input name="end-time" value="%value end-time%" size=12 > HH:MM:SS <i>(optional)</i></input>
              </td>
            </tr>
            <tr>
              <td class="oddrow" rowspan="3" >Run Mask</td><td class="oddrow-l" >Months</td><td class="oddrow-l" >Days</td><td class="oddrow-l" >Weekly Days</td><td class="oddrow-l" >Hours</td><td class="oddrow-l" >Minutes</td>
            </tr>
            <tr>

              <td class="oddrow-l" >
              <select name=month size=7 multiple>
              %loop moMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
              </select>
              </td>
              <td class="oddrow-l">
              <select name="mo_day" size=7 multiple>
              %loop dayMoMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
              </select>
              </td>
              <td class="oddrow-l">
              <select name="wk_day" size=7 multiple>
              %loop dayWkMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
              </select>
              </td>
              <td class="oddrow-l">
              <select name="hour" size=7 multiple>
              %loop hourMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
              </select>
              </td>
              <td class="oddrow-l">
              <select name="min" size=7 multiple>
              %loop minMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
              </select>
              </td>
            </tr>
            <tr>
              <td class="oddrow" colspan=5>
              <i>Selecting no items is equivalent to selecting all items for a given list</i>
              </td>
            </tr>

          %endswitch%

        %else%
    <tr><td class="space" colspan="7">&nbsp;</td></tr>
    <tr><td class="heading" colspan=7>Schedule Type and Details</td></tr>
        <tr><td class="subHeading" colspan=7>One-Time Tasks</td></tr>
        <tr>
          <td class="oddrow-l" rowspan=2 valign="top">
            <input name="type" type="radio" value="once" %ifvar type equals(once)%checked%endif%>Run Once</input>
          </td>
          <td class="oddrow" colspan=1>Date</td>
          <td class="oddrow-l" colspan=5>
            <input name="date" size=12 value="%value date%"> YYYY/MM/DD</input>
          </td>
        </tr>
        <tr>
          <td class="evenrow" colspan=1>Time</td>
          <td class="evenrow-l" colspan=5>
              <input name="time" size=12 value="%value time%"> HH:MM:SS </input>
          </td>
        </tr>
        <tr><td class="subHeading" colspan=7>Repeating Tasks With a Simple Interval</td></tr>
        <tr>
          <td class="oddrow-l" rowspan="2" valign="top">
            <input name="type" type="radio" value="repeat" %ifvar type equals(repeat)%checked%endif%>Repeating</input>
          </td>
          <td class="oddrow" colspan=1>Interval</td>
          <td class="oddrow-l" colspan=5>
            <input name="interval" size=12 value="%value interval decimal(-3,0)%"> seconds</input><BR>

          </td>
    </tr>
    <tr>
    <td class="evenrow" colspan=1>Repeating</td>
          <td class="evenrow-l" colspan=5>
            <input type="checkbox" name="doNotOverlap" size=12 value="true"> Repeat from end of invocation</input>
          </td>

        </tr>


        <tr><td class="subHeading" colspan=7>Repeating Tasks with Complex Schedules</td></tr>
        <tr>
          <td valign="top" class="oddrow-l" rowspan=7 valign="top">
            <input  name="type" type="radio" value="complex" %ifvar type equals(complex)%checked%endif%>Complex Repeating</input>
          </td>
          <td class="oddrow">Start Date</td>
          <td class="oddrow-l" colspan=6>
          <input name="start-date" size=12 > YYYY/MM/DD <i>(optional)</i></input>
          </td>
        </tr>
        <tr>
          <td class="evenrow">Start Time</td>
          <td class="evenrow-l" colspan=5>
          <input name="start-time" size=12 > HH:MM:SS <i>(optional)</i></input>
          </td>
        </tr>
        <tr>
          <td class="oddrow">End Date</td>
          <td class="oddrow-l" colspan=6>
          <input name="end-date" size=12 > YYYY/MM/DD <i>(optional)</i></input>
          </td>
        </tr>
        <tr>
          <td class="evenrow">End Time</td>
          <td class="evenrow-l" colspan=6>
          <input name="end-time" size=12 > HH:MM:SS <i>(optional)</i></input>
          </td>
        </tr>
        <tr>
          <td class="oddrow" rowspan=3>Run Mask</td><td class="oddrow-l">Months</td><td class="oddrow-l">Days</td><td class="oddrow-l">Weekly Days</td><td class="oddrow-l">Hours</td><td class="oddrow-l">Minutes</td>
        </tr>
        <tr>
          <td class="oddrow-l">
            <select name=month size=7 multiple>
            %loop moMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
            </select>
          </td>
          <td class="oddrow-l">
            <select name="mo_day" size=7 multiple>
            %loop dayMoMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
            </select>
          </td>
          <td class="oddrow-l">
            <select name="wk_day" size=7 multiple>
            %loop dayWkMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
            </select>
          </td>
          <td class="oddrow-l">
            <select name="hour" size=7 multiple>
            %loop hourMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
            </select>
          </td>
          <td class="oddrow-l">
            <select name="min" size=7 multiple>
            %loop minMask%<option value="%value idx%" %value selected%>%value name%</option>%endloop%
            </select>
          </td>
        </tr>
        <tr>
          <td class="oddrow" colspan=5>
          <i>Selecting no items is equivalent to selecting all items for a given list</i>
          </td>
        </tr>

        %endif%

          <tr>
            <td class="action" colspan=7>
              <input type="hidden" name="oid" value="%value oid%"></input>
              <input type="hidden" name="schaction"></input>
              %ifvar smode%
              <input type="button" value="Update task" onclick="return onUpdate();"></input>
              %else%
              <input type="button" value="Save Tasks" onclick="return onAdd();"></input>
              %endif%
            </td>
          </tr>



        %endinvoke%

      </form>

    </table>
    </td>
  </tr>
</table>


%case%
<body onLoad="setNavigation('scheduler.dsp', 'doc/OnlineHelp/WmRoot.htm#CS_Server_Scheduler.htm', 'foo');">

<!-- =========================== user =========================== -->

<!-- User tasks table -->
<table width="100%">

  <tr>
    <td class="menusection-Server" colspan=2>
      Server &gt;
      Scheduler
    </td>
  </tr>

  %ifvar message%
      <tr><td colspan="2">&nbsp;</td></tr>
  <tr>
    <td colspan=2 class="message">%value message%</td>
  </tr>
  %endif%

    %ifvar schaction%
    %switch schaction%

    %case 'cancel'%
      <!-- =============== cancel =============== -->
      %invoke wm.server.schedule:cancelUserTask%
        <script>document.location.replace("scheduler.dsp?message=Cancelled%20Task%20ID:%20%value -urlencode oid%");</script>
      %onerror%
        <script>document.location.replace("scheduler.dsp?message=Error%20cancelling%20task:%20%value -urlencode errorMessage%");</script>
      %endinvoke%

    %case 'suspend'%
      <!-- =============== suspend =============== -->
      %invoke wm.server.schedule:suspendUserTask%
      <script>document.location.replace("scheduler.dsp?message=Suspended%20Task%20ID:%20%value -urlencode oid%");</script>
      %onerror%
      <script>document.location.replace("scheduler.dsp?message=Error%20suspending%20task:%20%value -urlencode errorMessage%");</script>
      %endinvoke%

    %case 'wakeup'%
      <!-- =============== wakeup =============== -->
      %invoke wm.server.schedule:wakeupUserTask%
      <script>document.location.replace("scheduler.dsp?message=Woke%20Up%20Task%20ID:%20%value -urlencode oid%");</script>
      %onerror%
      <script>document.location.replace("scheduler.dsp?message=Error%20waking%20up%20task:%20%value -urlencode errorMessage%");</script>
      %endinvoke%

    %case 'Add Task'%
      <!-- =============== Add Task =============== -->
      %invoke wm.server.schedule:addTask%
        <script>document.location.replace("scheduler.dsp?message=Added%20new%20task%20ID:%20%value -urlencode oid%");</script>
      %onerror%
      <script>document.location.replace("scheduler.dsp?message=Error%20scheduling%20task:%20%value -urlencode errorMessage%");</script>
      %endinvoke%

    %case 'Update Task'%
      <!-- =============== Update Task =============== -->
      %invoke wm.server.schedule:updateTask%
      <script>document.location.replace("scheduler.dsp?message=Updated%20task%20ID:%20%value -urlencode oid%");</script>
      %onerror%
      <script>document.location.replace("scheduler.dsp?message=Error%20updating%20task:%20%value -urlencode errorMessage%");</script>
      %endinvoke%

    %endswitch%
    %endif%

    <tr>
      <td colspan=2>
        <ul>
          <li><a href="scheduler.dsp?mode=sys">View system tasks</a></li>
          <li><a href="scheduler.dsp?mode=useradd">Create a scheduled task</a></li>
        </ul>
      </td>
    </tr>

    <tr>
      <td><img src="/WmRoot/images/blank.gif" height=10 width=10 border=0></td>
      <td>
        <table class="tableView" width=100%>
          %invoke wm.server.schedule:getUserTaskList%

          <tr><td class="heading" colspan=7>One-Time and Simple Interval Tasks</td></tr>
          <TR>
            <td class="oddcol">Service</td>
            <td class="oddcol" nowrap>Run As User</td>
            <td class="oddcol" colspan=2>Interval</td>
            <td class="oddcol">Next Run</td>
            <td class="oddcol">Active</td>
            <td class="oddcol">Remove</td>
          </tr>
          %ifvar -notempty tasks%
              <script>resetRows();</script>
          %loop tasks%
          <tr>
              <script>writeTD("rowdata-l");</script>
                  <a href="scheduler.dsp?mode=useradd&oid=%value oid%&smode=Edit">%value name%</a></td>
              <script>writeTD("rowdata-l");</script>
                  %value custom/runAsUser%</td>
              <script>writeTDspan("rowdata-l","2");</script>
                  %ifvar interval equals(0)%once%else%%value interval decimal(-3,1)% sec%endif%</td>
            %ifvar execState equals('suspended')%
              <script>writeTD("rowdata");</script>
                  --</td>
              <script>writeTD("rowdata");</script>
                  <a href="scheduler.dsp?schaction=wakeup&oid=%value oid%" onclick="return confirm('Are you sure you want to activate this task?');">Suspended</a></td>
            %else%
              <script>writeTD("rowdata-l");</script>
                  %value msDelta decimal(-3,1)% sec</td>
              <script>writeTD("rowdata");</script>
                  <a class="imagelink"  href="scheduler.dsp?schaction=suspend&oid=%value oid%" onclick="return confirm('Are you sure you want to suspend task?');"><img src="/WmRoot/images/green_check.gif" border=0>Active</a></td>
            %endif%

              <script>writeTD("rowdata");</script>
                  <a class="imagelink" href="scheduler.dsp?schaction=cancel&oid=%value oid%" onclick="return confirm('Are you sure you want to remove this task?');"><img src="/WmRoot/icons/delete.gif" border=0></a></td>
          </tr>
              <script>swapRows();</script>
          %endloop%
          %else%
          <tr><td class="evenrow-l" colspan=7>No tasks are currently scheduled</td></tr>

          %endif%

          <tr><td class="space" colspan="7">&nbsp;</td></tr>

          <tr><td class="heading" colspan=7>Complex Repeating Tasks</td></tr>
          <TR>
            <td class="oddcol">Service</td>
            <td class="oddcol" nowrap>Run As User</td>
            <td class="oddcol" colspan=2>Interval Masks</td>
            <td class="oddcol">Last Run</td>
            <td class="oddcol">Active</td>
            <td class="oddcol">Remove</td>
          </tr>
          %ifvar -notempty extTasks%
              <script>resetRows();</script>
          %loop extTasks%
          <tr>
              <script>writeTD("rowdata-l");</script>
                  <a href="scheduler.dsp?mode=useradd&oid=%value oid%&smode=Edit">%value name%</a></td>
              <script>writeTD("rowdata-l");</script>
                  %value custom/runAsUser%</td>
	      <TD class="rowdata" colspan="2" style="padding: 0px;">
								<table width="100%" class="tableInline" cellspacing="1" style="background-color: #ffffff">
									<tr>
										<script>writeTD("row");</script>
											Months
										</td>
										<script>writeTD("rowdata-l");</script>
		    %ifvar monthMaskAlt%
		    %value monthMaskAlt%
		    %else%
		    January..December
		    %endif%
										</td>
									</tr>
									<tr>
										<script>writeTD("row");</script>
											Days
                  	</td>
										<script>writeTD("rowdata-l");</script>
		    %ifvar dayOfMonthMaskAlt%
		    %value dayOfMonthMaskAlt%
		    %else%
		    1..31
		    %endif%
										</td>
									</tr>
									<tr>
										<script>writeTD("row");</script>
											Days&nbsp;of Week
										</td>
										<script>writeTD("rowdata-l");</script>
		    %ifvar dayOfWeekMaskAlt%
		    %value dayOfWeekMaskAlt%
		    %else%
		    Monday..Sunday
		    %endif%
										</td>
									</tr>
									<tr>
										<script>writeTD("row");</script>
                  		Hours
                  	</td>
										<script>writeTD("rowdata-l");</script>
		    %ifvar hourMaskAlt%
		    %value hourMaskAlt%
		    %else%
		    0..23
		    %endif%
										</td>
									</tr>
									<tr>
										<script>writeTD("row");</script>
											Minutes
                  	</td>
										<script>writeTD("rowdata-l");</script>
		    %ifvar minuteMaskAlt%
		    %value minuteMaskAlt%
		    %else%
		    0..59
		    %endif%
										</td>
									</tr>
								</table>
							</td>
							<script>writeTD("rowdata-l");</script>
                  %ifvar neverRun%never%else%%value msDelta decimal(-3,1)% sec%endif%</td>
            %ifvar execState equals('suspended')%
              <script>writeTD("rowdata");</script>
                  <a href="scheduler.dsp?schaction=wakeup&oid=%value oid%" onclick="return confirm('Are you sure you want to activate this task?');">Suspended</a></td>
            %else%
              <script>writeTD("rowdata");</script>
                  <a class="imagelink" href="scheduler.dsp?schaction=suspend&oid=%value oid%" onclick="return confirm('Are you sure you want to suspend this task?');"><img src="/WmRoot/images/green_check.gif" border=0>Active</a></td>
            %endif%

              <script>writeTD("rowdata");</script>
                  <a class="imagelink" href="scheduler.dsp?schaction=cancel&oid=%value oid%" onclick="return confirm('Are you sure you want to remove this task?');"><img src="/WmRoot/icons/delete.gif" border=0></a></td>
          </tr>
              <script>swapRows();</script>
          %endloop%
          %else%
          <tr><td class="evenrow-l" colspan=7>No tasks are currently scheduled</td></tr>
          %endif%

          %endinvoke%

          </table>
      %endswitch%

        </td>
      </tr>
    </table>



</body>
</html>

