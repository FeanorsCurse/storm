/**
* Logging Flex and Flash projects using Firebug and ThunderBolt AS3
* 
* @version	1.0
* @author	Jens Krause [www.websector.de]
* @date		10/14/07
* @see		http://www.websector.de/blog/category/thunderbolt/
* @see		http://code.google.com/p/flash-thunderbolt/
* @source	http://flash-thunderbolt.googlecode.com/svn/trunk/as3/
* 
* ***********************
* HAPPY LOGGING ;-)
* ***********************
* 
*/

package org.osflash.thunderbolt
{
	import flash.external.ExternalInterface;
	import flash.system.System;
	import flash.utils.describeType;
	
	/**
	* Thunderbolts AS3 Logger class
	*/
	
	public class Logger
	{
		// constants
		// Firebug supports 4 log levels only
		public static const INFO: String = "info";
		public static const WARN: String = "warn";
		public static const ERROR: String = "error";
		public static const LOG: String = "log";

		private static const FIELD_SEPERATOR: String = " :: ";		
		private static const MAX_DEPTH: int = 255;
		private static const VERSION: String = "1.0";
		private static const AUTHOR: String = "Jens Krause [www.websector.de]"
		//
		// private vars	
		private static var _stopLog: Boolean = false;
		private static var _depth: int;	
		private static var _logLevel: String;						
		//
		// public vars
		public static var includeTime: Boolean = true;
		private static var _hide: Boolean = false;
		
		/**
		 * Information about the current version of ThunderBoltAS3
		 *
		 */		 
		public static function about():void
	    {
	        var message: String = "+++ Welcome to ThunderBolt AS3 | VERSION: " + Logger.VERSION + " | AUTHOR: " + Logger.AUTHOR + " | Happy logging ;-) +++";
			Logger.trace (Logger.INFO, message);
	    }
	
		/**
		 * Calculates the amount of memory in MB and Kb currently in use by Flash Player
		 * @return 	String		Message about the current value of memory in use
		 *
		 * Tip: For detecting memory leaks in Flash or Flex check out WSMonitor, too ;-)
		 * @see: http://www.websector.de/blog/2007/10/01/detecting-memory-leaks-in-flash-or-flex-applications-using-wsmonitor/ 
		 *
		 */		 
		public static function memorySnapshot():String
	    {
	        var currentMemValue: uint = System.totalMemory;
			var message: String = "Memory Snapshot: " + Math.round(currentMemValue / 1024 / 1024 * 100) / 100 + " MB (" + Math.round(currentMemValue / 1024) + " kb)";
			return message;
	    }
				
		/**
		 * Logs info messages including objects for calling Firebug
		 * 
		 * @param 	msg				String		log message 
		 * @param 	logObjects		Array		Array of log objects using rest parameter
		 * 
		 */		
		public static function info (msg: String = null, ... logObjects): void
		{
			Logger.trace(Logger.INFO, msg, logObjects);			
		}
		
		/**
		 * Logs warn messages including objects for calling Firebug
		 * 
		 * @param 	msg				String		log message 
		 * @param 	logObjects		Array		Array of log objects using rest parameter
		 * 
		 */		
		public static function warn (msg: String = null, ... logObjects): void
		{
			Logger.trace(Logger.WARN, msg, logObjects);			
		}

		/**
		 * Logs error messages including objects for calling Firebug
		 * 
		 * @param 	msg				String		log message 
		 * @param 	logObjects		Array		Array of log objects using rest parameter
		 * 
		 */		
		public static function error (msg: String = null, ... logObjects): void
		{
			Logger.trace(Logger.ERROR, msg, logObjects);			
		}
		
		/**
		 * Logs debug messages messages including objects for calling Firebug
		 * 
		 * @param 	msg				String		log message 
		 * @param 	logObjects		Array		Array of log objects using rest parameter
		 * 
		 */		
		public static function debug (msg: String = null, ... logObjects): void
		{
			Logger.trace(Logger.LOG, msg, logObjects);			
		}		
			
		/**
		 * Hides the logging process calling Firebug
		 * @param 	value	Boolean     Hide or show the logs of ThunderBolt within Firebug
		 */		 
		public static function set hide(value: Boolean):void
	    {
	        _hide = value;
	    }
					 
		/**
		 * Calls Firebugs command line API to write log information
		 * 
		 * @param 	level		String			log level 
 		 * @param 	msg			String			log message 
		 * @param 	logObjects	Array			Array of log objects using rest parameter
		 */			 
		public static function trace (level: String, msg: String = "", ... logObjects): void
		{
			if(!_hide)
			{
				_depth = 0;
			 	// get log level
			 	_logLevel = level;
			 	// add log level to log messagef
			 	var logMsg: String = "[" + _logLevel.toUpperCase() + "] ";
		    	// add time	to log message
	    		if (includeTime) logMsg += getCurrentTime();
				// add message text to log message
			 	logMsg += msg;
			 	// call Firebug		 	
			 	ExternalInterface.call("console." + _logLevel, logMsg);
			 	// log objects	
			 	var i: int, l: int = logObjects.length;	 	
				for (i = 0; i < l; i++) 
				{
		        	Logger.logObject(logObjects[i]);
		    	}				
			}
	 	
		}
				
		/**
		 * Logs nested instances and properties
		 * 
		 * @param 	logObj		Object		log object
		 * @param 	id			String		short description of log object
		 */	
		private static function logObject (logObj: *, id: String = null): void
		{				
			if (_depth < Logger.MAX_DEPTH)
			{
				++ _depth;
				
				var propID: String = id || "";
				var description:XML = describeType(logObj);				
				var type: String = description.@name;
				
				if (primitiveType(type))
				{					
					var msg: String = (propID.length) 	? 	"[" + type + "] " + propID + " = " + logObj
														: 	"[" + type + "] " + logObj;
															
					ExternalInterface.call("console." + Logger.LOG, msg);
				}
				else if (type == "Object")
				{
				  	ExternalInterface.call("console.group", "[Object] " + propID);				  	
				  	for (var element: String in logObj)
				  	{
				  		logObject(logObj[element], element);				  		
				  	}
				  	ExternalInterface.call("console.groupEnd");
				}
				else if (type == "Array")
				{
				  	/* don't create a group on depth 1 when we are using the ... (rest) parameter called by Logger.trace() ;-) */
				  	if (_depth > 1) ExternalInterface.call("console.group", "[Array] " + propID);
				  	
				  	var i: int, l: int = logObj.length;					  					  	
				  	for (i = 0; i < l; i++)
				  	{
				  		logObject(logObj[i]);				  		
				  	}
				  	ExternalInterface.call("console.groupEnd");					  			
				}
				else
				{
					// log private props as well - thx Rob Herman [http://www.toolsbydesign.com] ;-)
					var list: XMLList = description..accessor;					
					
					if (list.length())
					{
						for each(var item: XML in list)
						{
							var propItem: String = item.@name;
							var typeItem: String = item.@type;							
							var access: String = item.@access;
							
							// log objects && properties accessing "readwrite" and "readonly" only 
							if (access && access != "writeonly") 
							{
								//TODO: filter classes
								// var classReference: Class = getDefinitionByName(typeItem) as Class;
								var valueItem: * = logObj[propItem];
								logObject(valueItem, propItem);
							}
						}					
					}
					else
					{
						logObject(logObj, type);					
					}
				}
			}
			else
			{
				// call one stop message only ;-)
				if (!_stopLog)
				{
					ExternalInterface.call("console." + Logger.WARN, "STOP LOGGING: More than " + _depth + " nested objects or properties.");
					_stopLog = true;
				}			
			}									
		}
			
		/**
		 * Checking for primitive types
		 * 
		 * @param 	type				String			type of object
		 * @return 	isPrimitiveType 	Boolean			isPrimitiveType
		 * 
		 */							
		private static function primitiveType (type: String): Boolean
		{
			var isPrimitiveType: Boolean;
			
			switch (type) 
			{
				case "Boolean":
				case "void":
				case "int":
				case "uint":
				case "Number":
				case "String":
				case "undefined":
				case "null":
					isPrimitiveType = true;
				break;			
				default:
					isPrimitiveType = false;
			}

			return isPrimitiveType;
		}

		/**
		 * Creates a String of valid time value
		 * @return 			String 		current time as a String using valid hours, minutes, seconds and milliseconds
		 */
		 
		private static function getCurrentTime ():String
	    {
    		var currentDate: Date = new Date();
    		
			var currentTime: String = 	"time "
										+ timeToValidString(currentDate.getHours()) 
										+ ":" 
										+ timeToValidString(currentDate.getMinutes()) 
										+ ":" 
										+ timeToValidString(currentDate.getSeconds()) 
										+ "." 
										+ timeToValidString(currentDate.getMilliseconds()) + FIELD_SEPERATOR;
			return currentTime;
	    }
	    		
		/**
		 * Creates a valid time value
		 * @param 	timeValue	Number     	Hour, minute or second
		 * @return 				String 		A valid hour, minute or second
		 */
		 
		private static function timeToValidString(timeValue: Number):String
	    {
	        return timeValue > 9 ? timeValue.toString() : "0" + timeValue.toString();
	    }

	}
}