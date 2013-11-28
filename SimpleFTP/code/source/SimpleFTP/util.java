package SimpleFTP;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-02-25 16:18:05 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.util.*;
import java.text.*;
import com.wm.app.b2b.server.ServerAPI;
import com.wm.lang.ns.NSName;
import com.wm.app.b2b.server.ServiceThread;
// --- <<IS-END-IMPORTS>> ---

public final class util

{
	// ---( internal utility methods )---

	final static util _instance = new util();

	static util _newInstance() { return new util(); }

	static util _cast(Object o) { return (util)o; }

	// ---( server methods )---




	public static final void ftp_log (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(ftp_log)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required msg
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	msg = IDataUtil.getString( pipelineCursor, "msg" );
		pipelineCursor.destroy();
		String logfile = "simpleftp";
		try 
		{
			File fl = ServerAPI.getPackageConfigDir("SimpleFTP");
			String config_dir = fl.getPath();
			config_dir += File.separator + config_file;
			Properties config = new Properties();
			InputStream in_stream = (InputStream) new FileInputStream(config_dir);
			config.load(in_stream);
			in_stream.close();
		
			// Stop here
			String ftp_flag = config.getProperty("ftp.log");
			if (ftp_flag.equals("false"))
			    return;
		
		        // Get file name
		        Date currentTime = new Date();
		        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		        logfile += formatter.format(currentTime) + ".log";
		
			String log_path = config.getProperty("ftp.log.dir");	
		
			if (log_path == null)
			    log_path = "logs" + File.separator + logfile;
			else if (log_path.length() == 0)
			    log_path = "logs" + File.separator + logfile;
			else
			{
			    log_path += File.separator + logfile;
			}
			
			// Write out a file
		        formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss SSS");
		        String strTime = formatter.format(currentTime);
			String str = "["+strTime+"]---" + msg + "\r\n";
		        FileWriter fw = new FileWriter(log_path, true);
		        fw.write(str);
		        fw.close();
		        fw = null;
		}
		catch(Exception e)
		{
		throw new ServiceException(e.getMessage());
		}
		// --- <<IS-END>> ---

                
	}



	public static final void generate_service (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generate_service)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required svc_type {"INBOUND","OUTBOUND"}
		// [i] field:0:required alias
		// [i] field:0:required svc_name
		// [i] field:0:required folder
		// [i] field:0:required package
		// [o] field:0:required new_service
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	svc_type = IDataUtil.getString( pipelineCursor, "svc_type" );
			String	alias = IDataUtil.getString( pipelineCursor, "alias" );
			String	svc_name = IDataUtil.getString( pipelineCursor, "svc_name" );
			String	folder = IDataUtil.getString( pipelineCursor, "folder" );
			String	package_name = IDataUtil.getString( pipelineCursor, "package" );
		pipelineCursor.destroy();
		
		String ftp_service = "";
		if (svc_type.equals("INBOUND"))
			ftp_service = "SimpleFTP.schedule:ftp_transport_get"; // Inbound FTP GET
		else
			ftp_service = "SimpleFTP.schedule:ftp_transport_put"; // Outbound FTP PUT
		
		///////////////////////////////////////
		// Build FLOW services XML document
		String flow_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n";
		flow_xml += "<FLOW VERSION=\"3.0\" CLEANUP=\"true\">\n\n";
		flow_xml += "  <!-- nodes -->\n\n";
		flow_xml += "<INVOKE TIMEOUT=\"\" ";
		flow_xml += "SERVICE=\"" + ftp_service + "\" VALIDATE-IN=\"$none\" VALIDATE-OUT=\"$none\">\n";
		flow_xml += "  <COMMENT></COMMENT>\n\n  <!-- nodes -->\n\n";
		flow_xml += "<MAP MODE=\"INPUT\">\n  <MAPTARGET>\n\n";
		flow_xml += "<Values version=\"2.0\">\n  <record name=\"xml\" javaclass=\"com.wm.util.Values\">\n";
		flow_xml += "    <value name=\"node_type\">record</value>\n";
		flow_xml += "    <value name=\"field_type\">record</value>\n";
		flow_xml += "    <value name=\"field_dim\">0</value>\n";
		flow_xml += "    <value name=\"nillable\">true</value>\n";
		flow_xml += "    <array name=\"rec_fields\" type=\"record\" depth=\"1\">\n";
		flow_xml += "      <record javaclass=\"com.wm.util.Values\">\n";
		flow_xml += "        <value name=\"node_type\">record</value>\n";
		flow_xml += "        <value name=\"field_name\">ftpalias</value>\n";
		flow_xml += "        <value name=\"field_type\">string</value>\n";
		flow_xml += "        <value name=\"field_dim\">0</value>\n";
		flow_xml += "        <value name=\"nillable\">true</value>\n";
		flow_xml += "      </record>\n    </array>\n  </record>\n</Values>\n</MAPTARGET>\n  <MAPSOURCE>\n\n";
		flow_xml += "<Values version=\"2.0\">\n";
		flow_xml += "  <record name=\"xml\" javaclass=\"com.wm.util.Values\">\n";
		flow_xml += "    <value name=\"node_type\">record</value>\n";
		flow_xml += "    <value name=\"field_type\">record</value>\n";
		flow_xml += "    <value name=\"field_dim\">0</value>\n";
		flow_xml += "    <value name=\"nillable\">true</value>\n";
		flow_xml += "    <array name=\"rec_fields\" type=\"record\" depth=\"1\">\n";
		flow_xml += "    </array>\n  </record>\n</Values>\n</MAPSOURCE>\n\n  <!-- nodes -->\n\n";
		flow_xml += "<MAPSET NAME=\"Setter\" OVERWRITE=\"true\" VARIABLES=\"false\" FIELD=\"/ftpalias;1;0\">\n";
		flow_xml += "  <DATA ENCODING=\"XMLValues\" I18N=\"true\">\n\n";
		flow_xml += "<Values version=\"2.0\">\n";
		flow_xml += "  <value name=\"xml\">" + alias + "</value>\n";
		flow_xml += "  <record name=\"type\" javaclass=\"com.wm.util.Values\">\n";
		flow_xml += "    <value name=\"node_type\">record</value>\n";
		flow_xml += "    <value name=\"field_name\">ftpalias</value>\n";
		flow_xml += "    <value name=\"field_type\">string</value>\n";
		flow_xml += "    <value name=\"field_dim\">0</value>\n";
		flow_xml += "    <value name=\"nillable\">true</value>\n";
		flow_xml += "  </record>\n</Values>\n</DATA>\n</MAPSET>\n</MAP>\n\n";
		flow_xml += "<MAP MODE=\"OUTPUT\">\n</MAP>\n</INVOKE>\n</FLOW>\n";
		///////////////////////////////////////
		
		String svc_path = get_svc_path(svc_name,folder,package_name);
		System.out.println(svc_path);
		write_flow_xml(flow_xml, svc_path);
		
		String svc_ifc = folder + ":" + svc_name;
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "new_service", svc_ifc );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getPropertyList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getPropertyList)>> ---
		// @sigtype java 3.5
		// [i] field:0:required file
		// [o] record:1:required value_list
		// [o] - field:0:required propertyname
		// [o] - field:0:required propertyvalue
	IDataHashCursor idc = pipeline.getHashCursor();	

	String property_name = "";
	idc.first("file");
	String file_name = (String)idc.getValue();

	Values data = null;
	Values[] property_list = null;

	try 
	{
		String str = "";
		Properties config = new Properties();
		InputStream in_stream = (InputStream) new FileInputStream(file_name);
		config.load(in_stream);
		// Get property name list
		Enumeration enum = config.propertyNames();
		int count = 0;
		while (enum.hasMoreElements())
		{
			property_name = (String)enum.nextElement();
			count++;
		}
		enum = config.propertyNames();
		property_list = new Values[count];
		int i = 0;
		while (enum.hasMoreElements())
		{
			property_name = (String)enum.nextElement();
			str = config.getProperty(property_name);
			data = new Values();
			data.put("propertyname",property_name);
			data.put("propertyvalue",str);
			property_list[i] = data;
			i++;
        }

		idc.insertAfter("value_list",property_list);

		// clean up
		config = null;
		in_stream.close();
		in_stream = null;
		enum = null;
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void get_file_name (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_file_name)>> ---
		// @sigtype java 3.5
		// [i] field:0:required file_name
		// [i] field:0:required path
		// [i] field:0:optional type {"LOCAL","FTP"}
		// [o] field:0:required value
   	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first( "file_name" );
	String file_name = (String) idc.getValue();
	idc.first( "path" );
	String path = (String) idc.getValue();

	idc.first("type");
	String type = (String) idc.getValue();
	if (type == null)
		type = "LOCAL"; //Default

	file_name = file_name.replace('#','0');
	file_name = file_name.replace('&','0');
	String full_path = path;
	if (type.equals("FTP"))
		full_path += "/";
	else
		full_path += File.separator;
	full_path += file_name;

	idc.first( "file" );
	idc.insertAfter("value",full_path);
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void get_ftp_code (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_ftp_code)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required ftp_code
		// [o] field:0:required result
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	ftp_code = IDataUtil.getString( pipelineCursor, "ftp_code" );
		pipelineCursor.destroy();
		
		String result = "false";
		//String config_file = "ftp_settings.cnf";
		
		try 
		{
			File fl = ServerAPI.getPackageConfigDir("SimpleFTP");
			String config_dir = fl.getPath();
			config_dir += File.separator + config_file;
			Properties config = new Properties();
			InputStream in_stream = (InputStream) new FileInputStream(config_dir);
			config.load(in_stream);
			in_stream.close();
		
			String str = config.getProperty("ftp.return.code.list");	
			StringTokenizer st = new StringTokenizer(str,",");
		     	while (st.hasMoreTokens()) {				
				if (ftp_code.equals(st.nextToken()))
				    result = "true";
		     	}
		
			// clean up
			config = null;
			in_stream = null;
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
			
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result);
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void get_package_config_dir (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_package_config_dir)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional pkg
		// [o] field:0:required pkg_config
		// [o] field:1:required config_file_list
		 	IDataHashCursor idc = pipeline.getHashCursor();
		
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	key = IDataUtil.getString( pipelineCursor, "pkg" );
		pipelineCursor.destroy();
			// If package name is not provided use default package where services located
			if (key == null)
			{	
				Values vl = ValuesEmulator.getValues(pipeline, key);
				key = Service.getPackageName(vl);
			}
			String pkg = key;
			File fl = ServerAPI.getPackageConfigDir(pkg);
			String config_dir = fl.getPath();
		
			try
			{	
				// Get list of files in a give directory
		        File fname = new File(config_dir);
		        String[] file_list = fname.list();
				fname = null;
			   	idc.first();
				idc.insertAfter("config_file_list", file_list);
			}
			catch(Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
		
			// Setup output message
			idc.first();
			idc.insertAfter("pkg_config",config_dir);
		
			idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void get_property_list (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_property_list)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required file
		IDataHashCursor idc = pipeline.getHashCursor();	
		
			String property_name = "";
			idc.first("file");
			String file_name = (String)idc.getValue();
		
			Values data = null;
			Values[] property_list = null;
		
			try 
			{
				String str = "";
				Properties config = new Properties();
				InputStream in_stream = (InputStream) new FileInputStream(file_name);
				config.load(in_stream);
				// Get property name list
				Enumeration enum = config.propertyNames();
				int count = 0;
				while (enum.hasMoreElements())
				{
					property_name = (String)enum.nextElement();
					count++;
				}
				enum = config.propertyNames();
				property_list = new Values[count];
				int i = 0;
				while (enum.hasMoreElements())
				{
					property_name = (String)enum.nextElement();
					str = config.getProperty(property_name);
					data = new Values();
					data.put(property_name,str);
					idc.insertAfter(property_name,str);
					property_list[i] = data;
					i++;
		        }
		
				//idc.insertAfter("value_list",property_list);
		
				// clean up
				config = null;
				in_stream.close();
				in_stream = null;
				enum = null;
			}
			catch(Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
			idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void is_proc_service (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(is_proc_service)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required procservice
		// [o] field:0:required start_svc
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	procservice = IDataUtil.getString( pipelineCursor, "procservice" );
		pipelineCursor.destroy();
		String start_svc = "false";
		if (procservice != null)
		    start_svc = "true";
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "start_svc", start_svc );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void list_file (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(list_file)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required dirpath
		// [i] field:0:optional dateformat
		// [o] record:1:required DirList
		// [o] - field:0:required sortkey
		// [o] - field:0:required filename
		// [o] - field:0:required size
		// [o] - field:0:required time
		// [o] - field:0:required dir
		// [o] - field:0:required isfile
		IDataHashCursor idc = pipeline.getHashCursor();
		
			// Get input values
		   	idc.first( "dirpath" );
			String dir = (String) idc.getValue();
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String pattern = IDataUtil.getString( pipelineCursor, "dateformat" );
		pipelineCursor.destroy();
		
			// Provide default value for date pattern
			if (pattern == null)
				pattern = "MMddyyyyHHmmSS";
		
			// Output values
			Values data_source = null;
			Values data = null;
			Values[] directory_list = null;
			Date date = null;
			int file_ext = 4; //Default file extension
		
		try
		{	
				// Get list of files in a give directory
		        File fname = new File(dir);
		        String[] file_list = fname.list();
		        String ff = "";
		
			// Find all files in a given location and return names and size
			int count = 0;
		        for (int i = 0; i < file_list.length; i++)
		        {
		            ff = dir;
		            ff += File.separator;
		            ff += file_list[i];
			    fname = null;
		            fname = new File(ff);
		            if (fname.isFile())				
				count++;
			}
		
			directory_list = new Values[count];
			count = 0; // reset count for reuse
		        for (int i = 0; i < file_list.length; i++)
		        {
		            ff = dir;
		            ff += File.separator;
		            ff += file_list[i];
			    fname = null;
		            fname = new File(ff);
		
		            if (fname.isFile())
			    {				
				data = new Values();
				// Extract sort key from format EIBContact_1-2I2TQ_11162003210633.xml
				// format correct date
				int len = fname.getName().length();
				if (len > (pattern.length()+ file_ext))
				{
					try
					{
						String date_time = fname.getName().substring(len-(pattern.length()+file_ext), len-file_ext);
						
						// Normilize date time stamp for sorting yyyyMMddHHmmSS format
						SimpleDateFormat df = new SimpleDateFormat(pattern);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");		
						Date d = df.parse(date_time);
						date_time = sdf.format(d);
						df = null;
						sdf = null;
						d = null;
						data.put("sortkey",date_time);
					}
					catch(Exception e)
					{
						data.put("sortkey","");
					}
				}
				else
				{
					data.put("sortkey","");
		
					data.put("filename",fname.getName());
					data.put("size",fname.length());
					date = new Date(fname.lastModified());
					data.put("time",date.toString());
					data.put("dir",dir);	
					date = null;
					data.put("isfile","YES");
		
					directory_list[count] = data;
					count++;           	
				}	
			    }		
		        }
		
			fname = null;
			date = null;
			idc.first();
			idc.insertAfter("DirList",directory_list);
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void move (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(move)>> ---
		// @sigtype java 3.5
		// [i] field:0:required source
		// [i] field:0:required target
		// [o] field:0:required status
   	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first( "source" );
	String source = (String) idc.getValue();
   	idc.first( "target" );
	String target = (String) idc.getValue();
	
	try
	{
		File source_file = new File(source);
		source_file.renameTo(new File(target));
		source_file = null;
	
	   	idc.first();
		idc.insertAfter("status","SUCCESS");
	}
	catch(Exception e)
	{
	   	idc.first();
		idc.insertAfter("status","ERROR");		
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void move_local_file (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(move_local_file)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required source
		// [i] field:0:required target
		// [o] field:0:required status
		IDataHashCursor idc = pipeline.getHashCursor();
		
			// Get input values
		   	idc.first( "source" );
			String source = (String) idc.getValue();
		   	idc.first( "target" );
			String target = (String) idc.getValue();
			
			try
			{
				File source_file = new File(source);
				source_file.renameTo(new File(target));
				source_file = null;
			
			   	idc.first();
				idc.insertAfter("status","SUCCESS");
			}
			catch(Exception e)
			{
			   	idc.first();
				idc.insertAfter("status","ERROR");		
				throw new ServiceException(e.getMessage());
			}
			idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void remove (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(remove)>> ---
		// @sigtype java 3.5
		// [i] field:0:required file

   	IDataHashCursor idc = pipeline.getHashCursor();
	// Get input values
   	idc.first( "file" );
	String source = (String) idc.getValue();

	try
	{
		File source_file = new File(source);
		if (source_file != null)
		{
			if (source_file.isFile())
			{
				source_file.delete();
			}
		}
		source_file = null;
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void run_service (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(run_service)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required service
		// [i] field:0:required invoketype
		// [i] field:0:required file_name
		IDataHashCursor idc = pipeline.getHashCursor();
		    idc.first( "service" );
		    String svc_name = (String) idc.getValue();
		    idc.first( "invoketype" );
		    String invoketype = (String) idc.getValue();
		
		    try
		    {
			NSName nsName = NSName.create(svc_name);
			if (invoketype.equals("true"))
			{		
			    ServiceThread boo = Service.doThreadInvoke(nsName, pipeline);
			    boo.getData();
			}
			else
			{
			    Service.doInvoke(nsName, pipeline);
			}
		    }
		    catch (Exception e)
		    {
			throw new ServiceException(e.getMessage());
		    }
		idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void setPropertyList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(setPropertyList)>> ---
		// @sigtype java 3.5
		// [i] field:0:required file
	IDataCursor idc = pipeline.getCursor();	

	idc.first("file");
	String file_name = (String)idc.getValue();
	
	try
	{
			String key = "";
			String val = "";
			// Get properties from a given file
			Properties config = new Properties();
			InputStream in_stream = (InputStream) new FileInputStream(file_name);
			config.load(in_stream);
			// Loop over pipeline and get all property values and names
			idc.first();
			String temp = "";
			while (idc.hasMoreData())
			{
				key = idc.getKey();
				if (key.length() >= 4)
					temp = key.substring(key.length() - 4, key.length());
		
				if (!key.equals("file") && !key.equals("file_name") &&
				!key.equals("action") && !temp.equals("List"))
				{
					val = (String)idc.getValue();
					config.setProperty(key, val);
				}
				idc.next();
			}
			// Stor properties to file
			OutputStream out = (OutputStream) new FileOutputStream(file_name);
			config.store(out, "update property");
			out.flush();
			out.close();
			
			idc.first();
			idc.insertAfter( "message", "Set/update property file - " + file_name );
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static String config_file = "ftp_settings.cnf";
	
	private static String get_svc_path(String svc_name,String folder,String pkg)
	{
		String str = "packages/" + pkg + "/ns/";
		str += get_folder(folder);
		str += "/" + svc_name + "/flow.xml";
		return str;
	}
	
	private static void write_flow_xml(String flow, String path)
	{
	try {
		FileWriter fw = new FileWriter(path, false);
		fw.write(flow);    
		fw.close();
		fw = null;
	}
	catch(Exception e)
	{}
	}
	
	private static String get_folder(String str)
	{
		String res = str.replace('.','/');
		String tmp = res.replace(':','/');
		return tmp;
	}
	// --- <<IS-END-SHARED>> ---
}

