
//�ļ��ϴ����裺
//1.�������������ļ�DiskFileItemFactory
//2.�����ļ��ϴ�������ServletFileUpload
//3.�ж��ύ�����Ƿ����ϴ���������
//4.ServletFileUpload �����������ϴ����ݣ������������һ��List ���ϣ�ÿ��FileItem��Ӧһ��Form��������



private void addBook(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);//�ж��ύ�����Ƿ����ϴ���������
		if(!isMultipart)
			throw new RuntimeException("��ı�enctype���Ա�����multipart/form-data���͵�");
		DiskFileItemFactory factory = new DiskFileItemFactory();//�������������ļ�
		ServletFileUpload sfu = new ServletFileUpload(factory);//�����ļ��ϴ�������
		try {
			List<FileItem> items = sfu.parseRequest(request);//ServletFileUpload �����������ϴ����ݣ������������һ��List ���ϣ�ÿ��FileItem��Ӧһ��Form��������
			Book book = new Book();
			for(FileItem item:items){
				if(item.isFormField()){
					processFormField(item,book);
				}else{
					processUpload(item,book);
				}
			}
			s.addBook(book);
			response.getWriter().write("<script type='text/javascript'>alert('��ӳɹ�')</script>");
			response.setHeader("Refresh", "0;URL="+request.getContextPath()+"/servlet/ControllerServlet?op=addBookUI");
		} catch (FileUploadException e) {
			throw new RuntimeException("�����ϴ�����ʧ��");
		}
	}





