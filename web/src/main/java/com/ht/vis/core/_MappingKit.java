package com.ht.vis.core;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.ht.vis.model.*;

public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("s_log_op", "id", LogOp.class);
		arp.addMapping("s_param", "id", Param.class);
		arp.addMapping("s_res", "id", Res.class);
		arp.addMapping("s_ser", "id", Ser.class);
		arp.addMapping("s_role", "id", Role.class);
		arp.addMapping("s_role_res", "id", RoleRes.class);
		arp.addMapping("s_role_ser", "id", RoleSer.class);
		arp.addMapping("s_user", "id", User.class);
		arp.addMapping("s_user_role", "id", UserRole.class);
		arp.addMapping("s_dd","id",Dd.class);
		arp.addMapping("c_meeting", "id", CMeeting.class);
		arp.addMapping("v_change", "id", VChange.class);
		arp.addMapping("v_info", "id", VInfo.class);
		arp.addMapping("v_insurance", "id", VInsurance.class);
		arp.addMapping("c_department", "id", CDepartment.class);
		arp.addMapping("c_staff", "id", CStaff.class);
		arp.addMapping("c_department_staff", "id", CDepartmentStaff.class);
		arp.addMapping("c_info", "id", CInfo.class);
		arp.addMapping("v_line", "id", VLine.class);
		arp.addMapping("sp_assessment_criteria", "id", SpAssessmentCriteria.class);
		arp.addMapping("s_file_storage", "id", SFileStorage.class);
	}
}