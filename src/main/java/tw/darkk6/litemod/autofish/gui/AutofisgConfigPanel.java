package tw.darkk6.litemod.autofish.gui;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import tw.darkk6.litemod.autofish.util.Config;
import tw.darkk6.litemod.autofish.util.Lang;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;

public class AutofisgConfigPanel extends Gui implements ConfigPanel {

	private Minecraft mc=Minecraft.getMinecraft();
	private Config config;
	private HashMap<String,GuiCheckbox> boxMap;
	private Rect rectDis,rectSname,rectBvalue;
	private GuiTextField txtDis,txtSname,txtBvalue;
	private int extraGap=0;
	
	@Override
	public String getPanelTitle() {return Lang.get("autofishd.setting.gui.title");}
	@Override
	public int getContentHeight() {return 0;}
	@Override
	public void onPanelResize(ConfigPanelHost host) {}
	@Override
	public void onTick(ConfigPanelHost host) {}

	@Override
	public void onPanelShown(ConfigPanelHost host) {
		int wCenter=host.getWidth()/2;
		config=Config.get();
		//Control ID 從 0 開始
		if(boxMap==null){
			int ctlID=0;
			extraGap=0;
			boxMap=new HashMap<String, GuiCheckbox>();
			GuiCheckbox tmp;
			tmp=createCheckbox(ctlID++,0,0,Lang.get("autofishd.setting.enable"));
			boxMap.put("enable", tmp);
			
			tmp=createCheckbox(ctlID++,0,1,Lang.get("autofishd.setting.preventBreak"));
			boxMap.put("preventBreak", tmp);
			
			tmp=createCheckbox(ctlID++,0,2,Lang.get("autofishd.setting.switch"));
			boxMap.put("switchRod", tmp);
			
			tmp=createCheckbox(ctlID++,0,3,Lang.get("autofishd.setting.checkdistance"));
			boxMap.put("checkDistance", tmp);
			
			tmp=createCheckbox(ctlID++,0,4,Lang.get("autofishd.setting.showdistance"));
			boxMap.put("showDistance", tmp);
			
			ctlID=100;
			extraGap=10;
			int maxLabelWidth=0;
			
			rectDis=createRect(0,5,Lang.get("autofishd.setting.maxdistance"),wCenter);
			rectBvalue=createRect(0,6,Lang.get("autofishd.setting.breakvalue"),wCenter);
			rectSname=createRect(0,7,Lang.get("autofishd.setting.soundname"),wCenter);
			
			maxLabelWidth = Math.max(rectDis.width, Math.max(rectSname.width, rectBvalue.width)) + 3;
			rectDis.width=maxLabelWidth;
			rectSname.width=maxLabelWidth;
			rectBvalue.width=maxLabelWidth;
			
			txtDis=createTextbox(ctlID++,rectDis,40);
			txtSname=createTextbox(ctlID++,rectSname,80);
			txtBvalue=createTextbox(ctlID++,rectBvalue,40);
			
		}
		
		loadSetting();
	}
	
	@Override
	public void onPanelHidden() {
		//將 checkbox 設定寫入 config
		config.enable=boxMap.get("enable").checked;
		config.preventBreak=boxMap.get("preventBreak").checked;
		config.switchRod=boxMap.get("switchRod").checked;
		config.checkDistance=boxMap.get("checkDistance").checked;
		config.showDistance=boxMap.get("showDistance").checked;
		//取得 txt 內容，驗證後存回 config
		config.soundName=txtSname.getText();
		if(config.soundName.length()==0) config.soundName=Config.DEFAULT_SOUND_NAME;
		
		String tmp=txtDis.getText().replaceAll("[^0-9.]","");
		try{config.maxDistance=Float.parseFloat(tmp);}
		catch(Exception e){ config.maxDistance=1.7f; }
		
		tmp=txtBvalue.getText().replaceAll("[^0-9]","");
		try{config.breakValue=Integer.parseInt(tmp);}
		catch(Exception e){ config.breakValue=5; }
		
		config.save();
	}

	@Override
	public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks) {
		//繪製出所有 Checkbox
		for(String key:boxMap.keySet())
			boxMap.get(key).drawButton(mc, mouseX, mouseY);
		//繪製出所有 String
		this.drawString(mc.fontRendererObj, Lang.get("autofishd.setting.maxdistance"), rectDis.x, rectDis.y, 0xFFFFFFFF);
		this.drawString(mc.fontRendererObj, Lang.get("autofishd.setting.breakvalue"), rectBvalue.x, rectBvalue.y, 0xFFFFFFFF);
		this.drawString(mc.fontRendererObj, Lang.get("autofishd.setting.soundname"), rectSname.x, rectSname.y, 0xFFFF7373);
		//繪製出所有 Textfield
		txtDis.drawTextBox();
		txtSname.drawTextBox();
		txtBvalue.drawTextBox();
	}

	@Override
	public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
		txtDis.mouseClicked(mouseX, mouseY, mouseButton);
		txtSname.mouseClicked(mouseX, mouseY, mouseButton);
		txtBvalue.mouseClicked(mouseX, mouseY, mouseButton);
		
		for(String key:boxMap.keySet()){
			if(boxMap.get(key).mousePressed(mc, mouseX, mouseY)){
				boxMap.get(key).checked=!boxMap.get(key).checked;
			}
		}
	}

	@Override
	public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
		
	}

	@Override
	public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY) {
	}

	@Override
	public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode) {
		if(txtDis.isFocused()){
			txtDis.textboxKeyTyped(keyChar, keyCode);
			return;
		}
		if(txtSname.isFocused()){
			txtSname.textboxKeyTyped(keyChar, keyCode);
			return;
		}
		if(txtBvalue.isFocused()){
			txtBvalue.textboxKeyTyped(keyChar, keyCode);
			return;
		}
	}
	
	private void loadSetting(){
		boxMap.get("enable").checked=config.enable;
		boxMap.get("preventBreak").checked=config.preventBreak;
		boxMap.get("switchRod").checked=config.switchRod;
		boxMap.get("checkDistance").checked=config.checkDistance;
		boxMap.get("showDistance").checked=config.showDistance;
		
		txtDis.setText(String.valueOf(config.maxDistance));
		txtSname.setText(config.soundName);
		txtBvalue.setText(String.valueOf(config.breakValue));
	}

	private GuiCheckbox createCheckbox(int id,int col,int row,String str){
		int fh=mc.fontRendererObj.FONT_HEIGHT;
		return new GuiCheckbox(id, 10 + col, 10 + (fh+5)*row, str);
	}
	
	private Rect createRect(int col,int row,String str,int hostCenter){
		int fh=mc.fontRendererObj.FONT_HEIGHT;
		int strWidth=mc.fontRendererObj.getStringWidth(str);
		return new Rect(
					10 + col * hostCenter,
					extraGap + 10 + (fh+7)*row,
					strWidth,
					fh+4
				);
	}
	
	private GuiTextField createTextbox(int id,Rect rect,int width){
		int fh=mc.fontRendererObj.FONT_HEIGHT;
		return new GuiTextField(id,mc.fontRendererObj,
						rect.x + rect.width+2,
						rect.y,
						width,
						fh+4
					);
	}
	
	private class Rect{
		public int x,y,width,height;
		public Rect(int x,int y,int w,int h){
			this.x=x;
			this.y=y;
			this.width=w;
			this.height=h;
		}
	}
}
