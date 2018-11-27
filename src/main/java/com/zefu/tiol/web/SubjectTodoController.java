package com.zefu.tiol.web;

import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.SubjectTodo;
import com.zefu.tiol.service.SubjectTodoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 * @功能描述 会议议题填报待办
 * @time 2018年11月14日
 * @author 李缝兴
 *
 */
@Controller
@RequestMapping("/subjectTodo")
public class SubjectTodoController {
	
	/**
	 * 此备注为ServiceImpl中配置的参数，如不要请用@Autowired
	 */
	@Resource(name="subjectTodoService")
	private SubjectTodoService subjectTodoService;

	/**
	 * 保存待办信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSubjectTodo")
	@ResponseBody
	public Object saveSubjectTodo(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		LoginUser loginUser = CommonUtil.getLoginUser(request);
		SubjectTodo todo = new SubjectTodo();
		todo.setUuid(CommonUtil.getUUID());
		todo.setMeetingId(param.get("meetingId")+"");
		todo.setMeetingName(param.get("meetingName")+"");
		todo.setReceiveUserId(param.get("receiveUserId")+"");
		todo.setReceiveUserName(param.get("receiveUserName")+"");
		todo.setSendUserId(loginUser.getUserId());
		todo.setSendUserName(loginUser.getUserName());
		todo.setSubjectId(CommonUtil.getUUID());
		todo.setSubjectName(param.get("subjectName")+"");
		todo.setStatus("1");
		todo.setItemCode(param.get("itemCode")+"");
		todo.setItemId(param.get("itemIds")+"");
		int count = subjectTodoService.saveSubjectTodo(todo);
		return new Result(todo);
	}

	/**
	 * 查询待办信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSubjectTo")
	@ResponseBody
	public List<SubjectTodo> getSubjectTo(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<SubjectTodo> SubjectTodoList = subjectTodoService.getSubjectTo(param);
		return SubjectTodoList;
	}

	/**
	 * 查询待办已办信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySubjectList")
	@ResponseBody
	public Page<Map<String, Object>> querySubjectList(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		LoginUser loginUser = CommonUtil.getLoginUser(request);
		Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
		param.put("receiveUserId",loginUser.getUserId());
		List<SubjectTodo> SubjectTodoList = subjectTodoService.querySubjectListWithPage(param, page);
		int totalCount = subjectTodoService.countSubjectTodo(param);
		page.setData(SubjectTodoList);
		page.setTotalCount(totalCount);
		return page;
	}

	/**
	 * 获取个人待办已办
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPersonalTodoCount")
	@ResponseBody
	public Object getPersonalTodoCount(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		LoginUser loginUser = CommonUtil.getLoginUser(request);
		Map<String,Object> countMap =  subjectTodoService.getPersonalTodoCount(loginUser.getUserId());
		return countMap;
	}
}
