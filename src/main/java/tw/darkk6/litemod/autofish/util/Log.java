package tw.darkk6.litemod.autofish.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
public static Logger log=null;
	
	private static void initLogger(){
		if(log==null) log=LogManager.getLogger(Reference.LOG_TAG);
	}
	
	public static Logger info(String str){
		initLogger();
		log.info(str);
		return log;
	}
	
	public static void logChat(String msg){
		try{
			TextComponentString txt=new TextComponentString(TextFormatting.RED+"["+Reference.LOG_TAG+"] ");
			txt.appendText(TextFormatting.RESET+msg);
			Minecraft.getMinecraft().thePlayer.addChatMessage(txt);
		}catch(Exception e){
			Log.info(msg);
		}
	}
	
	public static void infoChat(String msg){
		try{
			TextComponentString txt=new TextComponentString(TextFormatting.GOLD+"["+Reference.LOG_TAG+"] ");
			txt.appendText(TextFormatting.RESET+msg);
			Minecraft.getMinecraft().thePlayer.addChatMessage(txt);
		}catch(Exception e){
			Log.info(msg);
		}
	}
}
