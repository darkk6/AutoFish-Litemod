package tw.darkk6.litemod.autofish.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

@ExposableOptions(strategy=ConfigStrategy.Unversioned , filename=Reference.MOD_CONFIG)
public class Config implements Exposable {
	public static final String DEFAULT_SOUND_NAME="entity.bobber.splash";
	
	private static Config instance=null;
	public static Config get(){
		if(instance==null) instance=new Config();
		return instance;
	}
	//這個是紀錄權限的，不會寫入檔案中
	public boolean isPermitted=true;
	//是否啟用 Autofish
	@Expose @SerializedName("enable")
	public boolean enable=true;
	//是否啟用 防止損壞
	@Expose @SerializedName("preventBreak")
	public boolean preventBreak=true;
	//是否啟用 自動更換
	@Expose @SerializedName("switchRod")
	public boolean switchRod=true;
	//是否啟用 檢查距離
	@Expose @SerializedName("checkDistance")
	public boolean checkDistance=false;
	//檢查距離的最大值
	@Expose @SerializedName("maxDistance")
	public float maxDistance=1.7f;
	//是否啟用 顯示聲音距離[偵測用]
	@Expose @SerializedName("showDistance")
	public boolean showDistance=false;
	
	//Internal value
	@Expose @SerializedName("internal_soundName")
	public String soundName=DEFAULT_SOUND_NAME;
	@Expose @SerializedName("internal_breakValue")
	public int breakValue=5;
	
	public void init(){
		LiteLoader.getInstance().registerExposable(this,null);
	}
	public void save(){
		LiteLoader.getInstance().writeConfig(this);
	}
}
