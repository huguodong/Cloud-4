package upgrades.entity;
/**
 * 升级版本信息Entity
 * @author gcy
 *
 */
public class PatchInfoEntity {
	private Integer patch_idx;    //索引值
	private String patch_version;  //版本号
	private String patch_desc;      //版本描述
	private String patch_type;      //版本类型
	private String restrict_info;   //版本约束
	private String patch_directory; //升级路径             
	private String create_time;     //创建时间
	public Integer getPatch_idx() {
		return patch_idx;
	}
	public void setPatch_idx(Integer patch_idx) {
		this.patch_idx = patch_idx;
	}
	public String getPatch_version() {
		return patch_version;
	}
	public void setPatch_version(String patch_version) {
		this.patch_version = patch_version;
	}
	public String getPatch_desc() {
		return patch_desc;
	}
	public void setPatch_desc(String patch_desc) {
		this.patch_desc = patch_desc;
	}
	public String getPatch_type() {
		return patch_type;
	}
	public void setPatch_type(String patch_type) {
		this.patch_type = patch_type;
	}
	public String getRestrict_info() {
		return restrict_info;
	}
	public void setRestrict_info(String restrict_info) {
		this.restrict_info = restrict_info;
	}
	public String getPatch_directory() {
		return patch_directory;
	}
	public void setPatch_directory(String patch_directory) {
		this.patch_directory = patch_directory;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
