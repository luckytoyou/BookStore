//注册,发送了一封邮件
	private void regist(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException  {
		Customer c=WebUtil.fillBean(request,Customer.class);
		//生成随机验证码
		String code=UUID.randomUUID().toString();
		c.setCode(code);
		//单独启动一个线程：发送激活邮件
		SendMail sm=new SendMail(c);
		sm.start();
		s.regist(c);
		response.getWriter().write("<script type='text/javascript'>alert('注册成功！我们已经发送了一封激活邮件到您的邮箱，请注意查查')</script>");
		response.setHeader("Refresh","0;URL="+request.getContextPath());
		
	}









public class SendMail extends Thread {

	private Customer c;

	public SendMail(Customer c) {
		this.c = c;
	}
	//发送邮件
	public void run() {
		try {
			Properties props = new Properties();
			props.setProperty("mail.host", "smtp.163.com");
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.auth", "true");//请求认证，与JavaMail的实现有关
			Session session = Session.getInstance(props);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("itheimacloud@163.com"));//  noreply webmaster等一些官方含义的邮件
			msg.setRecipients(Message.RecipientType.TO, c.getEmail());
			msg.setSubject("来自XX书店的激活邮件");
			
			msg.setContent("感谢您注册成为我们的会员<br/>请猛点下方激活您的账户<br/><a href='http://localhost:8080/day23_00_bookstore/servlet/ControllerServlet?op=active&username="+c.getUsername()+"&code="+c.getCode()+"'>猛点</a>", "text/html;charset=UTF-8");
			msg.saveChanges();
			
			Transport ts = session.getTransport();
			ts.connect("itheimacloud", "iamsorry");
			ts.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("发送邮件失败");
		}
		
	}
	
}
