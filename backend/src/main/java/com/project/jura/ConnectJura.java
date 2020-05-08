package com.project.jura;

import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.ServletContext;

import com.project.service.JuraService;
import com.project.service.impl.JuraServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;


public class ConnectJura implements InitializingBean, ServletContextAware{

	@Autowired
	private JuraService juraService;

	@Override
	public void setServletContext(ServletContext application) {
		//System.out.println("----------------JuraServer Start------------------");
		/*List<Nodeinfo> r = burnServiceImpl.nodeDetail();
		List<String> ips = new ArrayList<String>();
		for(int i = 0; i < r.size(); i++){
			String ip = r.get(i).getIp();
			//System.out.println(ip);
		}*/
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		juraService.connectJuraServer();
		//accessServiceImpl.test();
	}
}