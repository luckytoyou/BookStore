//ע��,������һ���ʼ�
	private void regist(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException  {
		Customer c=WebUtil.fillBean(request,Customer.class);
		//���������֤��
		String code=UUID.randomUUID().toString();
		c.setCode(code);
		//��������һ���̣߳����ͼ����ʼ�
		SendMail sm=new SendMail(c);
		sm.start();
		s.regist(c);
		response.getWriter().write("<script type='text/javascript'>alert('ע��ɹ��������Ѿ�������һ�⼤���ʼ����������䣬��ע����')</script>");
		response.setHeader("Refresh","0;URL="+request.getContextPath());
		
	}









public class SendMail extends Thread {

	private Customer c;

	public SendMail(Customer c) {
		this.c = c;
	}
	//�����ʼ�
	public void run() {
		try {
			Properties props = new Properties();
			props.setProperty("mail.host", "smtp.163.com");
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.auth", "true");//������֤����JavaMail��ʵ���й�
			Session session = Session.getInstance(props);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("itheimacloud@163.com"));//  noreply webmaster��һЩ�ٷ�������ʼ�
			msg.setRecipients(Message.RecipientType.TO, c.getEmail());
			msg.setSubject("����XX���ļ����ʼ�");
			
			msg.setContent("��л��ע���Ϊ���ǵĻ�Ա<br/>���͵��·����������˻�<br/><a href='http://localhost:8080/day23_00_bookstore/servlet/ControllerServlet?op=active&username="+c.getUsername()+"&code="+c.getCode()+"'>�͵�</a>", "text/html;charset=UTF-8");
			msg.saveChanges();
			
			Transport ts = session.getTransport();
			ts.connect("itheimacloud", "iamsorry");
			ts.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("�����ʼ�ʧ��");
		}
		
	}
	
}
