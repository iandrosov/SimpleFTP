package SimpleFTP;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-02-25 22:10:04 EST
// -----( ON-HOST: PC-WIN7-001

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.StringTokenizer;
import com.wm.util.coder.XMLCoder;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
// --- <<IS-END-IMPORTS>> ---

public final class ftp

{
	// ---( internal utility methods )---

	final static ftp _instance = new ftp();

	static ftp _newInstance() { return new ftp(); }

	static ftp _cast(Object o) { return (ftp)o; }

	// ---( server methods )---




	public static final void ftp_alias_add (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(ftp_alias_add)>> ---
		// @sigtype java 3.5
		// [i] field:0:required ftpalias
		// [i] field:0:required ftphost
		// [i] field:0:required serverport
		// [i] field:0:required user
		// [i] field:0:required password
		// [i] field:0:required directory
		// [i] field:0:required searchmask
		// [i] field:0:required targetdir
		// [i] field:0:required status
		// [i] field:0:required archivedir
		// [i] field:0:required archiveflag {"true","false"}
		// [i] field:0:required remotearchivedir
		// [i] field:0:required rmsource
		// [i] field:0:optional transfertype {"active","passive"}
		// [i] field:0:optional transfermode {"ascii","binary"}
		// [i] field:0:required runAsUser
		// [i] field:0:required procservice
		// [i] field:0:required invoketype
		// [o] field:0:required message
			IDataCursor idc = pipeline.getCursor();
		
			// Get input values
		   	idc.first( "ftpalias" );
			String ftp_alias = (String) idc.getValue();
			idc.first( "ftphost" );
			String ftp_host = (String) idc.getValue();
			idc.first( "serverport" );
			String ftp_port = (String) idc.getValue();
			if (ftp_port == null || ftp_port.equals(""))
				ftp_port = "21"; //Set default port 21
			idc.first( "transfertype" );
			String transfertype = (String) idc.getValue();
			idc.first( "transfermode" );
			String transfermode = (String) idc.getValue();
			idc.first( "user" );
			String ftp_user = (String) idc.getValue();
			idc.first( "password" );
			String ftp_pwd = (String) idc.getValue();
			idc.first( "directory" );
			String ftp_dir = (String) idc.getValue();
			idc.first( "searchmask" );
			String ftp_mask = (String) idc.getValue();
			idc.first( "targetdir" );
			String local_directory = (String) idc.getValue();
			idc.first( "status" );
			String status = (String) idc.getValue();
			idc.first( "archivedir" );
			String archive_directory = (String) idc.getValue();
			idc.first( "rmsource" );
			String remove_source = (String) idc.getValue();
			idc.first( "remotearchivedir" );
			String remote_archive_directory = (String) idc.getValue();
			idc.first( "archiveflag" );
			String archive_flag = (String) idc.getValue();
		
			/////////////////////////////////////////////////////        
			// Add fields for Services invoke
			idc.first( "runAsUser" );
			String runAsUser = (String) idc.getValue();
			idc.first( "procservice" );
			String procservice = (String) idc.getValue();
			idc.first( "invoketype" );
			String invoketype = (String) idc.getValue();	
			/////////////////////////////////////////////////////
		
			// test FTP server alias
			if (ftp_alias == null)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Ftp alias not defined");
				return;
			}
		
			// test FTP user id
			if (ftp_user == null)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Ftp user not defined - REQUIERED");
				return;
			}
			// test FTP server password
			if (ftp_pwd == null)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Ftp password not defined");
				return;
			}
		
			// test Archive directory
			if (archive_directory == null) //(archive_directory.length() == 0)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Archive directory is required.");
				return;
			}
		
			// test working directory
			if (local_directory == null) // || local_directory.length() == 0)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Working/target directory is required.");
				return;
			}
		
			// test FTP server host
			if (ftp_host == null)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Ftp server or host not defined");
				return;
			}
		
			Values ftp_config = null;
			Values val = null;
		
			XMLCoder coder = null;
			FileOutputStream ostream = null;
			ObjectOutputStream p = null;
		
			try
			{
				///////////////////////////////////////////////////
				// Encrypt ftp user password
				/*String serviceName = "COMMON_SYSTEM.encrypt:encrypt";
				IData idin = IDataFactory.create();
				IDataCursor idc_tmp = idin.getCursor();
				idc_tmp.first();
				idc_tmp.insertAfter("string",ftp_pwd);
				NSName nsName = NSName.create(serviceName);
				IData results = Service.doInvoke(nsName, idin);
		    		idc_tmp = results.getCursor();
		    		idc_tmp.first("result");		
				ftp_pwd = (String)idc_tmp.getValue();
				idc_tmp.destroy();
				*************************/
				coder = new XMLCoder();		
				FileInputStream istream = new FileInputStream(config_file);		
				ObjectInputStream in_stream = new ObjectInputStream(istream);
				// Get current FTP configurations
				ftp_config = coder.decode(in_stream);
				
				val = new Values();
				val.put("ftpalias", ftp_alias);
				val.put("ftphost", ftp_host);
				val.put("serverport", ftp_port);
				val.put("user",ftp_user);
				val.put("password", ftp_pwd);
				val.put("directory", ftp_dir);
				val.put("searchmask", ftp_mask);
				val.put("targetdir", local_directory );
				val.put("status", status );
				val.put("archivedir", archive_directory);
				val.put("remotearchivedir", remote_archive_directory);
				val.put("archiveflag", archive_flag);
				val.put("rmsource", remove_source);
				val.put("transfertype", transfertype);
				val.put("transfermode", transfermode);
				val.put("runAsUser", runAsUser);
				val.put("procservice", procservice);
				val.put("invoketype", invoketype);
		
				if (ftp_config == null)
				{
					ftp_config = new Values();
				}
				ftp_config.put(ftp_alias,val);
		
				ostream = new FileOutputStream(config_file);
				p = new ObjectOutputStream(ostream);
		
				// Save FTP alias
				coder.encode(p,ftp_config);
		
				////////////////////////////////////////////////////////////
				// Test or create directories working and archive if needed
		        	File dir = new File(local_directory);
		        	if (!dir.exists())
		        	{
		   			if (!dir.mkdirs())
					{
						idc.first();
						idc.insertAfter("message","ERROR - Working directory could not be created!");
						return;
					}
				}
		
		        	dir = new File(archive_directory);
		        	if (!dir.exists())
		        	{
		   			if (!dir.mkdirs())
					{
						idc.first();
						idc.insertAfter("message","ERROR - Archive directory could not be created!");
						return;
					}
				}
				// create working directory
		        	dir = new File(get_base_directory(local_directory) + File.separator + working_dir);
		        	if (!dir.exists())
		        	{
		   			if (!dir.mkdirs())
					{
						idc.first();
						idc.insertAfter("message","ERROR - Working directory could not be created!");
						return;
					}
				}
				dir = null;
			}
			catch(Exception e)
			{
				try
				{
					coder = new XMLCoder();
					val = new Values();
					val.put("ftpalias", ftp_alias);
					val.put("ftphost", ftp_host);
					val.put("serverport", ftp_port);
					val.put("user",ftp_user);
					val.put("password", ftp_pwd);
					val.put("directory", ftp_dir);
					val.put("searchmask", ftp_mask);
					val.put("targetdir", local_directory );
					val.put("status", status );
					val.put("archivedir", archive_directory);
					val.put("remotearchivedir", remote_archive_directory);
					val.put("archiveflag", archive_flag);
					val.put("rmsource", remove_source);
					val.put("transfertype", transfertype);
					val.put("transfermode", transfermode);
					val.put("runAsUser", runAsUser);
					val.put("procservice", procservice);
					val.put("invoketype", invoketype);
		
					ftp_config = new Values();
					ftp_config.put(ftp_alias,val);
		
					ostream = new FileOutputStream(config_file);
					p = new ObjectOutputStream(ostream);
			
					// Save FTP alias
					coder.encode(p,ftp_config);
			
		/*** REMOVE directory creation code ******************/
					// Test or create directories working and archive if needed
		        		File dir = new File(local_directory);
		        		if (!dir.exists())
		        		{
		   				if (!dir.mkdirs())
						{
							idc.first();
							idc.insertAfter("message","ERROR - Working directory could not be created!");
							return;
						}
		   	    		}
		
		        		dir = new File(archive_directory);
		        		if (!dir.exists())
		        		{
		   				if (!dir.mkdirs())
						{
							idc.first();
							idc.insertAfter("message","ERROR - Archive directory could not be created!");
							return;
						}
		   	    		}
		
					// create working directory
		        		dir = new File(get_base_directory(local_directory) + File.separator + working_dir);
		        		if (!dir.exists())
		        		{
		   				if (!dir.mkdirs())
						{
							idc.first();
							idc.insertAfter("message","ERROR - Default working directory could not be created!");
							return;
						}
					}
		
					dir = null;
		/***********************************************************/
					
				}
				catch(Exception ex)
				{
					
					throw new ServiceException(e.getMessage());
				}
			}
			// Setup output message
			idc.first();
			idc.insertAfter("message","New Alias was added.");
		
			idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void ftp_alias_delete (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(ftp_alias_delete)>> ---
		// @sigtype java 3.5
		// [i] field:0:required ftpalias
		// [o] field:0:required message
	IDataCursor idc = pipeline.getCursor();

	// Get input values
   	idc.first( "ftpalias" );
	String ftp_alias = (String) idc.getValue();
	String aliasName = "";
	
	Values ftp_config = null;
	Values recValue = null;
	Values outValues = null;
	Enumeration enum1 = null;

	XMLCoder coder = null;
	FileInputStream istream = null;
	ObjectInputStream in_stream = null;
	FileOutputStream ostream = null;
	ObjectOutputStream p = null;

	try
	{
		outValues = new Values();
		coder = new XMLCoder();		
		istream = new FileInputStream(config_file);		
		in_stream = new ObjectInputStream(istream);
		// Get current FTP configurations
		ftp_config = coder.decode(in_stream);
		if (ftp_config != null)
		{
			// Find FTP server requested
			enum1 = ftp_config.sortedKeys();
			while (enum1.hasMoreElements())
			{
				aliasName = (String)enum1.nextElement(); // Get alias name
				// If alias is found we will ignore it for DELETE
				if (!aliasName.equals(ftp_alias))
				{
					recValue = ftp_config.getValues(aliasName);
					if (recValue != null) 
					{
						// Save not deleted FTP configurations to new object
						outValues.put(aliasName,recValue);
					}
				}
			}
			// Save remainig configurations to a file
			ostream = new FileOutputStream(config_file);
			p = new ObjectOutputStream(ostream);		
			coder.encode(p,outValues);
		}	
		// Setup output message
		idc.first();
		idc.insertAfter("message","Alias - " + ftp_alias + " - was removed.");
	
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	// Clean objects
	coder = null;
	istream = null;
	in_stream = null;
	ftp_config = null;
	recValue = null;
	outValues = null;
	enum1 = null;
	ostream = null;
	p = null;

	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void ftp_alias_edit (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(ftp_alias_edit)>> ---
		// @sigtype java 3.5
		// [i] field:0:required ftpalias
		// [i] field:0:required ftphost
		// [i] field:0:required serverport
		// [i] field:0:required user
		// [i] field:0:required password
		// [i] field:0:required directory
		// [i] field:0:required searchmask
		// [i] field:0:required targetdir
		// [i] field:0:required status
		// [i] field:0:required archivedir
		// [i] field:0:required remotearchivedir
		// [i] field:0:required archiveflag {"true","false"}
		// [i] field:0:required rmsource
		// [i] field:0:optional transfertype {"active","passive"}
		// [i] field:0:optional transfermode {"ascii","binary"}
		// [i] field:0:required invoketype
		// [i] field:0:required procservice
		// [i] field:0:required runAsUser
		// [o] field:0:required message
		IDataCursor idc = pipeline.getCursor();
		
		String ftp_alias = "";
		String ftp_host = "";
		String ftp_port = "";
		String ftp_user = "";
		String ftp_pwd = "";
		String ftp_dir = "";
		String ftp_mask = "";
		String local_directory = "";
		String status = "";
		String archive_directory = "";
		String remote_archive_directory = "";
		String archive_flag = "";
		String remove_source = "";
		String transfertype = "";
		String transfermode = "";
		String runAsUser = "";
		String procservice = "";
		String invoketype = "";
		
			if (idc.first( "transfertype" ))
				transfertype = (String) idc.getValue();
			if (idc.first( "transfermode" ))
				transfermode = (String) idc.getValue();
		
			// Get input values
		   	if (idc.first( "ftpalias" ))
				ftp_alias = (String) idc.getValue();
			if (idc.first( "ftphost" ))
				ftp_host = (String) idc.getValue();
			if (idc.first( "serverport" ))
				ftp_port = (String) idc.getValue();
			if (ftp_port == null || ftp_port.equals(""))
				ftp_port = "21"; //Set default port 21
		
			if (idc.first( "user" ))
				ftp_user = (String) idc.getValue();
			if (idc.first( "password" ))
				ftp_pwd = (String) idc.getValue();
			if (idc.first( "directory" ))
				ftp_dir = (String) idc.getValue();
			if (idc.first( "searchmask" ))
				ftp_mask = (String) idc.getValue();
			if (idc.first( "targetdir" ))
				local_directory = (String) idc.getValue();
			if (idc.first( "status" ))
				status = (String) idc.getValue();
			if (idc.first( "archivedir" ))
				archive_directory = (String) idc.getValue();
			if (idc.first( "remotearchivedir" ))
				remote_archive_directory = (String) idc.getValue();
			if (idc.first( "archiveflag" ))
				archive_flag = (String) idc.getValue();
			if (idc.first( "rmsource" ))
				remove_source = (String) idc.getValue();
		
			if (idc.first( "runAsUser" ))
				runAsUser = (String) idc.getValue();
			if (idc.first( "procservice" ))
				procservice = (String) idc.getValue();
			if (idc.first( "invoketype" ))
				invoketype = (String) idc.getValue();
		
			// test FTP server alias
			if (ftp_alias == null)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Ftp alias not defined");
				return;
			}
		
			// test FTP user id
			if (ftp_user == null)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Ftp user not defined - REQUIERED");
				return;
			}
			// test FTP server password
			if (ftp_pwd == null)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Ftp password not defined");
				return;
			}
		
			// test Archive directory
			if (archive_directory == null) //(archive_directory.length() == 0)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Archive directory is required.");
				return;
			}
		
			// test working directory
			if (local_directory == null) // || local_directory.length() == 0)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Working/target directory is required.");
				return;
			}
			// test FTP server host
			if (ftp_host == null)
			{
				idc.first();
				idc.insertAfter("message","ERROR - Ftp server or host not defined");
				return;
			}
		
			Values ftp_config = null;
			Values val = null;
		
			XMLCoder coder = null;
			FileOutputStream ostream = null;
			ObjectOutputStream p = null;
		
			try
			{
				///////////////////////////////////////////////////
				// Encrypt ftp user password
				/*String serviceName = "COMMON_SYSTEM.encrypt:encrypt";
				IData idin = IDataFactory.create();
				IDataCursor idc_tmp = idin.getCursor();
				idc_tmp.first();
				idc_tmp.insertAfter("string",ftp_pwd);
				NSName nsName = NSName.create(serviceName);
				IData results = Service.doInvoke(nsName, idin);
		    		idc_tmp = results.getCursor();
		    		idc_tmp.first("result");		
				ftp_pwd = (String)idc_tmp.getValue();
				idc_tmp.destroy();
				**************************/
		
				coder = new XMLCoder();		
				FileInputStream istream = new FileInputStream(config_file);		
				ObjectInputStream in_stream = new ObjectInputStream(istream);
				// Get current FTP configurations
				ftp_config = coder.decode(in_stream);
		
				val = new Values();
				val.put("ftpalias", ftp_alias);
				val.put("ftphost", ftp_host);
				val.put("user",ftp_user);
				val.put("password", ftp_pwd);
				val.put("directory", ftp_dir);
				val.put("searchmask", ftp_mask);
				val.put("serverport", ftp_port);
				val.put("targetdir", local_directory);
				val.put("status", status);
				val.put("archivedir",archive_directory);
				val.put("remotearchivedir",remote_archive_directory);
				val.put("archiveflag",archive_flag);
				val.put("rmsource", remove_source);
				val.put("transfertype",transfertype);
				val.put("transfermode",transfermode);
				val.put("runAsUser", runAsUser);
				val.put("procservice",procservice);
				val.put("invoketype",invoketype);
		
				if (ftp_config == null)
				{
					ftp_config = new Values();
				}
				ftp_config.put(ftp_alias,val);
		
				ostream = new FileOutputStream(config_file);
				p = new ObjectOutputStream(ostream);
		
				// Save FTP alias
				coder.encode(p,ftp_config);
		
		/********************************************************************/
				// Test or create directories working and archive if needed
		        	File dir = new File(local_directory);
		        	if (!dir.exists())
		        	{
		   			if (!dir.mkdirs())
					{
						idc.first();
						idc.insertAfter("message","ERROR - Working directory could not be created!");
						return;
					}
		   	    	}
				// Test or create directories working and archive if needed
		        	dir = new File(archive_directory);
		        	if (!dir.exists())
		        	{
		   			if (!dir.mkdirs())
					{
						idc.first();
						idc.insertAfter("message","ERROR - Archive directory could not be created!");
						return;
					}
		   	    	}
				// create working directory
		        	dir = new File(get_base_directory(local_directory) + File.separator + working_dir);
		        	if (!dir.exists())
		        	{
		   			if (!dir.mkdirs())
					{
						idc.first();
						idc.insertAfter("message","ERROR - Default working directory could not be created!");
						return;
					}
				}
		
				dir = null;
		/*******************************************/
		
				// Setup output message
				idc.first();
				idc.insertAfter("message","Alias - " + ftp_alias + " was modified.");
			}
			catch(Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
			idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void ftp_alias_get (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(ftp_alias_get)>> ---
		// @sigtype java 3.5
		// [i] field:0:required ftpalias
		// [o] field:0:required ftpalias
		// [o] field:0:required ftphost
		// [o] field:0:required user
		// [o] field:0:required password
		// [o] field:0:required directory
		// [o] field:0:required searchmask
		// [o] field:0:required serverport
		// [o] field:0:required targetdir
		// [o] field:0:required status
		// [o] field:0:required archivedir
		// [o] field:0:required archiveflag {"true","false"}
		// [o] field:0:required remotearchivedir
		// [o] field:0:required rmsource
		// [o] field:0:optional transfertype {"active","passive"}
		// [o] field:0:optional transfermode {"ascii","binary"}
		// [o] field:0:required runAsUser
		// [o] field:0:required procservice
		// [o] field:0:required invoketype
		IDataCursor idc = pipeline.getCursor();
		
			// Get input values
		   	idc.first( "ftpalias" );
			String ftp_alias = (String) idc.getValue();
			String ftphost = "";
			String user = "";
			String password = "";
			String directory = "";
			String searchmask = "";
			String mask = "";
			String port = "";
			String target = "";
			String status = "";
			String archive = "";
			String remote_archive_dir = "";
			String archive_flag = "";
			String remove_source = "NO";
			String transfertype = "active";
			String transfermode = "ascii";
			String runAsUser = "";
			String procservice = "";
			String invoketype = "";
		
			Values ftp_config = null;
			Values recValue = null;
			Enumeration<String> enum1 = null;
		
			String aliasName = "";
		
			XMLCoder coder = null;
			FileInputStream istream = null;
			ObjectInputStream in_stream = null;
			try
			{
				coder = new XMLCoder();		
				istream = new FileInputStream(config_file);		
				in_stream = new ObjectInputStream(istream);
				// Get current FTP configurations
				ftp_config = coder.decode(in_stream);
				if (ftp_config != null)
				{
					enum1 = ftp_config.sortedKeys();
					while (enum1.hasMoreElements())
					{
						aliasName = (String)enum1.nextElement(); // Get alias name
						
						if (aliasName.equals(ftp_alias))
						{
							recValue = ftp_config.getValues(aliasName);
						}
					}
		
					if (recValue != null) 
					{
						enum1 = recValue.sortedKeys();
						while (enum1.hasMoreElements())
						{
							String name = (String)enum1.nextElement();
		
							if( name.compareTo("ftphost") == 0 )
								ftphost = recValue.getString("ftphost");
							if( name.compareTo("user") == 0 )
								user = recValue.getString("user");
							if( name.compareTo("password") == 0 )
								password = recValue.getString("password");
							if( name.compareTo("directory") == 0 )
								directory = recValue.getString("directory");
							if( name.compareTo("searchmask") == 0 )
								mask = recValue.getString("searchmask");
							if( name.compareTo("serverport") == 0 )
								port = recValue.getString("serverport");
							if( name.compareTo("targetdir") == 0 )
								target = recValue.getString("targetdir");
							if( name.compareTo("status") == 0 )
								status = recValue.getString("status");
							if( name.compareTo("archivedir") == 0 )
								archive = recValue.getString("archivedir");
							if( name.compareTo("remotearchivedir") == 0 )
								remote_archive_dir = recValue.getString("remotearchivedir");
							if( name.compareTo("archiveflag") == 0 )
								archive_flag = recValue.getString("archiveflag");
							if( name.compareTo("rmsource") == 0 )
								remove_source = recValue.getString("rmsource");
							if( name.compareTo("transfertype") == 0 )
								transfertype = recValue.getString("transfertype");
							if( name.compareTo("transfermode") == 0 )
								transfermode = recValue.getString("transfermode");
							if( name.compareTo("runAsUser") == 0 )
								runAsUser = recValue.getString("runAsUser");
							if( name.compareTo("procservice") == 0 )
								procservice = recValue.getString("procservice");
							if( name.compareTo("invoketype") == 0 )
								invoketype = recValue.getString("invoketype");
						}
		
		
						///////////////////////////////////////////////////
						// Dencrypt ftp user password
						/*String serviceName = "COMMON_SYSTEM.encrypt:decrypt";
						IData idin = IDataFactory.create();
						IDataCursor idc_tmp = idin.getCursor();
						idc_tmp.first();
						idc_tmp.insertAfter("string",password);
						NSName nsName = NSName.create(serviceName);
						IData results = Service.doInvoke(nsName, idin);
		    				idc_tmp = results.getCursor();
		    				idc_tmp.first("result");		
						password = (String)idc_tmp.getValue();
						idc_tmp.destroy();
						******************************/
		
					   	idc.first( "ftpalias" );
						idc.insertAfter("ftphost",ftphost);
						idc.insertAfter("user", user);
						idc.insertAfter("password", password.trim());
						idc.insertAfter("directory", directory );
						idc.insertAfter("searchmask", mask);			
						idc.insertAfter("serverport", port);			
						idc.insertAfter("targetdir", target);			
						idc.insertAfter("status", status);
						idc.insertAfter("archivedir", archive);	
						idc.insertAfter("remotearchivedir", remote_archive_dir);	
						idc.insertAfter("archiveflag", archive_flag);		
						idc.insertAfter("rmsource", remove_source);
						idc.insertAfter("runAsUser", runAsUser);
						idc.insertAfter("procservice", procservice);
						idc.insertAfter("invoketype", invoketype);
							
						///////////////////////////////////////
						// provide default value
						if (transfertype == null) // provide default value
							transfertype = "active";
		
						if (transfermode == null) // provide default value
							transfermode = "ascii";
		
			
						idc.insertAfter("transfertype", transfertype);
						idc.insertAfter("transfermode", transfermode);
					}
				}		
			}
			catch(Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
			// Clean objects
			coder = null;
			istream = null;
			in_stream = null;
			ftp_config = null;
			recValue = null;
			enum1 = null;
		
			idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void ftp_alias_list_get (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(ftp_alias_list_get)>> ---
		// @sigtype java 3.5
		// [o] recref:0:required DataSourceAlias SimpleFTP.ftp.rec:FTPDataSource
	IDataHashCursor idc = pipeline.getHashCursor();
	String ftp_alias = "";
	String ftphost = "";
	String user = "";
	String password = "";
	String directory = "";
	String searchmask = "";
	String mask = "";
	String port = "";
	String target = "";
	String status = "";
	String archive = "";
	String archive_flag = "";
	String remote_archive_dir = "";
	String remove_source = "N";
	String transfertype = "active";
	String transfermode = "ascii";
	String runAsUser = "";
	String procservice = "";
	String invoketype = "";

	Values ftp_config = null;
	Values recValue = null;
	Enumeration enum1 = null;
	Enumeration enum_data = null;
	// Output values
	Values data_source = null;
	Values data = null;
	Values[] data_source_list = null;
	
	XMLCoder coder = null;
	FileInputStream istream = null;
	ObjectInputStream in_stream = null;

	try
	{
		int count = 0;
		coder = new XMLCoder();		
		istream = new FileInputStream(config_file);		
		in_stream = new ObjectInputStream(istream);
		// Get current FTP configurations
		ftp_config = coder.decode(in_stream);
		if (ftp_config != null)
		{
			// create object to hole list of Aliases
			data_source = new Values();

			// Get number of values in this colection
			enum1 = ftp_config.sortedKeys();
			int size = 0;
			while (enum1.hasMoreElements())
			{
				ftp_alias = (String)enum1.nextElement(); // Get alias name				
				size++;
			}	
			data_source_list = new Values[size];

			enum1 = ftp_config.sortedKeys();
			while (enum1.hasMoreElements())
			{
				ftp_alias = (String)enum1.nextElement(); // Get alias name				
				recValue = ftp_config.getValues(ftp_alias);

				if (recValue != null) 
				{
					enum_data = recValue.sortedKeys();
					while (enum_data.hasMoreElements())
					{
						String name = (String)enum_data.nextElement();

						if( name.compareTo("ftphost") == 0 )
							ftphost = recValue.getString("ftphost");
						if( name.compareTo("user") == 0 )
							user = recValue.getString("user");
						if( name.compareTo("password") == 0 )
							password = recValue.getString("password");
						if( name.compareTo("directory") == 0 )
							directory = recValue.getString("directory");
						if( name.compareTo("searchmask") == 0 )
							searchmask = recValue.getString("searchmask");
						if( name.compareTo("serverport") == 0 )
							port = recValue.getString("serverport");
						if( name.compareTo("targetdir") == 0 )
							target = recValue.getString("targetdir");
						if( name.compareTo("status") == 0 )
							status = recValue.getString("status");
						if( name.compareTo("archivedir") == 0 )
							archive = recValue.getString("archivedir");
						if( name.compareTo("remotearchivedir") == 0 )
							remote_archive_dir = recValue.getString("remotearchivedir");
						if( name.compareTo("archiveflag") == 0 )
							archive_flag = recValue.getString("archiveflag");
						if( name.compareTo("rmsource") == 0 )
							remove_source = recValue.getString("rmsource");
						if( name.compareTo("transfertype") == 0 )
							transfertype = recValue.getString("transfertype");
						if( name.compareTo("transfermode") == 0 )
							transfermode = recValue.getString("transfermode");
						if( name.compareTo("runAsUser") == 0 )
							runAsUser = recValue.getString("runAsUser");
						if( name.compareTo("procservice") == 0 )
							procservice = recValue.getString("procservice");
						if( name.compareTo("invoketype") == 0 )
							invoketype = recValue.getString("invoketype");

						data = new Values();
						data.put("ftpalias",ftp_alias);
						data.put("ftphost",ftphost);
						data.put("user",user);
						data.put("password",password);
						data.put("directory",directory);
						data.put("searchmask",searchmask);
						data.put("serverport",port);
						data.put("targetdir",target);
						data.put("status",status);
						data.put("archivedir",archive);
						data.put("remotearchivedir",remote_archive_dir);
						data.put("archiveflag",archive_flag);
						data.put("rmsource",remove_source);
						if (transfertype == null) // provide default value
							transfertype = "active";
						data.put("transfertype",transfertype);
						if (transfermode == null) // provide default value
							transfermode = "ascii";
						data.put("transfermode",transfermode);
						data.put("runAsUser", runAsUser);
						data.put("procservice", procservice);
						data.put("invoketype", invoketype);
	

						data_source_list[count] = data;
					}
					count++;
				}

			}
	   		idc.first( );
			idc.insertAfter("DataSourceAlias", data_source_list);
		}		
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	// Clean objects
	coder = null;
	istream = null;
	in_stream = null;
	ftp_config = null;
	recValue = null;
	enum1 = null;
	enum_data = null;
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void get_working_dir (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_working_dir)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required local_dir
		// [i] field:0:required type {"INBOUND","OUTBOUND"}
		// [o] field:0:required working_dir
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	local_dir = IDataUtil.getString( pipelineCursor, "local_dir" );
			String	type = IDataUtil.getString( pipelineCursor, "type" );
		pipelineCursor.destroy();
		
		if (type == null)
		    type = "INBOUND"; //Default value
		
		String location = working_dir;
		if (type.equals("OUTBOUND"))
			location = work_out;
		String working = get_base_directory(local_dir) + File.separator + location;
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "working_dir", working );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	static String config_file = "packages/SimpleFTP/config/ftp.cnf";
	static String working_dir = "work_in";
	static String work_out = "work_out";
	
	static private String get_base_directory(String dir)
	{
		String str = "";
		String tmp = "";
		
		StringTokenizer st = new StringTokenizer(dir,File.separator);
		int count = st.countTokens();
			
		while (st.hasMoreTokens()) {
			tmp = st.nextToken();
			count--;
			if (count > 0)	
			{			
				str += tmp;
				if (count > 1)
			 		str += File.separator;
			}
	    }
		return str;
	}
	// --- <<IS-END-SHARED>> ---
}

